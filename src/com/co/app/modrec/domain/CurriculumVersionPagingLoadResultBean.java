/**
 * 
 */
package com.co.app.modrec.domain;

import java.util.List;

import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

/**
 * GXT paging load result bean for grid for curriculum version
 * 
 * @author Lucas Reeh
 * 
 */
public class CurriculumVersionPagingLoadResultBean extends
		PagingLoadResultBean<CurriculumVersion> {

	private static final long serialVersionUID = 1L;

	protected CurriculumVersionPagingLoadResultBean() {

	}

	public CurriculumVersionPagingLoadResultBean(List<CurriculumVersion> list,
			int totalLength, int offset) {
		super(list, totalLength, offset);
	}

}
