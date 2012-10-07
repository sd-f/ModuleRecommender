/**
 * 
 */
package com.co.core.server.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.Session;

import com.co.server.persistence.COPersistenceConstants;

/**
 * @author Lucas Reeh
 *
 */
public class EMF {

	
	private static EntityManagerFactory coEMF = Persistence
			.createEntityManagerFactory(COPersistenceConstants.PERSISTENCE_UNIT_NAME);
	
	/*
	@Inject
	static EntityManager em;
	*/

	/**
	 * @return the em
	 */
	public static EntityManager getEm() {
		return coEMF.createEntityManager();
	}

	/**
	 * @return the session for eclipselink
	 */
	public static Session getSession() {
		return ((JpaEntityManager) getEm().getDelegate()).getActiveSession();

	}
	
}
