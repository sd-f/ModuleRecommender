package com.co.app.modrec.shared.requestfactory;

import java.util.List;

import com.co.app.modrec.domain.CurriculumVersionPagingLoadResultBean;
import com.co.app.modrec.domain.CurriculumVersionService;
import com.co.app.modrec.domain.CurriculumVersionServiceLocator;
import com.co.app.modrec.shared.proxy.CurriculumVersionProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Request Context for CurriculumVersionService
 * 
 * @author Lucas Reeh
 * 
 */
@Service(value = CurriculumVersionService.class, locator = CurriculumVersionServiceLocator.class)
public interface CurriculumVersionServiceRequest extends RequestContext {

	@ProxyFor(CurriculumVersionPagingLoadResultBean.class)
	public interface CurriculumVersionPagingLoadResultProxy extends ValueProxy,
			PagingLoadResult<CurriculumVersionProxy> {
		@Override
	    public List<CurriculumVersionProxy> getData();

	}

	Request<CurriculumVersionPagingLoadResultProxy> getCurriculumVersions(
			int offset, int limit, List<? extends SortInfo> sortInfo,
			List<? extends FilterConfig> filterConfig);

	Request<CurriculumVersionPagingLoadResultProxy> getStudentsCurriculumVersions(
			int offset, int limit, List<? extends SortInfo> sortInfo,
			List<? extends FilterConfig> filterConfig);
	
}
