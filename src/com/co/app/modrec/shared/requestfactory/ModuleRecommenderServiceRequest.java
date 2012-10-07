package com.co.app.modrec.shared.requestfactory;

import java.util.List;

import com.co.app.modrec.domain.ModuleRecommenderService;
import com.co.app.modrec.domain.ModuleRecommenderServiceLocator;
import com.co.app.modrec.shared.proxy.CurriculumNodeProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * Request Context for ModuleRecommenderService
 * 
 * @author Lucas Reeh
 * 
 */
@Service(value = ModuleRecommenderService.class, locator = ModuleRecommenderServiceLocator.class)
public interface ModuleRecommenderServiceRequest extends RequestContext {

	Request<Boolean> persistRecommendation(
			List<CurriculumNodeProxy> recommendation, CurriculumNodeProxy recNode);
	
	Request<Boolean> deleteRecommendation(
			CurriculumNodeProxy recNode);
	
	Request<List<CurriculumNodeProxy>> getRecommendation(CurriculumNodeProxy recNode);

	Request<Integer> getNumberOfStundentsMatching(List<CurriculumNodeProxy> all,
			CurriculumNodeProxy node);
	
	
}
