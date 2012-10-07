package com.co.app.modrec.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

import com.co.app.modrec.shared.proxy.CurriculumVersionProxy;
import com.co.app.stm.domain.Student;
import com.co.core.server.util.EMF;
import com.co.core.server.util.Paginate;
import com.sencha.gxt.data.shared.SortInfoBean;
import com.sencha.gxt.data.shared.loader.FilterConfigBean;

/**
 * The persistent class for curriculum versions database table
 * 
 * @author Lucas Reeh
 */
@Entity
@Table(name = "MODREC_CURRICULUM_VERSION_MV", schema = "CO_INSTALL")
@NamedQueries({ @NamedQuery(name = "CurriculumVersion.findAll", query = "SELECT o FROM CurriculumVersion o") })
@NamedNativeQueries({
		@NamedNativeQuery(name = "CurriculumVersion.recModules", query = ""
				+ " select distinct m.node_shortname"
				+ "   from modrec_module_node_mv m"
				+ "      , ( select distinct m.node_shortname"
				+ "               , m.curriculum_version_id"
				+ "            from module_recommendations m ) rec"
				+ "  where rec.node_shortname = m.node_shortname"
				+ "    and rec.curriculum_version_id = m.curriculum_version_id"
				+ "    and m.curriculum_version_id =  ?1"),
		@NamedNativeQuery(name = "CurriculumVersion.recLecturerModules", query = ""
				+ " select distinct m.node_shortname"
				+ "   from modrec_module_node_mv m"
				+ "      , modrec_lecturer_node_mv lect"
				+ "  where lect.node_id = m.node_id"
				+ "    and m.curriculum_version_id = ?1"
				+ "    and lect.staff_id = ?2"),
		@NamedNativeQuery(name = "CurriculumVersion.recLecturerModulesByShortName", query = ""
				+ " select distinct m.node_shortname"
				+ "   from modrec_module_node_mv m"
				+ "      , modrec_lecturer_node_mv lect"
				+ "  where lect.node_id = m.node_id"
				+ "    and m.curriculum_version_id = ?1"
				+ "    and lect.staff_id = ?2"
				+ "      and m.node_shortname = ?3") })
public class CurriculumVersion implements Serializable, CurriculumVersionProxy {

	private static final long serialVersionUID = 1L;

	// primary key
	@Id
	private Long curriculumId;

	// curriculum identificator
	private String curriculumIdentificator;

	// intended degree
	private String curriculumIntendedDegreeName;

	// name
	private String curriculumVersionName;

	// version reference id
	private String curriculumVersionRefId;

	/**
	 * class constructor
	 */
	public CurriculumVersion() {
	}

	/**
	 * load curriculum version
	 * 
	 * @param id
	 * @return Curriculum version by ID
	 */
	public static CurriculumVersionProxy findCurriculumVersion(Long id) {
		if (id == null) {
			return null;
		}
		return EMF.getEm().find(CurriculumVersion.class, id);
	}

	/**
	 * @return all Curriculum versions
	 */
	public static List<CurriculumVersion> findAllCurriculumVersions() {
		EntityManager em = EMF.getEm();
		TypedQuery<CurriculumVersion> query = em.createNamedQuery(
				"CurriculumVersion.findAll", CurriculumVersion.class);
		List<CurriculumVersion> results = query.getResultList();
		return results;
	}

	/**
	 * GXT parameterized curriculum version service method
	 * 
	 * @param offset
	 * @param limit
	 * @param sortInfo
	 * @param filterConfig
	 * @param resultCount
	 * @return list of curriculum version
	 */
	public static List<CurriculumVersion> findFilteredAndSorted(int offset,
			int limit, List<SortInfoBean> sortInfo,
			List<FilterConfigBean> filterConfig, Integer resultCount) {
		EntityManager em = EMF.getEm();
		Paginate<CurriculumVersion> pag = new Paginate<CurriculumVersion>(em);

		/** default sorting */
		SortInfoBean defaultSort = new SortInfoBean();
		defaultSort.setSortField("curriculumIdentificator");
		if (sortInfo.size() == 1) {
			if (sortInfo.get(0).getSortField()
					.equals("curriculumIntendedDegreeName")) {
				sortInfo.add(defaultSort);
			}
		}
		List<CurriculumVersion> results = pag.paginate(CurriculumVersion.class,
				offset, limit, sortInfo, filterConfig, resultCount);
		return results;
	}

