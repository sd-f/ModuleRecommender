/**
 * 
 */
package com.co.app.cer.client.curriculum.node.ui.grid;

import com.gwtplatform.mvp.client.UiHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * Interface for user interaction Handlers
 * 
 * interface holding all user interface Handlers of the CurriculumNodeGrid
 * 
 * @author Lucas Reeh
 *
 */
public interface CurriculumNodeGridUiHandlers extends UiHandlers {

	/**
	 * user clicked button Attendance
	 */
	void onAttendanceSelected(SelectEvent event);
	
	/**
	 * user clicked button AttendanceInactive
	 */
	void onAttendanceInactiveSelected(SelectEvent event);

	/**
	 * user clicked button SemesterType
	 */
	void onShowSemesterType(SelectEvent event);
	
	/**
	 * user clicked button Recommended
	 */
	void onShowRecommended(SelectEvent event);
	
	/**
	 * user clicked button RecommendedByLecturer
	 */
	void onShowRecommendedByLecturer(SelectEvent event);
	
	/**
	 * user clicked button Semester
	 */
	void onShowSemester(SelectEvent event);

	/**
	 * user clicked button EditRecommendation
	 */
	void onEditRecommendation(SelectEvent event);

}
