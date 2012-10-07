package com.co.app.cer.client.curriculum.version;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Provider;

import com.co.app.cer.shared.resources.COCerResources;
import com.co.app.modrec.shared.proxy.CurriculumVersionProxy;
import com.co.app.modrec.shared.requestfactory.CurriculumRequestFactory;
import com.co.app.modrec.shared.requestfactory.CurriculumVersionServiceRequest;
import com.co.app.modrec.shared.resources.COModuleRecommenderResources;
import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.data.shared.loader.RequestFactoryProxy;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

/**
 * View for Presenter Widget for curriculum Version List
 * 
 * @author Lucas Reeh
 * 
 */
public class CurriculumVersionListView extends ViewImpl implements
		CurriculumVersionListPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, CurriculumVersionListView> {
	}

	/** creating properties class */
	CurriculumVersionProxyProperties props = GWT
			.create(CurriculumVersionProxyProperties.class);

	@UiField
	ContentPanel pnList;

	@UiField(provided = true)
	Grid<CurriculumVersionProxy> grid;
	
	@UiField(provided = true)
	PagingToolBar toolbar;
	
	// GXT components for grid
	
	ColumnModel<CurriculumVersionProxy> cm;

	ListStore<CurriculumVersionProxy> store;

	GroupingView<CurriculumVersionProxy> view;

	RequestFactoryProxy<FilterPagingLoadConfig, PagingLoadResult<CurriculumVersionProxy>> proxy;

	PagingLoader<FilterPagingLoadConfig, PagingLoadResult<CurriculumVersionProxy>> loader;

	CurriculumRequestFactory factory;
	
	

	/**
	 * Class constructor
	 * 
	 * @param binder
	 */
	@Inject
	public CurriculumVersionListView(final Binder binder, Provider<CurriculumRequestFactory> provider) {
		this.factory = provider.get();
		// proxy for accessing curriculum version service on server
		if (proxy == null) {
			proxy = new RequestFactoryProxy<FilterPagingLoadConfig, PagingLoadResult<CurriculumVersionProxy>>() {
				@Override
				public void load(
						FilterPagingLoadConfig loadConfig,
						Receiver<? super PagingLoadResult<CurriculumVersionProxy>> receiver) {
					CurriculumVersionServiceRequest req = factory
							.curriculumVersionServiceRequest();
					List<SortInfo> sortInfo = createRequestSortInfo(req,
							loadConfig.getSortInfo());
					List<FilterConfig> filterConfig = createRequestFilterConfig(
							req, loadConfig.getFilters());
					req.getCurriculumVersions(loadConfig.getOffset(),
							loadConfig.getLimit(), sortInfo, filterConfig).to(
							receiver);
					req.fire();

				}
			};
		}
		
		// loader for grid
		loader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<CurriculumVersionProxy>>(
				proxy) {
			@Override
			protected FilterPagingLoadConfig newLoadConfig() {
				return new FilterPagingLoadConfigBean();
			}
		};
		
		// no async loader
		loader.setRemoteSort(false);
	
		store = new ListStore<CurriculumVersionProxy>(
				props.curriculumId());
		
		store.addSortInfo(new StoreSortInfo<CurriculumVersionProxy>(props.curriculumIdentificator(), SortDir.ASC));
		
		loader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, CurriculumVersionProxy, PagingLoadResult<CurriculumVersionProxy>>(
				store));
		
		toolbar = new PagingToolBar(1000);
		toolbar.bind(loader);
		
		
		// curriuclum version grid columns
		ColumnConfig<CurriculumVersionProxy, String> colCurriculumIdentificator = new ColumnConfig<CurriculumVersionProxy, String>(
				props.curriculumIdentificator(), 100,
				COCerResources.TEXT.curriculumReferenceNameLabel());
		
		ColumnConfig<CurriculumVersionProxy, String> colCurriculumVersionRefId = new ColumnConfig<CurriculumVersionProxy, String>(
				props.curriculumVersionRefId(), 100,
				COCerResources.TEXT.curriculumVersionNameLabel());
		
		ColumnConfig<CurriculumVersionProxy, String> colCurriculumVersionName = new ColumnConfig<CurriculumVersionProxy, String>(
				props.curriculumVersionName(), 400,
				COModuleRecommenderResources.TEXT.curriculumVersionNameLabel());

		ColumnConfig<CurriculumVersionProxy, String> colCurriculumIntendedDegreeName = new ColumnConfig<CurriculumVersionProxy, String>(
				props.curriculumIntendedDegreeName(), 300,
				COCerResources.TEXT.intendedDegreeNameLabel());
		
		List<ColumnConfig<CurriculumVersionProxy, ?>> columnList = new ArrayList<ColumnConfig<CurriculumVersionProxy, ?>>();

		columnList.add(colCurriculumIdentificator);
		columnList.add(colCurriculumVersionRefId);
		columnList.add(colCurriculumIntendedDegreeName);
		columnList.add(colCurriculumVersionName);
		
		colCurriculumIdentificator.setAlignment(HorizontalAlignmentConstant
				.endOf(Direction.LTR));
		colCurriculumIdentificator.setGroupable(false);
		colCurriculumVersionRefId.setGroupable(false);
		colCurriculumVersionName.setGroupable(false);
		
		// grid config (column model, view, and grid) 
		cm = new ColumnModel<CurriculumVersionProxy>(columnList);
		view = new GroupingView<CurriculumVersionProxy>();
		view.groupBy(colCurriculumIntendedDegreeName);
		grid = new Grid<CurriculumVersionProxy>(store, cm, view) {
			@Override
			protected void onAfterFirstAttach() {
				super.onAfterFirstAttach();
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					public void execute() {
						loader.load();
					}
				});
			}
		};
		
		grid.setLoader(loader);
		
		GridFilters<CurriculumVersionProxy> filters = new GridFilters<CurriculumVersionProxy>();
		filters.initPlugin(grid);
		filters.setLocal(true);
		filters.addFilter(new StringFilter<CurriculumVersionProxy>(props.curriculumVersionName()));
		filters.addFilter(new StringFilter<CurriculumVersionProxy>(props.curriculumIdentificator()));
		filters.addFilter(new StringFilter<CurriculumVersionProxy>(props.curriculumVersionRefId()));
		filters.addFilter(new StringFilter<CurriculumVersionProxy>(props.curriculumIntendedDegreeName()));
		
		// default grouping enabled
		view.setShowGroupedColumn(false);
		view.setAutoExpandColumn(colCurriculumVersionName);	
		
		// special attributes
		grid.setAllowTextSelection(false);
		grid.getView().setForceFit(true);
		
		view.setEmptyText(COModuleRecommenderResources.TEXT.curriculaVersionNoResults());

		// uibinder
		widget = binder.createAndBindUi(this);
	}

	/**
	 * @return view as widget
	 */
	@Override
	public Widget asWidget() {
		return widget;
	}

	/**
	 * @return the grid
	 */
	public Grid<CurriculumVersionProxy> getGrid() {
		return grid;
	}

	/**
	 * @return the toolbar
	 */
	public PagingToolBar getToolbar() {
		return toolbar;
	}

	/**
	 * @return the props
	 */
	public CurriculumVersionProxyProperties getProps() {
		return props;
	}

	/**
	 * @return the myCurriculumVersionsStore
	 */
	public ListStore<CurriculumVersionProxy> getStore() {
		return store;
	}

	/**
	 * @return the pnList
	 */
	public ContentPanel getPnList() {
		return pnList;
	}

	/**
	 * @return the view
	 */
	public GroupingView<CurriculumVersionProxy> getView() {
		return view;
	}

	/**
	 * @return the proxy
	 */
	public RequestFactoryProxy<FilterPagingLoadConfig, PagingLoadResult<CurriculumVersionProxy>> getProxy() {
		return proxy;
	}

	/**
	 * @param proxy the proxy to set
	 */
	public void setProxy(
			RequestFactoryProxy<FilterPagingLoadConfig, PagingLoadResult<CurriculumVersionProxy>> proxy) {
		this.proxy = proxy;
	}
	
}
