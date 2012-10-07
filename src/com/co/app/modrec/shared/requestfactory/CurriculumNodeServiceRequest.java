package com.co.app.modrec.shared.requestfactory;

import java.util.List;

import com.co.app.modrec.domain.CurriculumNodeService;
import com.co.app.modrec.domain.CurriculumNodeServiceLocator;
import com.co.app.modrec.shared.proxy.CurriculumNodeProxy;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * Request Context for CurriculumNodeService
 * 
 * @author Lucas Reeh
 * 
 */
@Service(value = CurriculumNodeService.class, locator = CurriculumNodeServiceLocator.class)
public interface CurriculumNodeServiceRequest extends RequestContext {

	Request<List<CurriculumNodeProxy>> getTree(Long curriculumVersionId);

	Request<List<CurriculumNodeProxy>> getCurriculumNodesForGrid(
			List<Long> currentSelection);
	
	Request<List<CurriculumNodeProxy>> getCurriculumNodesByFatherIdForGrid(Long nodeFatherId);

	Request<List<CurriculumNodeProxy>> findCurriculumNodesForGrid(
			Long curriculumVersionId, String searchString);

	Request<List<CurriculumNodeProxy>> getRecommendedNodes(
			Long curriculumVersionId);
	
	Request<List<CurriculumNodeProxy>> getRecommendedNodesByLecturer(
			Long curriculumVersionId);
}
