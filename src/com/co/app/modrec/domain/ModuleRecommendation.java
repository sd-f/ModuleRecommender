package com.co.app.modrec.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.co.core.server.util.EMF;
import com.google.common.base.Joiner;


/**
 * The persistent class for the module recommendations database table.
 * 
 */
@Entity
@IdClass(ModuleRecommendationId.class)
@Table(name="MODULE_RECOMMENDATIONS")
@NamedQueries({
	@NamedQuery(name = "ModuleRecommendation.byNodeShortName", query = ""
					+ "  select rec from ModuleRecommendation rec"
					+ "   where rec.nodeShortName = :pNodeShortName" +
					"       and rec.curriculumVersionId = :pCurriculumVersionId")
})
@NamedNativeQueries({
	@NamedNativeQuery(name = "ModuleRecommendation.countStudentsMatching", query = "" +
			"select count(1)" +
			"  from ( select a1.student_id" +
			"          from ( select distinct m.node_shortname, a.student_id" +
			"                   from modrec_node_student_att_all_mv a" +
			"                      , modrec_module_node_mv m" +
			"                  where m.node_id = a.node_id" +
			"                    and m.node_shortname in ( select distinct node_shortname" +
			"                                                       from modrec_module_node_mv m" +
			"                                                      where m.node_id in (?1)" +
			"                                                   )" +
			"               ) a1" +
			"             , ( select distinct node_shortname" +
			"                   from modrec_module_node_mv m" +
			"                  where m.node_id in (?1) " +
			"               ) b2" +
			"          where a1.node_shortname = b2.node_shortname" +
			"           group by a1.student_id" +
			"          having count ( a1.node_shortname )" +
			"                  = (  select count ( distinct node_shortname )" +
			"                         from modrec_module_node_mv m" +
			"                        where m.node_id in (?1)" +
			"                    )" +
			"        ) a"),
	@NamedNativeQuery(name = "ModuleRecommendation.countStudentsMatchingSingle", query = "" +
			"select count(1)" +
			"  from ( select a1.student_id" +
			"          from ( select distinct m.node_shortname, a.student_id" +
			"                   from modrec_node_student_att_all_mv a" +
			"                      , modrec_module_node_mv m" +
			"                  where m.node_id = a.node_id" +
			"                    and m.node_shortname in ( select distinct node_shortname" +
			"                                                       from modrec_module_node_mv m" +
			"                                                      where m.node_id = ?1" +
			"                                                   )" +
			"               ) a1" +
			"             , ( select distinct node_shortname" +
			"                   from modrec_module_node_mv m" +
			"                  where m.node_id = ?1 " +
			"               ) b2" +
			"          where a1.node_shortname = b2.node_shortname" +
			"           group by a1.student_id" +
			"          having count ( a1.node_shortname )" +
			"                  = (  select count ( distinct node_shortname )" +
			"                         from modrec_module_node_mv m" +
			"                        where m.node_id = ?1" +
			"                    )" +
			"        ) a")
})
public class ModuleRecommendation implements Serializable {


	private static final long serialVersionUID = 1L;

	// primary key (compound)
	@Id
	@Column(name = "node_shortname")
	private String nodeShortName;

	// primary key (compound)
	@Id
	@Column(name = "node_shortname_req")
	private String nodeShortNameReq;

