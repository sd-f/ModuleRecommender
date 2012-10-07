/**
 * 
 */
package com.co.app.cer.client.curriculum.version;

import com.co.app.modrec.shared.proxy.CurriculumVersionProxy;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * interface for accessing properties of class for curriculum version grid
 * 
 * @author Lucas Reeh
 *
 */
public interface CurriculumVersionProxyProperties extends
		PropertyAccess<CurriculumVersionProxy> {

	ModelKeyProvider<CurriculumVersionProxy> curriculumId();

	ValueProvider<CurriculumVersionProxy, String> curriculumIdentificator();
	
	ValueProvider<CurriculumVersionProxy, String> curriculumIntendedDegreeName();
	
	ValueProvider<CurriculumVersionProxy, String> curriculumVersionName();
	
	ValueProvider<CurriculumVersionProxy, String> curriculumVersionRefId();
}
