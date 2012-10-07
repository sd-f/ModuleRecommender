/**
 * 
 */
package com.co.app.modrec.domain;

import java.util.ArrayList;
import java.util.List;

import com.co.app.cer.shared.resources.COCerServerResources;
import com.co.app.modrec.server.auth.rights.ModRecRights;
import com.co.app.stm.domain.Student;
import com.co.core.auth.server.helper.CurrentIdentity;
import com.google.common.base.Joiner;
import com.google.inject.Inject;

/**
 * class for request factory service for curriculum nodes
 * 
 * @author Lucas Reeh
 * 
 */
public class CurriculumNodeService {

	/* current identity for right check */
	@Inject
	CurrentIdentity currentIdentity;

	/**
	 * returns list of curriculum nodes by node father id
	 * 
	 * @param nodeFatherId
	 * @return
	 * @throws Exception
	 */
	public List<CurriculumNode> getCurriculumNodesByFatherIdForGrid(
			Long nodeFatherId) throws Exception {
		List<CurriculumNode> result = CurriculumNode
				.findModuleNodesByFatherNodeId(nodeFatherId);
		CurriculumNode fatherNode = CurriculumNode
				.findCurriculumNode(nodeFatherId);
		
		// set attributes in nodes
		for (CurriculumNode node : result) {
			
			// load recommendations for each node and set attribute
			List<ModuleRecommendation> rec = ModuleRecommendation
					.findByNodeShortName(node.getNodeShortName(),
							node.getCurriculumVersionId());
			node.setHasRecommendations(Joiner.on(",").join(rec));
			
			// staff user gets info if it is his module
			if (isStaff()) {
				node.setLecturersModule(CurriculumVersion.isLecturersModule(node.getCurriculumVersionId(),
						currentIdentity.getIdentity().getStaffId(),
						node.getNodeShortName()));
			}
			node.setSuperNodeName(fatherNode.getName());
		}
		return result;
	}

	/**
	 * returns list of curriculum nodes by node list
	 * 
	 * @param currentSelection
	 * @return
	 * @throws Exception
	 */
	public List<CurriculumNode> getCurriculumNodesForGrid(
			List<Long> currentSelection) throws Exception {
		List<CurriculumNode> result = CurriculumNode
				.findModuleNodesByNodeIdsList(currentSelection);
		
		// if user is staff module recommendations for each node
		if (isStaff()) {
			for (CurriculumNode node : result) {
				List<ModuleRecommendation> rec = ModuleRecommendation
						.findByNodeShortName(node.getNodeShortName(),
								node.getCurriculumVersionId());
				node.setHasRecommendations(Joiner.on(",").join(rec));
				
				// if it is lecturers own node attribute is set
				node.setLecturersModule(CurriculumVersion.isLecturersModule(node.getCurriculumVersionId(),
						currentIdentity.getIdentity().getStaffId(),
						node.getNodeShortName()));
			}
		}

		return result;
	}

	/**
	 * returns list of curriculum nodes by search string
	 * 
	 * @param curriculumVersionId
	 * @param searchString
	 * @return
	 */
	public List<CurriculumNode> findCurriculumNodesForGrid(
			Long curriculumVersionId, String searchString) {
		List<CurriculumNode> result = CurriculumNode.findModuleNodesBySearchString(
				curriculumVersionId, searchString);
		CurriculumNode fatherNode;
		
		// setting father node name for grouping
		for (CurriculumNode node : result) {
			if (node.getSuperNodeId() != null) {
				fatherNode = CurriculumNode.findCurriculumNode(node
						.getSuperNodeId());
				if (fatherNode != null)
					node.setSuperNodeName(fatherNode.getName());
			}
		}
		
		// if user is staff module recommendations for each node
		if (isStaff()) {
			for (CurriculumNode node : result) {
				List<ModuleRecommendation> rec = ModuleRecommendation
						.findByNodeShortName(node.getNodeShortName(),
								node.getCurriculumVersionId());
				node.setHasRecommendations(Joiner.on(",").join(rec));
				
				// if it is lecturers own node attribute is set
				node.setLecturersModule(CurriculumVersion.isLecturersModule(node.getCurriculumVersionId(),
						currentIdentity.getIdentity().getStaffId(),
						node.getNodeShortName()));
			}
		}
		return result;
	}

