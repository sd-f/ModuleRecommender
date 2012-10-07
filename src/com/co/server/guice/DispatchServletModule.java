package com.co.server.guice;

import com.co.core.auth.server.factory.ActionInitUserServlet;
import com.co.core.auth.server.factory.RequestFactoryInitUserServlet;
import com.co.core.auth.server.helper.CurrentIdentity;
import com.co.core.server.util.exception.LoquaciousExceptionHandler;
import com.co.server.persistence.COPersistenceConstants;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.shared.Action;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.server.guice.HttpSessionSecurityCookieFilter;

/**
 * Class Servlet Module Dispatch
 * 
 * @author Lucas Reeh
 *
 */
public class DispatchServletModule extends ServletModule {

	/**
	 * servlet configuration set up
	 */
	@Override
	public void configureServlets() {
		/** standard dispatcher for gwtp */
		serve("/" + Action.DEFAULT_SERVICE_NAME)
				.with(ActionInitUserServlet.class);
		
		/** JPA */
		install(new JpaPersistModule(COPersistenceConstants.PERSISTENCE_UNIT_NAME));
		filter("/*").through(PersistFilter.class);
		
		/** custom exception */
		bind(LoquaciousExceptionHandler.class).in(Singleton.class);
		
		/** identity holder on server-side */
		bind(CurrentIdentity.class).in(Singleton.class);
		
		/** request factory */
		bind(RequestFactoryInitUserServlet.class);
		serve("/gwtRequest").with(RequestFactoryInitUserServlet.class);
		//filter("/gwtRequest").through(RequestFactoryInitUserServlet.class);
		
		/** security cookie filter for all servlets */
		bindConstant().annotatedWith(SecurityCookie.class).to("SecCookie");
		filter("*").through(HttpSessionSecurityCookieFilter.class);

		
	}
}
