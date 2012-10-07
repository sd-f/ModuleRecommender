/**
 * 
 */
package com.co.app.modrec.domain;

import com.co.server.guice.requestfactory.inject.InjectingServiceLocator;
import com.google.inject.Injector;

/**
 * service locator class
 * 
 * @author Lucas Reeh
 *
 */
public class CurriculumNodeServiceLocator extends InjectingServiceLocator {

	public CurriculumNodeServiceLocator(Injector injector) {
		super(injector);
	}

	@Override
	public Object getInstance(Class<?> clazz) {
		return new CurriculumNodeService();
	}

}
