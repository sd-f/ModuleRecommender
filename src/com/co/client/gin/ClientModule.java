package com.co.client.gin;

import com.google.inject.Singleton;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.co.client.place.ClientPlaceManager;
import com.co.client.place.DefaultPlace;
import com.co.client.place.PageNameTokens;
import com.co.core.auth.client.COClientUserHolder;
import com.co.core.auth.client.COLoginBarPresenter;
import com.co.core.auth.client.COLoginBarView;
import com.co.core.home.client.COHomePresenter;
import com.co.core.home.client.COHomeView;
import com.co.core.page.client.COPagePresenter;
import com.co.core.page.client.COPageView;
import com.co.core.page.client.footer.COPageFooterPresenter;
import com.co.core.page.client.footer.COPageFooterView;
import com.co.core.page.client.header.COPageHeaderPresenter;
import com.co.core.page.client.header.COPageHeaderView;
import com.co.core.page.client.error.COPageNotFoundPresenter;
import com.co.core.page.client.error.COPageNotFoundView;
import com.co.core.auth.client.COSessionControllerPresenter;
import com.co.core.auth.client.COSessionControllerView;
import com.co.core.auth.client.gatekeeper.LoggedInGatekeeper;
import com.co.core.auth.shared.service.COAuthDataRequestFactory;
import com.co.core.page.client.error.COPageNotAuthorizedPresenter;
import com.co.core.page.client.error.COPageNotAuthorizedView;
import com.co.core.myapps.client.COMyAppsPresenter;
import com.co.core.myapps.client.COMyAppsView;
import com.co.core.myapps.client.widget.COMyAppsButtonPresenter;
import com.co.core.myapps.client.widget.COMyAppsButtonView;
import com.co.app.modrec.client.ModRecHomePresenter;
import com.co.app.modrec.client.ModRecHomeView;
import com.co.app.modrec.shared.requestfactory.CurriculumRequestFactory;
import com.co.app.cer.client.curriculum.node.ui.grid.CurriculumNodeGridPresenter;
import com.co.app.cer.client.curriculum.node.ui.grid.CurriculumNodeGridView;
import com.co.app.cer.client.curriculum.node.ui.tree.CurriculumNodeTreePresenter;
import com.co.app.cer.client.curriculum.node.ui.tree.CurriculumNodeTreeView;
import com.co.app.cer.client.curriculum.version.CurriculumVersionListPresenter;
import com.co.app.cer.client.curriculum.version.CurriculumVersionListView;
import com.co.app.cer.client.curriculum.version.MyCurriculumVersionListPresenter;
import com.co.app.cer.client.curriculum.version.MyCurriculumVersionListView;
import com.co.app.modrec.client.ui.widgets.CurriculumVersionSelectionPresenter;
import com.co.app.modrec.client.ui.widgets.CurriculumVersionSelectionView;
import com.co.app.modrec.client.ui.widgets.ModRecWelcomePresenter;
import com.co.app.modrec.client.ui.widgets.ModRecWelcomeView;
import com.co.app.modrec.client.ui.widgets.ModulePresenter;
import com.co.app.modrec.client.ui.widgets.ModuleView;
import com.co.app.modrec.client.ui.widgets.button.ModRecButtonPresenter;
import com.co.app.modrec.client.ui.widgets.button.ModRecButtonView;
import com.co.app.modrec.client.ui.widgets.recommendation.EditRecommendationWindow;

/**
 * Gin client module
 * 
 * class for binding client constants and presenters
 * 
 * @author Lucas Reeh
 * 
 */
