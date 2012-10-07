package com.co.app.cer.client.curriculum.node;

import com.co.app.cer.shared.resources.COCerResources;
import com.co.app.cer.shared.resources.images.CurriculumNodeTypeIconRefID;
import com.co.app.modrec.shared.proxy.CurriculumNodeTypeProxy;
import com.google.gwt.resources.client.ImageResource;

/**
 * class for generating Icon for Curriculum Node depending on Type
 * 
 * @author lr86
 *
 */
public class CurriculumNodeTypeIcon {
	
	private CurriculumNodeTypeProxy type;
	
	public CurriculumNodeTypeIcon(CurriculumNodeTypeProxy curriculumNodeType) {
		super();
		type = curriculumNodeType;
	}

	public ImageResource getImageResource() {
		if (type == null) {
			return COCerResources.IMAGES.page_white();
		}
		if (type.getIconRefId() == null) {
			return COCerResources.IMAGES.page_white();
		}
		String iconRefID = type.getIconRefId();
		if (iconRefID.equals(CurriculumNodeTypeIconRefID.FLAG_BLUE)) {
			return COCerResources.IMAGES.flag_blue();
		}
		if (iconRefID.equals(CurriculumNodeTypeIconRefID.FLAG_GREEN)) {
			return COCerResources.IMAGES.flag_green();
		}
		if (iconRefID.equals(CurriculumNodeTypeIconRefID.FLAG_GREY)) {
			return COCerResources.IMAGES.flag_grey();
		}
		if (iconRefID.equals(CurriculumNodeTypeIconRefID.FLAG_ORANGE)) {
			return COCerResources.IMAGES.flag_orange();
		}
		if (iconRefID.equals(CurriculumNodeTypeIconRefID.FLAG_RED)) {
			return COCerResources.IMAGES.flag_red();
		}
		if (iconRefID.equals(CurriculumNodeTypeIconRefID.FORM_BLANK_YELLOW)) {
			return COCerResources.IMAGES.form_blank_yellow();
		}
		return COCerResources.IMAGES.page_white();
	}
	
	
	

}
