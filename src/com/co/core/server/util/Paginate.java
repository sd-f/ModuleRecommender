/**
 * 
 */
package com.co.core.server.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.SortInfoBean;
import com.sencha.gxt.data.shared.loader.FilterConfigBean;

/**
 * @author Lucas Reeh
 * 
 */
public class Paginate<T> {

	private EntityManager emProvider;
	private List<FilterConfigBean> filterConfig;

	public Paginate(EntityManager emProvider) {
		this.emProvider = emProvider;
	}

	public List<T> paginate(Class<T> clazz, int offset, int limit,
			List<SortInfoBean> sortInfo, List<FilterConfigBean> filterConfig, Integer resultCount) {
		this.filterConfig = filterConfig;
		CriteriaBuilder cb = emProvider.getCriteriaBuilder();
		CriteriaQuery<T> c = cb.createQuery(clazz);
		Root<T> r = c.from(clazz);
		
		c.where(condition(cb, r).toArray(new Predicate[] {}));

		for (SortInfoBean sortField: sortInfo) {
			if (sortField.getSortDir() == SortDir.ASC)
				c.orderBy(cb.asc(r.get(sortField.getSortField())));
			if (sortField.getSortDir() == SortDir.DESC)
				c.orderBy(cb.desc(r.get(sortField.getSortField())));
		}
		
		TypedQuery<T> q = emProvider.createQuery(c);
		resultCount = q.getResultList().size();
		q.setFirstResult(offset);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	public Long count(Class<T> clazz) {

		CriteriaBuilder cb = emProvider.getCriteriaBuilder();
		CriteriaQuery<Long> c = cb.createQuery(Long.class);
		Root<T> r = c.from(clazz);
		c.where(condition(cb, r).toArray(new Predicate[] {}));
		c.select(cb.count(r));
		return emProvider.createQuery(c).getSingleResult();
	}

	private List<Predicate> condition(CriteriaBuilder cb, Root<T> r) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		for (FilterConfigBean s : filterConfig) {

			if ("contains".equals(s.getComparison())) {
				predicates.add(cb.like(cb.lower(r.<String> get(s.getField())),
						"%" + s.getValue().toLowerCase() + "%"));
			}
			if ("gt".equals(s.getComparison())) {
				predicates.add(cb.gt(r.<Double> get(s.getField()),
						Double.valueOf(s.getValue())));
			}
			if ("lt".equals(s.getComparison())) {
				predicates.add(cb.lt(r.<Double> get(s.getField()),
						Double.valueOf(s.getValue())));
			}
			if ("eq".equals(s.getComparison())) {
				predicates.add(cb.equal(r.<Double> get(s.getField()),
						Double.valueOf(s.getValue())));
			}
			if ("on".equals(s.getComparison())) {
			}
			if ("after".equals(s.getComparison())) {
			}
			if ("before".equals(s.getComparison())) {
			}

		}
		return predicates;
	}
}