	// primary key (compound)
	@Id
	@Column(name = "curriculum_version_id")
	private Long curriculumVersionId;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = CurriculumVersion.class)
	@JoinColumn(name = "curriculum_version_id", updatable = false, insertable = false)
	private CurriculumVersion curriculumVersion;
	
	/**
	 * class constructor
	 */
	public ModuleRecommendation() {
		super();
	}
	
	/**
	 * class constructor
	 * 
	 * @param nodeShortName
	 * @param nodeShortNameReq
	 * @param curriculumVersionId
	 */
	public ModuleRecommendation(String nodeShortName, String nodeShortNameReq, Long curriculumVersionId) {
		this.curriculumVersionId = curriculumVersionId;
		this.nodeShortName = nodeShortName;
		this.nodeShortNameReq = nodeShortNameReq;
	}
	
	
	/**
	 * find recommendations by curriculum node
	 * 
	 * @param node
	 * @return list of module recommendations
	 */
	public static List<ModuleRecommendation> findByNode(CurriculumNode node) {
		TypedQuery<ModuleRecommendation> query = EMF.getEm().createNamedQuery("ModuleRecommendation.byNodeShortName",ModuleRecommendation.class);
		query.setParameter("pNodeShortName", node.getNodeShortName());
		query.setParameter("pCurriculumVersionId", node.getCurriculumVersionId());
		return query.getResultList();
	}
	
	/**
	 * find recommendations by node short name and curriculum version id
	 * 
	 * @param nodeShortName
	 * @param curriculumVersionId
	 * @return list of module recommendations
	 */
	public static List<ModuleRecommendation> findByNodeShortName(String nodeShortName, Long curriculumVersionId) {
		TypedQuery<ModuleRecommendation> query = EMF.getEm().createNamedQuery("ModuleRecommendation.byNodeShortName",ModuleRecommendation.class);
		query.setParameter("pNodeShortName", nodeShortName);
		query.setParameter("pCurriculumVersionId", curriculumVersionId);
		return query.getResultList();
	}
	
	/**
	 * calculates number of matching students to recommendation requirements
	 * 
	 * @param nodeIds that should match
	 * @return number of students matching nodeIds list
	 */
	public static Integer getNumberOfStudentsMatching(List<Long> nodeIds) {
		if (nodeIds.size() == 1) {
			Query qery = EMF.getEm().createNamedQuery("ModuleRecommendation.countStudentsMatchingSingle").setParameter(1, nodeIds.get(0).intValue());
			BigDecimal result = (BigDecimal) qery.getSingleResult();
			return result.intValue();
		} else if (nodeIds.size()>1) {
			String itemsString = Joiner.on(",").join(nodeIds);
			Query qery = EMF.getEm().createNativeQuery("" +
			"select count(1)" +
			"  from ( select a1.student_id" +
			"          from ( select distinct m.node_shortname, a.student_id" +
			"                   from modrec_node_student_att_all_mv a" +
			"                      , modrec_module_node_mv m" +
			"                  where m.node_id = a.node_id" +
			"                    and m.node_shortname in ( select distinct node_shortname" +
			"                                                from modrec_module_node_mv m" +
			"                                               where m.node_id in (" +itemsString+")"+
			"                                            )" +
			"               ) a1" +
			"             , ( select distinct node_shortname" +
			"                   from modrec_module_node_mv m" +
			"                  where m.node_id in (" +itemsString+")"+
			"               ) b2" +
			"          where a1.node_shortname = b2.node_shortname" +
			"           group by a1.student_id" +
			"          having count ( a1.node_shortname )" +
			"                  = (  select count ( distinct node_shortname )" +
			"                         from modrec_module_node_mv m" +
			"                        where m.node_id in (" +itemsString+")"+
			"                    )" +
			"        ) a");
			
			
			BigDecimal result = (BigDecimal) qery.getSingleResult();
			return result.intValue();
		}
		return 0;
	}
	
	/**
	 * delete recommendation by node
	 * 
	 * @param node
	 */
	public static void deleteByNode(CurriculumNode node) {
		EntityManager em = EMF.getEm();
		try {
			em.getTransaction().begin();
			TypedQuery<ModuleRecommendation> query = EMF.getEm().createNamedQuery("ModuleRecommendation.byNodeShortName",ModuleRecommendation.class);
			query.setParameter("pNodeShortName", node.getNodeShortName());
			query.setParameter("pCurriculumVersionId", node.getCurriculumVersionId());
			List<ModuleRecommendation> recs = query.getResultList();
			for (ModuleRecommendation rec: recs) {
				ModuleRecommendation recommendation = em.merge(rec);
				em.remove(recommendation);
			}
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
		}
		
	}
	
	/**
	 * persist recommendation by given list of requirements
	 * 
	 * @param list
	 */
	public static void persist(List<ModuleRecommendation> list) {
		EntityManager em = EMF.getEm();
		try {
			em.getTransaction().begin();
			for (ModuleRecommendation rec: list) {
				em.persist(rec);
			}
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
		}
	}

	/**
	 * @return the nodeShortName
	 */
	public String getNodeShortName() {
		return nodeShortName;
	}

	/**
	 * @param nodeShortName the nodeShortName to set
	 */
	public void setNodeShortName(String nodeShortName) {
		this.nodeShortName = nodeShortName;
	}

	/**
	 * @return the nodeShortNameReq
	 */
	public String getNodeShortNameReq() {
		return nodeShortNameReq;
	}

	/**
	 * @param nodeShortNameReq the nodeShortNameReq to set
	 */
	public void setNodeShortNameReq(String nodeShortNameReq) {
		this.nodeShortNameReq = nodeShortNameReq;
	}

	/**
	 * @return the curriculumVersionId
	 */
	public Long getCurriculumVersionId() {
		return curriculumVersionId;
	}

	/**
	 * @param curriculumVersionId the curriculumVersionId to set
	 */
	public void setCurriculumVersionId(Long curriculumVersionId) {
		this.curriculumVersionId = curriculumVersionId;
	}
	
	
	/**
	 * converts requirements to String List
	 */
	public static List<String> requirementsAsStrings(List<ModuleRecommendation> list) {
		List<String> result = new ArrayList<String>();
		for (ModuleRecommendation rec: list) {
			result.add(rec.nodeShortNameReq);
		}
		return result;
	}
	
	/**
	 * string representation of module recommendation
	 */
	@Override
	public String toString() {
		return this.nodeShortNameReq;
	}

}