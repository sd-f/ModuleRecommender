/**
 * 
 */
package com.co.app.modrec.domain;

import java.util.ArrayList;
import java.util.List;

import com.co.app.modrec.server.auth.rights.ModRecRights;
import com.co.core.auth.server.helper.CurrentIdentity;
import com.google.inject.Inject;

/**
 * @author Lucas Reeh
 * 
 */
public class ModuleRecommenderService {

	/* current identity for right check */
	@Inject
	CurrentIdentity currentIdentity;

	/**
	 * persists Recommendation
	 * 
	 * @param nodes
	 * @param recNode
	 * @return true if ok
	 * @throws Exception 
	 */
	public Boolean persistRecommendation(List<CurriculumNode> nodes, CurriculumNode recNode) throws Exception {
		/** right check */
		ModRecRights.checkRights(currentIdentity);
		ModRecRights.checkObjectRightOnNode(currentIdentity, recNode);
		// casting to recommendation
		this.deleteRecommendation(recNode);
		List<ModuleRecommendation> rec = new ArrayList<ModuleRecommendation>();
		for (CurriculumNode node: nodes) {
			rec.add(new ModuleRecommendation(recNode.getNodeShortName(), node.getNodeShortName(), recNode.getCurriculumVersionId()));
		}
		if (rec.size() > 0)
			ModuleRecommendation.persist(rec);
		return true;

	}
	
	/**
	 * remove recommendation for node
	 * 
	 * @param recNode
	 * @return
	 * @throws Exception 
	 */
	public Boolean deleteRecommendation(CurriculumNode recNode) throws Exception {
		/** right check */
		ModRecRights.checkRights(currentIdentity);
		ModRecRights.checkObjectRightOnNode(currentIdentity, recNode);
		ModuleRecommendation.deleteByNode(recNode);
		return true;

	}

	/**
	 * get all recommendation requirements for node
	 * 
	 * @param recNode
	 * @return list of nodes required for recommendation
	 * @throws Exception 
	 */
	public List<CurriculumNode> getRecommendation(CurriculumNode recNode) throws Exception {
		/** right check */
		ModRecRights.checkObjectRightOnNode(currentIdentity, recNode);
		List<CurriculumNode> result = new ArrayList<CurriculumNode>();
		for (ModuleRecommendation rec: ModuleRecommendation.findByNode(recNode)) {
			result.add(CurriculumNode.findByNodeShortNameAndCurriculumVersionId(rec.getNodeShortNameReq(), rec.getCurriculumVersionId()));
		}
		return result;
	}
	
	/**
	 * returns number of students matching requirements
	 * 
	 * @param nodes
	 * @param recNode
	 * @return number of students matching
	 * @throws Exception
	 */
	public Integer getNumberOfStundentsMatching(List<CurriculumNode> nodes, CurriculumNode recNode) throws Exception {
		/** right check */
		ModRecRights.checkRights(currentIdentity);
		ModRecRights.checkObjectRightOnNode(currentIdentity, recNode);
		List<Long> nodeIds = new ArrayList<Long>();
		for (CurriculumNode node: nodes) {
			nodeIds.add(node.getId());
		}
		return ModuleRecommendation.getNumberOfStudentsMatching(nodeIds);

	}

}
