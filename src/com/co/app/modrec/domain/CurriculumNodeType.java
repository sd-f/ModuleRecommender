package com.co.app.modrec.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

import com.co.app.modrec.shared.proxy.CurriculumNodeTypeProxy;
import com.co.core.server.util.EMF;

/**
 * The persistent class for node types in database
 * 
 */
@Entity
@Table(name = "STP_KNOTEN_TYPEN", schema = "TUG_NEW")
@NamedQueries({
		@NamedQuery(name = "allCurriculumNodeTypes", query = "select nt from CurriculumNodeType nt"),
		@NamedQuery(name = "loadCurriculumNodeTypeByRefKey", query = "select nt from CurriculumNodeType nt where nt.referenceKey = :pKey"),
		@NamedQuery(name = "loadAllCurriculumNodeTypes", query = "select nt from CurriculumNodeType nt"),
		@NamedQuery(name = "nonModuleTypeIds", query = "select nt.id from CurriculumNodeType nt where nt.referenceKey not like '%MKN%' and nt.referenceKey not like '%AKN%' and nt.referenceKey not like '%PKN%'"),
		@NamedQuery(name = "moduleTypeIds", query = "select nt.id from CurriculumNodeType nt where nt.referenceKey like '%MKN%'")})
public class CurriculumNodeType implements Serializable,
		CurriculumNodeTypeProxy {
	private static final long serialVersionUID = 1L;

	// primary key
	@Id
	@SequenceGenerator(name = "STP_KNOTEN_TYPEN_ID_GENERATOR", sequenceName = "STP_KNOTEN_TYPEN_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STP_KNOTEN_TYPEN_ID_GENERATOR")
	@Column(name = "NR", updatable = false, unique = true, nullable = false)
	private Long id;

	// icon for node
	@Column(name = "ICON")
	private String iconRefId;

	// reference short name 
	@Column(name = "KURZBEZEICHNUNG")
	private String referenceKey;

	// name
	@Column(name = "NAME")
	private String name;

	// bi-directional many-to-one association to node types
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STP_KNTYP_NR")
	private CurriculumNodeType superType;

	// bi-directional many-to-one association to node types
	@OneToMany(mappedBy = "superType")
	private List<CurriculumNodeType> subTypes;

	/**
	 * class constructor
	 */
	public CurriculumNodeType() {
	}

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 * 			the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the iconRefId
	 */
	@Override
	public String getIconRefId() {
		return this.iconRefId;
	}

	/**
	 * @param iconRefId
	 * 				the iconRefId to set
	 */
	public void setIconRefId(String iconRefId) {
		this.iconRefId = iconRefId;
	}

	/**
	 * @return the referenceKey
	 */
	@Override
	public String getReferenceKey() {
		return this.referenceKey;
	}

	/**
	 * @param referenceKey
	 * 				the referenceKey to set
	 */
	public void setReferenceKey(String referenceKey) {
		this.referenceKey = referenceKey;
	}

	/**
	 * @return the name
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 * 				the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the superType
	 */
	public CurriculumNodeTypeProxy getSuperType() {
		return this.superType;
	}

	/**
	 * @param superType
	 * 				the superType to set
	 */
	public void setSuperType(CurriculumNodeType superType) {
		this.superType = superType;
	}

	/**
	 * @return the subTypes
	 */
	public List<CurriculumNodeType> getSubTypes() {
		return this.subTypes;
	}

	/**
	 * @param subTypes
	 * 				the subTypes to set
	 */
	public void setSubTypes(List<CurriculumNodeType> subTypes) {
		this.subTypes = subTypes;
	}
	
	/**
	 * list of non module types
	 * 
	 * @return list of node type ids
	 */
	public static List<Long> findNonModuleTypeIds() {
		EntityManager em = EMF.getEm();
		TypedQuery<Long> query = em.createNamedQuery(
				"nonModuleTypeIds", Long.class);
		
		List<Long> nodes = query.getResultList();
		return nodes;
	}
	
	/**
	 * list of module types
	 * 
	 * @return list of node type ids
	 */
	public static List<Long> findModuleTypeIds() {
		EntityManager em = EMF.getEm();
		TypedQuery<Long> query = em.createNamedQuery(
				"moduleTypeIds", Long.class);
		
		List<Long> nodes = query.getResultList();
		return nodes;
	}

}