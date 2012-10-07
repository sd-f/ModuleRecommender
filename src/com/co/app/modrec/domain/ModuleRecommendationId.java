/**
 * 
 */
package com.co.app.modrec.domain;

import java.io.Serializable;

/**
 * primary key class for embeddable module recommendation
 * 
 * @author Lucas Reeh
 * 
 */
public class ModuleRecommendationId implements Serializable {

	private static final long serialVersionUID = -717607351141665374L;
	
	// primary key (compound)
	private String nodeShortName;
	private String nodeShortNameReq;
	private Long curriculumVersionId;

	/**
	 * hashCode for identifying primary key
	 */
	public int hashCode() {
		return (int) nodeShortName.hashCode() + nodeShortNameReq.hashCode()
				+ curriculumVersionId.intValue();
	}

	/**
	 * compare primary keys for unique checks
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ModuleRecommendationId))
			return false;
		ModuleRecommendationId pk = (ModuleRecommendationId) obj;
		return pk.curriculumVersionId == curriculumVersionId
				&& pk.nodeShortName.equals(nodeShortName)
				&& pk.nodeShortNameReq.equals(nodeShortNameReq);
	}

}
