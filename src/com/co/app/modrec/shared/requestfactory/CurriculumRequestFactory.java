/**
 * 
 */
package com.co.app.modrec.shared.requestfactory;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

/**
 * Module Recommender Request Factory Class
 * 
 * @author Lucas Reeh
 *
 */
public interface CurriculumRequestFactory extends RequestFactory {

	/**
	 * @return a request selector 
	 */
	CurriculumVersionServiceRequest curriculumVersionServiceRequest();
	
	/**
	 * @return a request selector 
	 */
	CurriculumNodeServiceRequest curriculumNodeServiceRequest();
	
	/**
	 * @return a request selector 
	 */
	ModuleRecommenderServiceRequest moduleRecommenderServiceRequest();
	
}
