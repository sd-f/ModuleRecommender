/**
 * 
 */
package com.co.app.modrec.shared.proxy;

import com.co.app.modrec.domain.SemesterType;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * Proxy Class for SemesterType
 * 
 * @author Lucas Reeh
 *
 */
@ProxyFor(SemesterType.class)
public interface SemesterTypeProxy extends EntityProxy {
	
	/**
	 * @return the Id
	 */
	public abstract Long getId();

	/**
	 * @return the name
	 */
	public abstract String getRefId();
	
	/**
	 * @return the name
	 */
	public abstract String getName();

}
