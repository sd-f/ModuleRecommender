/**
 * 
 */
package com.co.app.modrec.domain;

import com.co.server.guice.requestfactory.inject.InjectingServiceLocator;
import com.google.inject.Injector;

/**
 * Default Service Locator for ModuleRecommenderService
 * 
 * @author Lucas Reeh
 *
 */
public class ModuleRecommenderServiceLocator extends InjectingServiceLocator {

	public ModuleRecommenderServiceLocator(Injector injector) {
		super(injector);
	}

	@Override
	public Object getInstance(Class<?> clazz) {
		return new ModuleRecommenderService();
	}
}
