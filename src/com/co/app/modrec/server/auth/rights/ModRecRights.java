/**
 * 
 */
package com.co.app.modrec.server.auth.rights;

import com.co.app.modrec.domain.CurriculumNode;
import com.co.app.modrec.domain.CurriculumVersion;
import com.co.core.auth.server.helper.CurrentIdentity;
import com.co.core.auth.shared.resources.text.COServerAuthResources;

/**
 * Right check class for module recommender application
 * 
 * @author Lucas Reeh
 * 
 */
public class ModRecRights {

	/**
	 * check if user is identified
	 * 
	 * @param currentIdentity
	 * @throws Exception
	 */
	public static void checkRights(CurrentIdentity currentIdentity)
			throws Exception {
		if (currentIdentity == null) {
			throw new Exception(COServerAuthResources.text()
					.msgNoRightOnService());
		} else if (currentIdentity.getIdentity() == null) {
			throw new Exception(COServerAuthResources.text()
					.msgNoRightOnService());
		}
	}

	/**
	 * check if current user is staff and owns given given node
	 * 
	 * @param currentIdentity
	 * @param node
	 * @throws Exception
	 */
	public static void checkObjectRightOnNode(CurrentIdentity currentIdentity,
			CurriculumNode node) throws Exception {
		if (currentIdentity == null) {
			throw new Exception(COServerAuthResources.text()
					.msgNoRightOnService());
		} else if (currentIdentity.getIdentity() == null) {
			throw new Exception(COServerAuthResources.text()
					.msgNoRightOnService());
		}
		if (!currentIdentity.getIdentity().isStaff()) {
			throw new Exception(COServerAuthResources.text()
					.msgNoRightOnService());
		}
		if (!CurriculumVersion.isLecturersModule(node.getCurriculumVersionId(),
				currentIdentity.getIdentity().getStaffId(),
				node.getNodeShortName())) {
			throw new Exception(COServerAuthResources.text()
					.msgNoRightOnService());
		}
	}
}
