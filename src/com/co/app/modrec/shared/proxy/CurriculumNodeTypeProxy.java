package com.co.app.modrec.shared.proxy;

import com.co.app.modrec.domain.CurriculumNodeType;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

/**
 * Proxy Class for CurriculumNodeType
 * 
 * @author Lucas Reeh
 *
 */
@ProxyFor(CurriculumNodeType.class)
public interface CurriculumNodeTypeProxy extends ValueProxy {

	public abstract Long getId();

	public abstract String getIconRefId();

	public abstract String getReferenceKey();

	public abstract String getName();

}