public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(ClientPlaceManager.class));

		/**
		 * security cookie binding on client side for cross platform injection
		 * protection, will be caught on server and checked
		 */
		bindConstant().annotatedWith(SecurityCookie.class).to("SecCookie");

		/**
		 * default place is bind to the token home and reveals default place
		 */
		bindConstant().annotatedWith(DefaultPlace.class)
				.to(PageNameTokens.home);

		/**
		 * Auth request Factory
		 */
		bind(COAuthDataRequestFactory.class).in(Singleton.class);

		/**
		 * CER Version Factory
		 */
		bind(CurriculumRequestFactory.class).in(Singleton.class);

		/**
		 * holding client user
		 */
		bind(COClientUserHolder.class).in(Singleton.class);

		/**
		 * logged in gate keeper
		 */
		bind(LoggedInGatekeeper.class).in(Singleton.class);
		
		/**
		 * edit recommendation dialog
		 */
		bind(EditRecommendationWindow.class).in(Singleton.class);

		/**
		 * CO presenter bindings
		 */
		bindPresenter(COPagePresenter.class, COPagePresenter.MyView.class,
				COPageView.class, COPagePresenter.MyProxy.class);

		bindPresenter(COPageHeaderPresenter.class,
				COPageHeaderPresenter.MyView.class, COPageHeaderView.class,
				COPageHeaderPresenter.MyProxy.class);

		bindPresenter(COPageFooterPresenter.class,
				COPageFooterPresenter.MyView.class, COPageFooterView.class,
				COPageFooterPresenter.MyProxy.class);

		bindPresenterWidget(COLoginBarPresenter.class,
				COLoginBarPresenter.MyView.class, COLoginBarView.class);

		bindPresenter(COPageNotFoundPresenter.class,
				COPageNotFoundPresenter.MyView.class, COPageNotFoundView.class,
				COPageNotFoundPresenter.MyProxy.class);

		bindPresenterWidget(COSessionControllerPresenter.class,
				COSessionControllerPresenter.MyView.class,
				COSessionControllerView.class);

		bindPresenter(COHomePresenter.class, COHomePresenter.MyView.class,
				COHomeView.class, COHomePresenter.MyProxy.class);

		bindPresenter(COPageNotAuthorizedPresenter.class,
				COPageNotAuthorizedPresenter.MyView.class,
				COPageNotAuthorizedView.class,
				COPageNotAuthorizedPresenter.MyProxy.class);

		bindPresenter(COMyAppsPresenter.class, COMyAppsPresenter.MyView.class,
				COMyAppsView.class, COMyAppsPresenter.MyProxy.class);

		bindSingletonPresenterWidget(COMyAppsButtonPresenter.class,
				COMyAppsButtonPresenter.MyView.class, COMyAppsButtonView.class);
		
		bindSingletonPresenterWidget(ModRecButtonPresenter.class,
				ModRecButtonPresenter.MyView.class, ModRecButtonView.class);

		bindPresenter(ModRecHomePresenter.class,
				ModRecHomePresenter.MyView.class, ModRecHomeView.class,
				ModRecHomePresenter.MyProxy.class);

		bindPresenterWidget(CurriculumVersionListPresenter.class,
				CurriculumVersionListPresenter.MyView.class,
				CurriculumVersionListView.class);
		
		bindPresenterWidget(MyCurriculumVersionListPresenter.class,
				MyCurriculumVersionListPresenter.MyView.class,
				MyCurriculumVersionListView.class);
		
		bindPresenterWidget(CurriculumNodeGridPresenter.class,
				CurriculumNodeGridPresenter.MyView.class,
				CurriculumNodeGridView.class);
		
		bindPresenterWidget(CurriculumNodeTreePresenter.class,
				CurriculumNodeTreePresenter.MyView.class, CurriculumNodeTreeView.class);

		bindPresenter(CurriculumVersionSelectionPresenter.class,
				CurriculumVersionSelectionPresenter.MyView.class,
				CurriculumVersionSelectionView.class,
				CurriculumVersionSelectionPresenter.MyProxy.class);

		bindPresenterWidget(ModRecWelcomePresenter.class,
				ModRecWelcomePresenter.MyView.class, ModRecWelcomeView.class);
		
		bindPresenter(ModulePresenter.class,
				ModulePresenter.MyView.class, ModuleView.class,
				ModulePresenter.MyProxy.class);
	}
}
