package com.co.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.co.client.gin.ClientModule;
import com.google.gwt.inject.client.AsyncProvider;
import com.co.core.auth.shared.service.COAuthDataRequestFactory;
import com.co.core.home.client.COHomePresenter;
import com.co.core.page.client.COPagePresenter;
import com.co.core.page.client.footer.COPageFooterPresenter;
import com.co.core.page.client.header.COPageHeaderPresenter;
import com.google.inject.Provider;
import com.co.core.page.client.error.COPageNotFoundPresenter;
import com.co.core.page.client.error.COPageNotAuthorizedPresenter;
import com.co.core.myapps.client.COMyAppsPresenter;
import com.co.app.modrec.client.ModRecHomePresenter;
import com.co.app.modrec.shared.requestfactory.CurriculumRequestFactory;
import com.co.app.modrec.client.ui.widgets.CurriculumVersionSelectionPresenter;
import com.co.app.modrec.client.ui.widgets.ModulePresenter;

/**
 * Gin Client Modules
 * 
 * add all your presenter here
 * 
 * @author Lucas Reeh
 *
 */
@GinModules({ DispatchAsyncModule.class, ClientModule.class })
public interface ClientGinjector extends ClientGinjectorBase {

	COAuthDataRequestFactory getCOAuthDataRequestFactory();
	
	CurriculumRequestFactory getCerVersionFactory();
	
	AsyncProvider<COPagePresenter> getCOPagePresenter();

	AsyncProvider<COHomePresenter> getCOHomePresenter();

	AsyncProvider<COPageHeaderPresenter> getCOPageHeaderPresenter();

	AsyncProvider<COPageFooterPresenter> getCOPageFooterPresenter();

	Provider<COPageNotFoundPresenter> getCOPageNotFoundPresenter();

	AsyncProvider<COPageNotAuthorizedPresenter> getCOPageNotAuthorizedPresenter();

	AsyncProvider<COMyAppsPresenter> getCOMyAppsPresenter();

	AsyncProvider<ModRecHomePresenter> getModRecHomePresenter();
	
	AsyncProvider<ModulePresenter> getModulePresenter();
	
	AsyncProvider<CurriculumVersionSelectionPresenter> getCurriculumVersionSelectionPresenter();

}
