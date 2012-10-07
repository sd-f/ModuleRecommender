package com.co.app.modrec.shared.proxy;

import com.co.app.modrec.domain.CurriculumNode;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * Proxy Class for CurriculumNode
 * 
 * @author Lucas Reeh
 *
 */
@ProxyFor(CurriculumNode.class)
public interface CurriculumNodeProxy extends EntityProxy {

	/**
	 * @return the Id
	 */
	public abstract Long getId();

	/**
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * @return the nodeType
	 */
	public abstract CurriculumNodeTypeProxy getType();
	
	/**
	 * @return the semesterType
	 */
	public abstract SemesterTypeProxy getSemesterType();
	
	/**
	 * @return the semesterType
	 */
	public abstract void setSemesterType(SemesterTypeProxy typeToSet);
	
	/**
	 * @return the superNodeId
	 */
	public abstract Long getSuperNodeId();
	
	/**
	 * @return the shortName
	 */
	public abstract String getNodeShortName();
	
	/**
	 * @return isLeaf
	 */
	public abstract Boolean isLeaf();
	
	/**
	 * @return superNodeName
	 */
	public abstract String getSuperNodeName();
	
	/**
	 * @return attendance
	 */
	public abstract Double getAttendance();
	
	/**
	 * @return attendance
	 */
	public abstract void setAttendance(Double attendanceToSet);
	
	/**
	 * @return attendance
	 */
	public abstract Double getAttendanceInactive();
	
	/**
	 * @return attendance
	 */
	public abstract void setAttendanceInactive(Double attendanceInactiveToSet);
	
	/**
	 * @param nameToSet
	 */
	public abstract void setSuperNodeName(String nameToSet);
	
	/**
	 * @return recommended
	 */
	public abstract Boolean isRecommended();
	
	/**
	 * @param recommended the recommended to set
	 */
	public abstract void setRecommended(Boolean recommended);
	
	/**
	 * @return recommended
	 */
	public abstract Boolean isRecommendedByLecturer();
	
	/**
	 * @param recommendedByLecturer the recommendedByLecturer to set
	 */
	public abstract void setRecommendedByLecturer(Boolean recommendedByLecturer);
	
	/**
	 * @return semester
	 */
	public abstract Integer getSemester();
	
	/**
	 * @param semester the semester to set
	 */
	public abstract void setSemester(Integer semester);
	
	/**
	 * HasRecommendations
	 * 
	 * @return HasRecommendations
	 */
	public abstract String getHasRecommendations();
	
	/**
	 * @param HasRecommendations to set
	 */
	public abstract void setHasRecommendations(String hasRecommendation);
	
	/**
	 * isLecturersModule
	 * 
	 * @return isLecturersModule
	 */
	public abstract Boolean isLecturersModule();
	
	/**
	 * @param isLecturersModule to set
	 */
	public abstract void setLecturersModule(Boolean lecturersModule);

}