package com.co.app.modrec.client.ui.widgets;

import java.util.List;

import com.co.app.cer.client.curriculum.version.CurriculumVersionListPresenter;
import com.co.app.cer.client.curriculum.version.MyCurriculumVersionListPresenter;
import com.co.app.modrec.client.ModRecHomePresenter;
import com.co.app.modrec.client.events.CurriculumVersionSelectedEvent;
import com.co.app.modrec.client.events.CurriculumVersionSelectedEvent.CurriculumVersionSelectedHandler;
import com.co.app.modrec.shared.proxy.CurriculumVersionProxy;
import com.co.app.modrec.shared.requestfactory.CurriculumRequestFactory;
import com.co.app.modrec.shared.requestfactory.CurriculumVersionServiceRequest;
import com.co.app.modrec.shared.resources.COModuleRecommenderResources;
import com.co.client.place.PageNameTokens;
import com.co.core.auth.client.COClientUserHolder;
import com.co.core.auth.client.event.COClientUserChangedEvent;
import com.co.core.auth.client.event.COClientUserChangedEvent.COClientUserChangedHandler;
import com.co.core.auth.shared.COClientUserModel;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.SimplePanel;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.RequestFactoryProxy;

/**
 * Presenter for Curriculum Version Selection
 * 
 * user sees all curriculum version and his own separated
 * 
 * @author Lucas Reeh
 * 
 */
public class CurriculumVersionSelectionPresenter
		extends
		Presenter<CurriculumVersionSelectionPresenter.MyView, CurriculumVersionSelectionPresenter.MyProxy> {

	COClientUserModel user;

	public interface MyView extends View {
		public SimplePanel getPnMyCurriculumVersionList();

		public SimplePanel getPnCurriculumVersionList();
	}

	/**
	 * Slot for list with students CER Versions - presenter widget
	 */
	public static final Object SLOT_myCurriculumVersionList = new Object();

	/**
	 * Slot for list with CER Versions - presenter widget
	 */
	public static final Object SLOT_curriculumVersionList = new Object();

	@Inject
	MyCurriculumVersionListPresenter myCurriculumVersionsPresenter;

	@Inject
	CurriculumVersionListPresenter curriculumVersionsPresenter;

	@Inject
	CurriculumRequestFactory factory;

	public final COClientUserChangedHandler coClientUserChangedHandler = new COClientUserChangedHandler() {

		/**
		 * sets header content in right content slot
		 */
		@Override
		public void onCOClientUserChanged(COClientUserChangedEvent event) {
			user = event.getCoClientUser();
			/** set in slots */
			if (user.isStudent())
				setInSlot(SLOT_myCurriculumVersionList,
						myCurriculumVersionsPresenter);
		}

	};

	/**
	 * placeManager
	 */
	@Inject
	PlaceManager placeManager;

	RequestFactoryProxy<FilterPagingLoadConfig, PagingLoadResult<CurriculumVersionProxy>> proxy;

	public final CurriculumVersionSelectedHandler curriculumVersionSelectedHandler = new CurriculumVersionSelectedHandler() {
		@Override
		public void onCurriculumVersionSelected(
				CurriculumVersionSelectedEvent event) {
			PlaceRequest request = new PlaceRequest(PageNameTokens.modules);
			placeManager.revealPlace(request.with("pCurriculumVersionId", event
					.getCurriculumVersionId().toString()));
		}
	};

	/**
	 * proxy interface (code split)
	 */
	@ProxyCodeSplit
	@NameToken(PageNameTokens.modreccurriculumversionselection)
	public interface MyProxy extends
			ProxyPlace<CurriculumVersionSelectionPresenter> {
	}

	/**
	 * class constructor
	 */
	@Inject
	public CurriculumVersionSelectionPresenter(final EventBus eventBus,
			final MyView view, final MyProxy proxy,
			COClientUserHolder userHolder) {
		super(eventBus, view, proxy);
		this.user = userHolder.getUser();
	}

	/**
	 * revealInParent
	 */
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, ModRecHomePresenter.SLOT_mainContent,
				this);
	}

	/**
	 * onBind
	 */
	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getEventBus().addHandler(
				CurriculumVersionSelectedEvent.getType(),
				curriculumVersionSelectedHandler));
		registerHandler(getEventBus().addHandler(
				COClientUserChangedEvent.getType(), coClientUserChangedHandler));
	}

	/**
	 * onReveal
	 */
	@Override
	protected void onReveal() {
		super.onReveal();
		factory.initialize(getEventBus());

		/** proxy differs for students curriculum versions */
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
				req.getStudentsCurriculumVersions(loadConfig.getOffset(),
						loadConfig.getLimit(), sortInfo, filterConfig).to(
						receiver);
				req.fire();
			}

		};

		/** special attributes for students list of curricula */
		myCurriculumVersionsPresenter.setProxy(proxy);
		myCurriculumVersionsPresenter.setPagingToolbarVisible(false);

		curriculumVersionsPresenter.setPagingToolbarVisible(false);

		/** set in slots */
		if (user.isStudent())
			setInSlot(SLOT_myCurriculumVersionList,
					myCurriculumVersionsPresenter);
		setInSlot(SLOT_curriculumVersionList, curriculumVersionsPresenter);

		/** set titles */
		myCurriculumVersionsPresenter
				.setPanelTitle(COModuleRecommenderResources.TEXT
						.myCurriculumVersions());
		curriculumVersionsPresenter
				.setPanelTitle(COModuleRecommenderResources.TEXT
						.curriculaVersionLabel());
	}
}
