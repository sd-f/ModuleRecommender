/**
 * 
 */
package com.co.app.modrec.domain;

import java.util.ArrayList;
import java.util.List;

import com.co.app.stm.domain.Student;
import com.co.core.auth.server.helper.CurrentIdentity;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.SortInfoBean;
import com.sencha.gxt.data.shared.loader.FilterConfigBean;
import com.sencha.gxt.data.shared.loader.PagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * @author Lucas Reeh
 * 
 */
public class CurriculumVersionService {

	/* current identity for right check */
	@Inject
	CurrentIdentity currentIdentity;

	/**
	 * GXT paging load result config wrapper
	 * 
	 * @param config
	 * @return getCurriculumVersions
	 */
	PagingLoadResult<CurriculumVersion> getCurriculumVersions(
			PagingLoadConfigBean config) {
		return getCurriculumVersions(config.getOffset(), config.getLimit(),
				config.getSortInfo(), null);
	}

	/**
	 * service returning curriculum versions
	 * 
	 * @param offset
	 * @param limit
	 * @param sortInfo
	 * @param filterConfig
	 * @return list of curriculum versions
	 */
	public CurriculumVersionPagingLoadResultBean getCurriculumVersions(
			int offset, int limit, List<SortInfoBean> sortInfo,
			List<FilterConfigBean> filterConfig) {
		List<CurriculumVersion> results = new ArrayList<CurriculumVersion>();
		Integer resultCount = 0;
		results = CurriculumVersion.findFilteredAndSorted(offset, limit,
				sortInfo, filterConfig, resultCount);
		return new CurriculumVersionPagingLoadResultBean(results,
				resultCount.intValue(), offset);
	}

	/**
	 * GXT paging load result config wrapper
	 * 
	 * @param config
	 * @return getStudentsCurriculumVersions
	 */
	PagingLoadResult<CurriculumVersion> getStudentsCurriculumVersions(
			PagingLoadConfigBean config) throws Exception {
		return getStudentsCurriculumVersions(config.getOffset(),
				config.getLimit(), config.getSortInfo(), null);
	}

	/**
	 * service returning curriculum versions filtered on those 
	 * student is attendending
	 * 
	 * @param offset
	 * @param limit
	 * @param sortInfo
	 * @param filterConfig
	 * @return list of curriculum versions
	 */
	public CurriculumVersionPagingLoadResultBean getStudentsCurriculumVersions(
			int offset, int limit, List<SortInfoBean> sortInfo,
			List<FilterConfigBean> filterConfig) throws Exception {
		/* right check */
		//ModRecRights.checkRights(currentIdentity);
		
		Student student = null;
		if (currentIdentity != null) {
			if (currentIdentity.getIdentity() != null) {
				if (currentIdentity.getIdentity().isStudent()) {
					student = Student.find(currentIdentity.getIdentity().getStudentId());
				}
			}
		}
		
		List<CurriculumVersion> results = new ArrayList<CurriculumVersion>();
		if (student != null) {
			results = CurriculumVersion.findStudentsCurriculumVersionsFilteredAndSorted(offset, limit,
					sortInfo, filterConfig, student);
		}
		return new CurriculumVersionPagingLoadResultBean(results,
				results.size(), offset);
	}

}
