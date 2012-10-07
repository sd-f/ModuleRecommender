package com.co.app.modrec.shared.proxy;

import com.co.app.modrec.domain.CurriculumVersion;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

/**
 * Proxy Class for CurriculumVersion
 * 
 * @author Lucas Reeh
 *
 */
@ProxyFor(CurriculumVersion.class)
public interface CurriculumVersionProxy extends ValueProxy {

	public abstract Long getCurriculumId();

	public abstract String getCurriculumIdentificator();

	public abstract String getCurriculumIntendedDegreeName();

	public abstract String getCurriculumVersionName();

	public abstract String getCurriculumVersionRefId();

}