	/**
	 * returns list of curriculum nodes recommended by lecturer
	 * 
	 * @param curriculumVersionId
	 * @return
	 * @throws Exception
	 */
	public List<CurriculumNode> getRecommendedNodesByLecturer(
			Long curriculumVersionId) throws Exception {
		ModRecRights.checkRights(currentIdentity);
		List<CurriculumNode> result = new ArrayList<CurriculumNode>();
		
		// setting super node name for grouping
		if (isStudent()) {
			Student student = Student.find(currentIdentity.getIdentity()
					.getStudentId());
			if (student != null) {
				result = CurriculumNode
						.findSubNodesByNodeShortNames(
								curriculumVersionId,
								student.getLecturerRecommendations(curriculumVersionId));
			}
			for (CurriculumNode nodeRecommended : result) {
				nodeRecommended.setRecommendedByLecturer(true);
				nodeRecommended.setSuperNodeName(COCerServerResources.text()
						.recommendedModules());
			}
		} else {
			// if user is staff module recommendations for each node
			if (isStaff()) {
				result = CurriculumNode.findSubNodesByNodeShortNames(
						curriculumVersionId, CurriculumVersion
								.getLecturerModulesVersionId(
										curriculumVersionId, currentIdentity
												.getIdentity().getStaffId()));
				CurriculumNode fatherNode;
				// setting father node name for grouping and attributes for module ownership
				for (CurriculumNode node : result) {
					node.setRecommendedByLecturer(true);
					node.setLecturersModule(CurriculumVersion.isLecturersModule(node.getCurriculumVersionId(),
							currentIdentity.getIdentity().getStaffId(),
							node.getNodeShortName()));
					List<ModuleRecommendation> rec = ModuleRecommendation
							.findByNodeShortName(node.getNodeShortName(),
									node.getCurriculumVersionId());
					node.setHasRecommendations(Joiner.on(",").join(rec));
					if (node.getSuperNodeId() != null) {
						fatherNode = CurriculumNode.findCurriculumNode(node
								.getSuperNodeId());
						if (fatherNode != null)
							node.setSuperNodeName(fatherNode.getName());
					}
				}
			}
		}

		return result;
	}

	/**
	 * returns list of curriculum nodes recommended (like other students)
	 * 
	 * @param currentSelection
	 * @return
	 * @throws Exception
	 */
	public List<CurriculumNode> getRecommendedNodes(Long curriculumVersionId)
			throws Exception {
		ModRecRights.checkRights(currentIdentity);

		List<CurriculumNode> result = new ArrayList<CurriculumNode>();
		if (isStudent()) {
			Student student = Student.find(currentIdentity.getIdentity()
					.getStudentId());
			List<Long> mymodules = student
					.getNodesAttendedByVersionId(curriculumVersionId);

			result.addAll(CurriculumNode.findModuleNodesByNodeIdsList(student
					.getNodesRecommendedByVersionId(curriculumVersionId)));
			for (CurriculumNode nodeRecommended : result) {
				for (Long nodeAttended : mymodules) {
					if (nodeAttended.equals(nodeRecommended.getId())) {
						nodeRecommended.setSuperNodeName(COCerServerResources
								.text().attendedModules());
					} else {
						nodeRecommended.setRecommended(true);
						nodeRecommended.setSuperNodeName(COCerServerResources
								.text().recommendedModules());
					}
				}
			}
		}

		return result;
	}

	/**
	 * returns list of nodes for rule nodes in tree
	 * 
	 * @param curriculumVersionId
	 * @return
	 * @throws Exception
	 */
	public List<CurriculumNode> getTree(Long curriculumVersionId)
			throws Exception {
		return CurriculumNode
				.findAllNonModuleNodesByCurrVersId(curriculumVersionId);
	}

	/**
	 * @return true if current user is staff
	 */
	private Boolean isStaff() {
		if (currentIdentity != null) {
			if (currentIdentity.getIdentity() != null) {
				return currentIdentity.getIdentity().isStaff();
			}
		}
		return false;
	}

	/**
	 * @return true if current user is student
	 */
	private Boolean isStudent() {
		if (currentIdentity != null) {
			if (currentIdentity.getIdentity() != null) {
				return currentIdentity.getIdentity().isStudent();
			}
		}
		return false;
	}

}
