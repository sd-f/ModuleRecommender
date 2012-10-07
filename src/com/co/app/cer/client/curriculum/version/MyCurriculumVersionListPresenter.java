package com.co.app.cer.client.curriculum.version;

import com.co.app.modrec.client.events.CurriculumVersionSelectedEvent;
import com.co.app.modrec.shared.proxy.CurriculumVersionProxy;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.data.shared.loader.RequestFactoryProxy;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.event.CellClickEvent;
import com.sencha.gxt.widget.core.client.event.CellClickEvent.CellClickHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

/**
 * Presenter widget for curriculum Version List
 * 
 * @author Lucas Reeh
 * 
 */
public class MyCurriculumVersionListPresenter extends
		PresenterWidget<MyCurriculumVersionListPresenter.MyView> {

	/**
	 * default view interface
	 * 
	 * @author Lucas Reeh
	 * 
	 */
	public interface MyView extends View {
		public Grid<CurriculumVersionProxy> getGrid();

		public PagingToolBar getToolbar();

		public CurriculumVersionProxyProperties getProps();

		public ListStore<CurriculumVersionProxy> getStore();

		public ContentPanel getPnList();

		public GroupingView<CurriculumVersionProxy> getView();

		public void setProxy(
				RequestFactoryProxy<FilterPagingLoadConfig, PagingLoadResult<CurriculumVersionProxy>> proxy);

		public PagingLoader<FilterPagingLoadConfig, PagingLoadResult<CurriculumVersionProxy>> getLoader();

	}

	/**
	 * Class constructor
	 * 
	 * @param eventBus
	 * @param view
	 * @param proxy
	 */
	@Inject
	public MyCurriculumVersionListPresenter(final EventBus eventBus,
			final MyView view) {
		super(eventBus, view);
	}

	/**
	 * placeManager
	 */
	@Inject
	PlaceManager placeManager;

	/**
	 * onBind
	 */
	@Override
	protected void onBind() {
		super.onBind();
		getView().getGrid().addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				int row = event.getRowIndex();
				CurriculumVersionSelectedEvent evt = new CurriculumVersionSelectedEvent(
						getView().getStore().get(row).getCurriculumId());
				getEventBus().fireEvent(evt);
			}
		});
		getView().getGrid().setLoadMask(true);
	}

	public void setPagingToolbarVisible(Boolean visible) {
		getView().getToolbar().setVisible(visible);
	}

	/**
	 * sets paging size for list
	 * 
	 * @param pageSize
	 */
	public void setPagingSize(int pageSize) {
		getView().getToolbar().setPageSize(pageSize);
	}

	/**
	 * sets title of framed panel
	 * 
	 * @param titleText
	 *            to set
	 */
	public void setPanelTitle(String titleText) {
		getView().getPnList().setHeadingText(titleText);
	}

	/**
	 * @param proxy
	 *            the proxy to set
	 */
	public void setProxy(
			RequestFactoryProxy<FilterPagingLoadConfig, PagingLoadResult<CurriculumVersionProxy>> proxy) {
		if (proxy != null)
			getView().setProxy(proxy);
	}

	/**
	 * set default grouping enabled
	 * 
	 * @param enabled
	 */
	public void setGridHeight(Integer height) {
		getView().getGrid().setHeight(height);
	}

	public void load() {
		getView().getLoader().load();
	}

}
