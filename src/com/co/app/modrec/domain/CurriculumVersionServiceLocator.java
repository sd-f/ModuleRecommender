/**
 * 
 */
package com.co.app.modrec.domain;

import com.co.server.guice.requestfactory.inject.InjectingServiceLocator;
import com.google.inject.Injector;

/**
 * Default Service Locator for CurriculumVersionService
 * 
 * @author Lucas Reeh
 *
 */
public class CurriculumVersionServiceLocator extends InjectingServiceLocator {

	public CurriculumVersionServiceLocator(Injector injector) {
		super(injector);
	}

	@Override
	public Object getInstance(Class<?> clazz) {
		return new CurriculumVersionService();
	}

}