	@Override
	public Long getCurriculumId() {
		return this.curriculumId;
	}

	public void setCurriculumId(Long curriculumId) {
		this.curriculumId = curriculumId;
	}

	@Override
	public String getCurriculumIdentificator() {
		return this.curriculumIdentificator;
	}

	public void setCurriculumIdentificator(String curriculumIdentificator) {
		this.curriculumIdentificator = curriculumIdentificator;
	}

	@Override
	public String getCurriculumIntendedDegreeName() {
		return this.curriculumIntendedDegreeName;
	}

	public void setCurriculumIntendedDegreeName(
			String curriculumIntendedDegreeName) {
		this.curriculumIntendedDegreeName = curriculumIntendedDegreeName;
	}

	@Override
	public String getCurriculumVersionName() {
		return this.curriculumVersionName;
	}

	public void setCurriculumVersionName(String curriculumVersionName) {
		this.curriculumVersionName = curriculumVersionName;
	}

	@Override
	public String getCurriculumVersionRefId() {
		return this.curriculumVersionRefId;
	}

	public void setCurriculumVersionRefId(String curriculumVersionRefId) {
		this.curriculumVersionRefId = curriculumVersionRefId;
	}

	/**
	 * returns students curriculum versions
	 */
	public static List<CurriculumVersion> findStudentsCurriculumVersionsFilteredAndSorted(
			int offset, int limit, List<SortInfoBean> sortInfo,
			List<FilterConfigBean> filterConfig, Student student) {
		List<CurriculumVersion> filterList = new ArrayList<CurriculumVersion>();
		Integer resultCount = 0;
		filterList = CurriculumVersion.findFilteredAndSorted(0,
				Integer.MAX_VALUE, sortInfo, filterConfig, resultCount);
		List<CurriculumVersion> results = new ArrayList<CurriculumVersion>();
		if (student != null) {
			List<CurriculumVersion> studentsCurriclumVersions = student
					.getPrimaryCurriculumVersions();
			for (CurriculumVersion v1 : studentsCurriclumVersions) {
				for (CurriculumVersion v2 : filterList) {
					if (v1.getCurriculumId().equals(v2.getCurriculumId())) {
						results.add(v2);
					}
				}
			}
		} else {
			results = new ArrayList<CurriculumVersion>();
		}
		return results;
	}

	/**
	 * returns list of node short names which are recommended in this curriculum
	 * version
	 * 
	 * @param curriculumVersionId
	 * @return list of node short names
	 */
	public static List<String> getRecommendedModulesVersionId(
			Long curriculumVersionId) {
		@SuppressWarnings("unchecked")
		List<String> nodeShortNames = EMF.getEm()
				.createNamedQuery("CurriculumVersion.recModules")
				.setParameter(1, curriculumVersionId).getResultList();
		return nodeShortNames;
	}

	/**
	 * returns list of node short names on which lecturer can add
	 * recommendations
	 * 
	 * @param curriculumVersionId
	 * @return list of node short names
	 */
	public static List<String> getLecturerModulesVersionId(
			Long curriculumVersionId, Long staffId) {
		@SuppressWarnings("unchecked")
		List<String> nodeShortNames = EMF.getEm()
				.createNamedQuery("CurriculumVersion.recLecturerModules")
				.setParameter(1, curriculumVersionId).setParameter(2, staffId)
				.getResultList();
		return nodeShortNames;
	}

	/**
	 * return node if is in lectureres list
	 * 
	 * @param curriculumVersionId
	 * @return true if lecturer is owner of module
	 */
	public static Boolean isLecturersModule(Long curriculumVersionId,
			Long staffId, String nodeShortName) {

		Query query = EMF.getEm().createNamedQuery(
				"CurriculumVersion.recLecturerModulesByShortName");
		query.setParameter(1, curriculumVersionId);
		query.setParameter(2, staffId);
		query.setParameter(3, nodeShortName);
		@SuppressWarnings("unchecked")
		List<String> nodeShortNames = query.getResultList();
		return (nodeShortNames.size() > 0);
	}

}