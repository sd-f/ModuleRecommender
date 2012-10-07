/**
 * 
 */
package com.co.app.cer.client.curriculum.node;

import java.util.Set;

import com.co.app.modrec.shared.resources.COModuleRecommenderResources;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Image;

/**
 * abstract cell class for rendering recommendation icon
 * 
 * @author Lucas Reeh
 * 
 */
public class RecommendedByLecturerChartCell extends AbstractCell<Boolean> {

	final AbstractImagePrototype imageRes = AbstractImagePrototype.create(
			COModuleRecommenderResources.IMAGES.star_16());
	
	final Image image = imageRes.createImage();
	
	/**
	 * @param consumedEvents
	 */
	public RecommendedByLecturerChartCell(Set<String> consumedEvents) {
		super(consumedEvents);
		image.setTitle(COModuleRecommenderResources.TEXT.recommendedByLecturer());
	}

	/**
	 * @param consumedEvents
	 */
	public RecommendedByLecturerChartCell(String... consumedEvents) {
		super(consumedEvents);
		image.setTitle(COModuleRecommenderResources.TEXT.recommendedByLecturer());
	}

	@Override
	public void render(Context context, Boolean value, SafeHtmlBuilder sb) {
		SafeHtml html = SafeHtmlUtils.fromTrustedString(image.toString());
		if (value)
			sb.append(html);
	}
}
