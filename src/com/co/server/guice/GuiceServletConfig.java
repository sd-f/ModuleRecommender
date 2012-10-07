package com.co.server.guice;

import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.Injector;
import com.google.inject.Guice;
import com.co.server.guice.ServerModule;
import com.co.server.guice.DispatchServletModule;

/**
 * Class Guice Servlet Config
 * 
 * @author Lucas Reeh
 *
 */
public class GuiceServletConfig extends GuiceServletContextListener {

	/**
	 * @return the injector
	 */
	@Override
	protected Injector getInjector() {
		return Guice
				.createInjector(new ServerModule(), new DispatchServletModule());
	}
}
