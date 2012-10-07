/**
 * 
 */
package com.co.app.cer.client.curriculum.node;

import com.co.app.modrec.shared.proxy.CurriculumNodeProxy;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * interface for accessing properties of class for grid
 * 
 * @author Lucas Reeh
 * 
 */
public interface CurriculumNodeProxySimpleProperties extends
		PropertyAccess<CurriculumNodeProxy> {

	ModelKeyProvider<CurriculumNodeProxy> id();

	ValueProvider<CurriculumNodeProxy, String> name();
	
	@Path("id")
	ValueProvider<CurriculumNodeProxy, Long> nodeId();
	
	ValueProvider<CurriculumNodeProxy, String> nodeShortName();
	
	ValueProvider<CurriculumNodeProxy, String> superNodeName();
	
	@Path("attendance")
	ValueProvider<CurriculumNodeProxy, Double> attendance();
	
	@Path("recommended")
	ValueProvider<CurriculumNodeProxy, Boolean> recommended();
	
	@Path("recommendedByLecturer")
	ValueProvider<CurriculumNodeProxy, Boolean> recommendedByLecturer();

	@Path("attendanceInactive")
	ValueProvider<CurriculumNodeProxy, Double> attendanceInactive();
	
	@Path("semesterType.refId")
	ValueProvider<CurriculumNodeProxy, String> semesterTypeId();
	
	@Path("semester")
	ValueProvider<CurriculumNodeProxy, Integer> semester();
	
	@Path("hasRecommendations")
	ValueProvider<CurriculumNodeProxy, String> hasRecommendations();

}
