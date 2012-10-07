package com.co.app.modrec.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.co.core.server.util.EMF;

/**
 * The persistent class for the curriculum nodes database table.
 * 
 */
@Entity
@Table(name = "MODREC_NODE_MV", schema = "CO_INSTALL")
@NamedQueries({
		@NamedQuery(name = "CurriculumNode.allByCurriculumVersionId", query = ""
				+ "  select node from CurriculumNode node"
				+ "   where node.curriculumVersionId = :pCurriculumVersionId"
				+ "     and node.typeId in :pNodeTypeIdFilter" +
				"      order by node.sortString"),
		@NamedQuery(name = "CurriculumNode.byCurrVersionIdAndNodeShortName", query = ""
				+ "  select node from CurriculumNode node"
				+ "   where node.curriculumVersionId = :pCurriculumVersionId"
				+ "     and node.nodeShortName = :pNodeShortName"),
		@NamedQuery(name = "CurriculumNode.byCurrVersionIdAndNodeShortNames", query = ""
				+ "  select node from CurriculumNode node"
				+ "   where node.curriculumVersionId = :pCurriculumVersionId"
				+ "     and node.nodeShortName in :pNodeShortNameList" +
				"       and node.typeId in :pNodeTypeIdFilter"),
		@NamedQuery(name = "CurriculumNode.children", query = ""
				+ "  select node from CurriculumNode node"
				+ "   where node.superNodeId = :pNodeId"
				+ "       and node.typeId in :pNodeTypeIdFilter"),
		@NamedQuery(name = "CurriculumNode.search", query = ""
				+ "  select node from CurriculumNode node"
				+ "   where node.curriculumVersionId = :pCurriculumVersionId"
				+ "       and node.typeId in :pNodeTypeIdFilter"
				+ "       and ( ( upper(node.name) like upper(:pSearchString) )"
				+ "           or ( upper(node.nodeShortName) like upper(:pSearchString) ) )"),
		@NamedQuery(name = "CurriculumNode.childrenByList", query = ""
				+ "  select node from CurriculumNode node"
				+ "   where node.id in :pNodesList"
				+ "       and node.typeId in :pNodeTypeIdFilter") })
