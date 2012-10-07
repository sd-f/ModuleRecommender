/**
 * 
 */
package com.co.app.stm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Persistence;
import javax.persistence.Table;

import com.co.app.modrec.domain.CurriculumVersion;
import com.co.app.modrec.domain.ModuleRecommendation;
import com.co.core.server.util.EMF;
import com.co.server.persistence.COPersistenceConstants;

/**
 * The persistent class for the students database table.
 * 
 * @author Lucas Reeh
 *
 */
@Entity
@Table(name = "ST_PERSONEN", schema = "STUD")
@NamedNativeQueries({
	@NamedNativeQuery(name = "Student.modules", query = "" +
			"select distinct c.node_id" +
			"  from modrec_node_student_att_mv c" +
			" where c.student_id = ?1" +
			"   and c.curriculum_version_id = ?2"),
	@NamedNativeQuery(name = "Student.attendedNodeShortName", query = "" +
			"select m.node_shortname" +
			"  from modrec_node_student_att_mv a" +
			"     , modrec_module_node_mv m" +
			" where a.student_id = ?" +
			"   and m.node_id = a.node_id"),
	@NamedNativeQuery(name = "Student.recommendationsIds", query = "" +
			"select distinct n.node_shortname" +
			"  from modrec_node_student_att_mv c" +
			"     , modrec_node_mv n" +
			"     , ( select a.student_id" +
			"           from modrec_node_student_att_mv a" +
			"              , (" +
			"                     select b.node_id" +
			"                       from modrec_node_student_att_mv b" +
			"                      where b.student_id = ?1 " +
			"                        and b.curriculum_version_id = ?2" +
			"                 ) b" +
			"           where a.curriculum_version_id = ?2" +
			"             and a.student_id != ?1" +
			"             and a.node_id = b.node_id" +
			"           group by a.student_id" +
			"          having count(b.node_id) = (  select count(b.node_id)" +
			"                                         from modrec_node_student_att_mv b" +
			"                                        where b.student_id = ?1 " +
			"                                          and b.curriculum_version_id = ?2" +
			"                                    )" +
			"        ) c1" +
			"     , ( select b.node_id" +
			"           from modrec_node_student_att_mv b" +
			"          where b.student_id = ?1 " +
			"            and b.curriculum_version_id = ?2" +
			"       ) c2" +
			" where c.student_id = c.student_id" +
			"   and c.curriculum_version_id = ?2" +
			"   and c2.node_id != c.node_id" +
			"   and n.id = c.node_id"),
	@NamedNativeQuery(name = "Student.recommendationsFast", query = "" +
			"select distinct node_id" +
			"  from (" +
			"  select f.node_id" +
			"    from modrec_node_student_att_mv f" +
			"       , (  select distinct al.student_id" +
			"              from (" +
			"                      select b.node_id" +
			"                           , count(b.node_id) over ( partition by b.student_id ) countma" +
			"                           , c.countm" +
			"                           , b.student_id" +
			"                        from modrec_node_student_att_mv b" +
			"                           , ( select a.node_id" +
			"                                    , count(a.node_id) over ( partition by a.student_id ) countm" +
			"                                 from modrec_node_student_att_mv a" +
			"                                where a.student_id = ?1" +
			"                                  and a.curriculum_version_id = ?2" +
			"                             ) c" +
			"                       where b.student_id != ?1" +
			"                         and b.curriculum_version_id = ?2" +
			"                         and c.node_id = b.node_id" +
			"                   ) al" +
			"               where al.countm = al.countma" +
			"          ) bf" +
			"   where bf.student_id = f.student_id" +
			"     and f.curriculum_version_id = ?2" +
			"  minus" +
			" select a.node_id" +
			" from modrec_node_student_att_mv a" +
			"  where a.student_id = ?1" +
			"    and a.curriculum_version_id = ?2" +
			")")	
})
public class Student implements Serializable {
	
	private static EntityManagerFactory coEMF = Persistence
			.createEntityManagerFactory(COPersistenceConstants.PERSISTENCE_UNIT_NAME);
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @return current entity manager
	 */
	public static final EntityManager entityManager() {
		return coEMF.createEntityManager();
	}

	// primary key
	@Id
	@Column(name = "NR")
	private Long id;
	
	// first name of student
	@Column(name = "VORNAME")
	private String fristName;
	
