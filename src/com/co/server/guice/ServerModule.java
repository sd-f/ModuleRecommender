package com.co.server.guice;

import com.gwtplatform.dispatch.server.guice.HandlerModule;
import com.co.core.auth.client.action.DoLogin;
import com.co.core.auth.server.action.DoLoginActionHandler;
import com.co.core.auth.client.action.GetCOClientUser;
import com.co.core.auth.server.action.GetCOClientUserActionHandler;
import com.co.core.auth.client.action.DoLogout;
import com.co.core.auth.server.action.DoLogoutActionHandler;
import com.co.core.auth.server.action.validator.InitActionValidator;

/**
 * Class Server Module
 * 
 * @author Lucas Reeh
 *
 */
public class ServerModule extends HandlerModule {

	/**
	 * configure (set up) servlet handlers
	 */
	@Override
	protected void configureHandlers() {

		bindHandler(DoLogin.class, DoLoginActionHandler.class);

		bindHandler(DoLogout.class, DoLogoutActionHandler.class,
				InitActionValidator.class);
		
		bindHandler(GetCOClientUser.class, GetCOClientUserActionHandler.class, InitActionValidator.class);

		
	}

	
}
