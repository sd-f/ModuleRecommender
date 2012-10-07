/**
 * 
 */
package com.co.core.identity.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;

import com.co.server.persistence.COPersistenceConstants;

/**
 * @author Lucas Reeh
 *
 */
@Entity
@Table(name = "IDENTITAETEN", schema = "TUG_NEW")
public class Identity implements Serializable {
	
	private static EntityManagerFactory coEMF = Persistence
			.createEntityManagerFactory(COPersistenceConstants.PERSISTENCE_UNIT_NAME);
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @return current entity manager
	 */
	public static final EntityManager entityManager() {
		return coEMF.createEntityManager();
	}

	@Id
	@Column(name = "NR")
	private Long id;
	
	@Column(name = "VORNAME")
	private String fristName;
	
	@Column(name = "FAMILIENNAME")
	private String lastName;
	
	@Column(name = "ST_PERSON_NR")
	private Long studentId;
	
	@Column(name = "PERSON_NR")
	private Long staffId;
	
	

	/**
	 * class constructor
	 */
	public Identity() {
		super();
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
	 * @return firstName + lastName
	 */
	public String getFullName() {
		return this.fristName + " " + this.lastName;
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
	 * 
	 * @param id
	 * @return
	 */
	public static Identity find(Long id) {
		if (id == null) {
			return null;
		}
		EntityManager em = entityManager();
		return em.find(Identity.class, id);
	}
	
	public Boolean isStudent() {
		return (getStudentId() != null);
	}
	
	public Boolean isStaff() {
		return (getStaffId() != null);
	}

	
	/**
	 * @return the staffId
	 */
	public Long getStaffId() {
		return staffId;
	}

	/**
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

}