	// students last name
	@Column(name = "FAMILIENNAME")
	private String lastName;
	
	// registration number
	@Column(name = "MATRIKELNUMMER")
	private String registrationNumber;
	
	// students curricula
	@ManyToMany
	@JoinTable(
			name="IN_STP_ST_STUD_ZU_BER_SMALL_MV",
			joinColumns={@JoinColumn(name="ST_PERSON_NR")},
			inverseJoinColumns={@JoinColumn(name="STP_STP_1_NR")}
	)
	List<CurriculumVersion> primaryCurriculumVersions;
	

	/**
	 * class constructor
	 */
	public Student() {
		super();
	}
	
	/**
	 * find student by id
	 * 
	 * @param id
	 * @return student
	 */
	public static Student find(Long id) {
		if (id == null) {
			return null;
		}
		EntityManager em = entityManager();
		return em.find(Student.class, id);
	}
	
	
	/**
	 * returns list of nodes which other student with same attended nodes have
	 * attended
	 * 
	 * @param curriculumVersionId
	 * @return list of Recommended Nodes
	 */
	public List<Long> getNodesRecommendedByVersionId(Long curriculumVersionId) {
		@SuppressWarnings("unchecked")
		List<BigDecimal> moduleIds = EMF.getEm().createNamedQuery("Student.recommendationsFast").setParameter(1, this.getId()).setParameter(2, curriculumVersionId).getResultList();
		List<Long> result = new ArrayList<Long>();
		for (BigDecimal value: moduleIds) {
			result.add(value.longValue());
		}
		return result;
	}
	
	/**
	 * returns list of nodes which other student with same attended nodes have
	 * attended
	 * 
	 * @param curriculumVersionId
	 * @return list of Recommended Nodes
	 */
	public List<Long> getNodesAttendedByVersionId(Long curriculumVersionId) {
		@SuppressWarnings("unchecked")
		List<BigDecimal> moduleIds = EMF.getEm().createNamedQuery("Student.modules").setParameter(1, this.getId()).setParameter(2, curriculumVersionId).getResultList();
		List<Long> result = new ArrayList<Long>();
		for (BigDecimal value: moduleIds) {
			result.add(value.longValue());
		}
		return result;
	}
	
	/**
	 * returns list of node names which student has attended
	 * 
	 * @return list of Node short names
	 */
	public List<String> getNodeShortNamesAttended() {
		@SuppressWarnings("unchecked")
		List<String> list = EMF.getEm().createNamedQuery("Student.attendedNodeShortName").setParameter(1, this.getId()).getResultList();
		return list;
	}
	
	
	/**
	 * returns list of nodes which have been recommended by lecturer
	 * 
	 * @param curriculumVersionId
	 * @return list of Recommended Nodes
	 */
	public List<String> getLecturerRecommendations(Long curriculumVersionId) {
		List<String> recommended = new ArrayList<String>();
		
		List<String> recAll = CurriculumVersion.getRecommendedModulesVersionId(curriculumVersionId);
		List<String> attended = this.getNodeShortNamesAttended();
		
		for (String c: recAll) {
			List<ModuleRecommendation> recRequirements = ModuleRecommendation.findByNodeShortName(c,curriculumVersionId);
			List<String> recRequirementsString = ModuleRecommendation.requirementsAsStrings(recRequirements);
			if (attended.containsAll(recRequirementsString))
				recommended.add(c);
		}
		return recommended;
		
	}
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * sets the id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the serialversionuid
	 */
	public long getVersion() {
		return serialVersionUID;
	}

	/**
	 * @return the fristName
	 */
	public String getFristName() {
		return fristName;
	}

	/**
	 * @param fristName the fristName to set
	 */
	public void setFristName(String fristName) {
		this.fristName = fristName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the registrationNumber
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * @param registrationNumber the registrationNumber to set
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	/**
	 * @return the primaryCurriculumVersions
	 */
	public List<CurriculumVersion> getPrimaryCurriculumVersions() {
		return primaryCurriculumVersions;
	}

	/**
	 * @param primaryCurriculumVersions the primaryCurriculumVersions to set
	 */
	public void setPrimaryCurriculumVersions(
			List<CurriculumVersion> primaryCurriculumVersions) {
		this.primaryCurriculumVersions = primaryCurriculumVersions;
	}

}
