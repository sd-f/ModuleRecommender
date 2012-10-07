package com.co.app.modrec.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.co.core.server.util.EMF;


/**
 * The persistent class for the semester type database table.
 * 
 */
@Entity
@Table(name="STP_SEMESTER_TYPEN", schema="TUG_NEW")
public class SemesterType implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @return the serialversionuid
	 */
	public long getVersion() {
		return serialVersionUID;
	}
	
	// primary key
	@Id
	@Column(name="NR")
	private Long id;

	// short name for referencing
	@Column(name="KURZBEZEICHNUNG")
	private String refId;
	
	// short name in English
	@Column(name="KURZBEZ_ENGL")
	private String refIdEnglish;

	// name
	private String name;

	// name in English
	@Column(name="NAME_ENGL")
	private String nameEnglish;

	/**
	 * class constructor
	 */
	public SemesterType() {
		this.id = new Long(0);
		this.refId = "NaN";
	}

	/**
	 * find record by id
	 * 
	 * @param id
	 * @return Curriculum version by ID
	 */
	public static SemesterType findSemesterType(Long id) {
		if (id == null) {
			return null;
		}
		return EMF.getEm().find(SemesterType.class, id);
	}
	
	/*
	 * default setter/getter
	 */
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRefIdEnglish() {
		return this.refIdEnglish;
	}

	public void setRefIdEnglish(String refIdEnglish) {
		this.refIdEnglish = refIdEnglish;
	}

	public String getRefId() {
		return this.refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameEnglish() {
		return this.nameEnglish;
	}

	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

}