public class CurriculumNode implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @return the serialversionuid
	 */
	public long getVersion() {
		return serialVersionUID;
	}

	/* primary key attribute */
	@Id
	private Long id;

	/* node name */
	@Column(name = "NAME", updatable = false, insertable = false)
	private String name;

	/* curriculum version */
	@Column(name = "curriculum_version_id", updatable = false, insertable = false)
	private Long curriculumVersionId;

	/* super node id */
	@Column(name = "super_node_id", updatable = false, insertable = false)
	private Long superNodeId;

	/* node short name (module id) */
	@Column(name = "node_shortname", updatable = false, insertable = false)
	private String nodeShortName;

	/* type of node */
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = CurriculumNodeType.class)
	@JoinColumn(name = "node_type_id")
	private CurriculumNodeType type;

	/* node type id for joining */
	@Column(name = "node_type_id", updatable = false, insertable = false)
	private Long typeId;

	/* subject type (compulsory, mandatory, etc..) */
	@Column(name = "SUBJECT_TYPE_NAME", updatable = false, insertable = false)
	private String subjectTypeName;

	/* subject type short description (compulsory, mandatory, etc..) */
	@Column(name = "SUBJECT_TYPE_REF_ID", updatable = false, insertable = false)
	private String subjectTypeReferenceId;
	
	/* string for sorting nodes e.g. in tree */
	@Column(name = "SORTSTRING", updatable = false, insertable = false)
	private String sortString;

	/* super node */
	@ManyToOne
	@JoinColumn(name = "super_node_id")
	private CurriculumNode superNode;

	/* semester type (recommended semester) */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = SemesterType.class)
	@JoinColumn(name = "semester_type_id")
	private SemesterType semesterType = new SemesterType();

	/* actual semester attended by students */
	@Column(name = "semester", updatable = false, insertable = false)
	private Integer semester = 0;

	/* student attendacne figure for node */
	@Column(name = "attendance_figure", updatable = false, insertable = false)
	private Double attendance = new Double(0);

	/* inactive (ex-)students attendance figure */
	@Column(name = "attendance_inactive_figure", updatable = false, insertable = false)
	private Double attendanceInactive = new Double(0);

	/* transfer attributes (calculated, transient) */
	@Transient
	private Boolean recommended = false;

	@Transient
	private String superNodeName;

	@Transient
	private Boolean recommendedByLecturer = false;
	
	@Transient
	private String hasRecommendations = "";
	
	@Transient
	private Boolean lecturersModule = false;


	/**
	 * class constructor
	 */
	public CurriculumNode() {
	}

	/**
	 * 
	 * @param id
	 * @return Curriculum version by ID
	 */
	public static CurriculumNode findCurriculumNode(Long id) {
		if (id == null) {
			return null;
		}
		return EMF.getEm().find(CurriculumNode.class, id);
	}

	/**
	 * find node by node short name inside given curriculum version id
	 * 
	 * @param nodeShortName
	 * @param curriculumVersionId
	 * 
	 * @return node
	 */
	public static CurriculumNode findByNodeShortNameAndCurriculumVersionId(
			String nodeShortName, Long curriculumVersionId) {
		TypedQuery<CurriculumNode> query = EMF.getEm().createNamedQuery(
				"CurriculumNode.byCurrVersionIdAndNodeShortName",
				CurriculumNode.class);
		query.setParameter("pNodeShortName", nodeShortName);
		query.setParameter("pCurriculumVersionId", curriculumVersionId);
		return query.getSingleResult();
	}

	/**
	 * find all module nodes by super node id
	 * 
	 * @param nodeId
	 * @return list of nodes
	 */
	public static List<CurriculumNode> findModuleNodesByFatherNodeId(Long nodeId) {
		EntityManager em = EMF.getEm();
		if (nodeId == null) {
			return new ArrayList<CurriculumNode>();
		}
		TypedQuery<CurriculumNode> query = em.createNamedQuery(
				"CurriculumNode.children", CurriculumNode.class);
		query.setParameter("pNodeId", nodeId);
		query.setParameter("pNodeTypeIdFilter",
				CurriculumNodeType.findModuleTypeIds());
		List<CurriculumNode> nodes = query.getResultList();
		return nodes;
	}

	/**
	 * find all module nodes inside curriculum version by search string
	 * 
	 * name / name short
	 * 
	 * @param curriculumVersionId
	 * @param searchString
	 * @return list of nodes
	 */
	public static List<CurriculumNode> findModuleNodesBySearchString(
			Long curriculumVersionId, String searchString) {
		EntityManager em = EMF.getEm();
		if (searchString == null) {
			return new ArrayList<CurriculumNode>();
		}
		if (curriculumVersionId == null) {
			return new ArrayList<CurriculumNode>();
		}
		TypedQuery<CurriculumNode> query = em.createNamedQuery(
				"CurriculumNode.search", CurriculumNode.class);
		query.setParameter("pCurriculumVersionId", curriculumVersionId);
		query.setParameter("pSearchString", "%" + searchString + "%");
		query.setParameter("pNodeTypeIdFilter",
				CurriculumNodeType.findModuleTypeIds());

		List<CurriculumNode> nodes = query.getResultList();
		return nodes;
	}

	/**
	 * find all module nodes by given node list 
	 * 
	 * @param currentSelection
	 * @return list of nodes
	 */
	public static List<CurriculumNode> findModuleNodesByNodeIdsList(
			List<Long> nodeIdsList) {
		EntityManager em = EMF.getEm();
		if (nodeIdsList == null) {
			return new ArrayList<CurriculumNode>();
		}
		if (nodeIdsList.size() <= 0) {
			return new ArrayList<CurriculumNode>();
		}
		TypedQuery<CurriculumNode> query = em.createNamedQuery(
				"CurriculumNode.childrenByList", CurriculumNode.class);
		query.setParameter("pNodesList", nodeIdsList);
		query.setParameter("pNodeTypeIdFilter",
				CurriculumNodeType.findModuleTypeIds());
		List<CurriculumNode> nodes = query.getResultList();

		return nodes;
	}

	/**
	 * find all non module nodes by curriculum version id
	 * 
	 * @param curriculumVersionId
	 * @return (flat) list of nodes 
	 */
	public static List<CurriculumNode> findAllNonModuleNodesByCurrVersId(
			Long curriculumVersionId) {
		EntityManager em = EMF.getEm();
		if (curriculumVersionId == null) {
			return new ArrayList<CurriculumNode>();
		}
		TypedQuery<CurriculumNode> query = em
				.createNamedQuery("CurriculumNode.allByCurriculumVersionId",
						CurriculumNode.class);
		query.setParameter("pNodeTypeIdFilter",
				CurriculumNodeType.findNonModuleTypeIds());
		query.setParameter("pCurriculumVersionId", curriculumVersionId);
		List<CurriculumNode> nodes = query.getResultList();
		return nodes;
	}
	
	/**
	 * find nodes filtered by list of node short names
	 * 
	 * @param nodeShortNameList
	 * @return list of nodes
	 */
	public static List<CurriculumNode> findSubNodesByNodeShortNames(
			Long curriculumVersionId, List<String> nodeShortNameList) {
		if (nodeShortNameList.size()<=0) {
			return new ArrayList<CurriculumNode>();
		}
		TypedQuery<CurriculumNode> query = EMF.getEm().createNamedQuery(
				"CurriculumNode.byCurrVersionIdAndNodeShortNames",
				CurriculumNode.class);
		query.setParameter("pNodeTypeIdFilter",
				CurriculumNodeType.findModuleTypeIds());
		query.setParameter("pCurriculumVersionId", curriculumVersionId);
		query.setParameter("pNodeShortNameList", nodeShortNameList);
		return query.getResultList();
	}

	/**
	 * @return true if Node is leaf in tree
	 */
	public Boolean isLeaf() {
		return false;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public CurriculumNodeType getType() {
		return type;
	}

	/**
	 * @return the superNodeId
	 */
	public Long getSuperNodeId() {
		return superNodeId;
	}

	/**
	 * @param superNodeId
	 *            the superNodeId to set
	 */
	public void setSuperNodeId(Long superNodeId) {
		this.superNodeId = superNodeId;
	}

	/**
	 * @return the curriculumVersionId
	 */
	public Long getCurriculumVersionId() {
		return curriculumVersionId;
	}

	/**
	 * @return the nodeShortName
	 */
	public String getNodeShortName() {
		return nodeShortName;
	}

	/**
	 * @param nodeShortName
	 *            the nodeShortName to set
	 */
	public void setNodeShortName(String nodeShortName) {
		this.nodeShortName = nodeShortName;
	}

	/**
	 * @return the superNodeName
	 */
	public String getSuperNodeName() {
		return superNodeName;
	}

	/**
	 * @param superNodeName
	 *            the superNodeName to set
	 */
	public void setSuperNodeName(String superNodeName) {
		this.superNodeName = superNodeName;
	}

	/**
	 * @return the attendance
	 */
	public Double getAttendance() {
		return attendance;
	}

	/**
	 * @param attendance
	 *            the attendance to set
	 */
	public void setAttendance(Double attendance) {
		this.attendance = attendance;
	}

	/**
	 * @return the attendanceInactive
	 */
	public Double getAttendanceInactive() {
		return attendanceInactive;
	}

	/**
	 * @param attendanceInactive
	 *            the attendanceInactive to set
	 */
	public void setAttendanceInactive(Double attendanceInactive) {
		this.attendanceInactive = attendanceInactive;
	}

	/**
	 * @return the semesterType
	 */
	public SemesterType getSemesterType() {
		return semesterType;
	}

	/**
	 * @param semesterType
	 *            the semesterType to set
	 */
	public void setSemesterType(SemesterType semesterType) {
		this.semesterType = semesterType;
	}

	/**
	 * @return the recommended
	 */
	public Boolean isRecommended() {
		return recommended;
	}

	/**
	 * @param recommended
	 *            the recommended to set
	 */
	public void setRecommended(Boolean recommended) {
		this.recommended = recommended;
	}

	/**
	 * @return the recommendedByLecturer
	 */
	public Boolean isRecommendedByLecturer() {
		return recommendedByLecturer;
	}

	/**
	 * @param recommendedByLecturer
	 *            the recommendedByLecturer to set
	 */
	public void setRecommendedByLecturer(Boolean recommendedByLecturer) {
		this.recommendedByLecturer = recommendedByLecturer;
	}

	/**
	 * @return the semester
	 */
	public Integer getSemester() {
		return semester;
	}

	/**
	 * @param semester
	 *            the semester to set
	 */
	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	/**
	 * @return the hasRecommendations
	 */
	public String getHasRecommendations() {
		return hasRecommendations;
	}

	/**
	 * @param hasRecommendations the hasRecommendations to set
	 */
	public void setHasRecommendations(String hasRecommendations) {
		this.hasRecommendations = hasRecommendations;
	}

	/**
	 * @return the lecturersModule
	 */
	public Boolean isLecturersModule() {
		return lecturersModule;
	}

	/**
	 * @param lecturersModule the lecturersModule to set
	 */
	public void setLecturersModule(Boolean lecturersModule) {
		this.lecturersModule = lecturersModule;
	}

	

}