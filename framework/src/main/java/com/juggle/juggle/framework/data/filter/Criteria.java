package com.juggle.juggle.framework.data.filter;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.errors.StandardErrors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.jpa.domain.Specification;

import com.juggle.juggle.framework.data.filter.meta.Filtered;

public class Criteria<T> implements Specification<T> {
	private static final Logger LOG = LoggerFactory.getLogger(Criteria.class);
	private static String[] DEFAULT_FILTERED = new String[]{"deleted", "createdTime", "updatedTime", "createdBy", "updatedBy", "id", "uuid"};
	private PageSearch search;
	private CriteriaModifier<T> modifier;
	
	public Criteria(PageSearch search) {
		this.search = search;
	}
	
	public Criteria(PageSearch search, CriteriaModifier<T> modifier) {
		this.modifier = modifier;
		this.search = search;
	}
	
	private Comparable getComparable(Object value) {
		return (Comparable)value;
	}
	
	private Collection getCollection(Object value) {
		Collection collection = (Collection)value;
		Collection<Long> longCollection = new ArrayList<>();
		for (Object o : collection) {
			if(o instanceof Integer){
				longCollection.add(Long.valueOf(o.toString()));
			}
		}
		if(longCollection.size()>0){
			return longCollection;
		}
		return collection;
	}
	
	private Predicate and(CriteriaBuilder cb, Predicate p, Predicate p2) {
		if (null != p) {
			return cb.and(p, p2);
		}
		else  {
			return p2;
		}
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		try {
			Predicate p = null;
			if (null != search.getFilters() && !search.getFilters().isEmpty()) {
				for (ValueFilter c : search.getFilters()) {
					if (!ArrayUtils.contains(DEFAULT_FILTERED, c.getName())) {
						Filtered f = AnnotationUtils.getAnnotation(root.getJavaType().getDeclaredField(c.getName()), Filtered.class);
						if (null == f) {
							throw new ServiceException(StandardErrors.INTERNAL_ERROR.getStatus(), c.getName() + " is not searchable.");
						}
					}
					if (null == c.getValue()) {
						continue;
					}
					
					if (c.getValue() instanceof String && StringUtils.isEmpty(c.getValue().toString())) {
						continue;
					}
					
					Object value = c.getValue();
					PropertyDescriptor pd = new PropertyDescriptor(c.getName(), root.getJavaType());
					
					Class pc = pd.getPropertyType();

					if (!ValueFilter.OP_IS_NULL.equals(c.getOp())) {
						if (pc.equals(Date.class)) {
							value = new Date(Long.valueOf(c.getValue().toString()));
						}
					}
					
					Path path = root.get(c.getName());
					if (StringUtils.isEmpty(c.getOp()) || ValueFilter.OP_EQ.equals(c.getOp())) {
						p = and(cb, p, cb.equal(path, value));
					} else if (ValueFilter.OP_NEQ.equals(c.getOp())) {
						p = and(cb, p, cb.notEqual(path, value));
					} else if (ValueFilter.OP_GT.equals(c.getOp())) {
						p = and(cb, p, cb.greaterThan(path, getComparable(value)));
					} else if (ValueFilter.OP_GE.equals(c.getOp())) {
						p = and(cb, p, cb.greaterThanOrEqualTo(path, getComparable(value)));
					} else if (ValueFilter.OP_LT.equals(c.getOp())) {
						p = and(cb, p, cb.lessThan(path, getComparable(value)));
					} else if (ValueFilter.OP_LE.equals(c.getOp())) {
						p = and(cb, p, cb.lessThanOrEqualTo(path, getComparable(value)));
					} else if (ValueFilter.OP_LEFT_LIKE.equals(c.getOp())) {
						p = and(cb, p, cb.like(path, makeLeftLike(value.toString())));
					} else if (ValueFilter.OP_LIKE.equals(c.getOp())) {
						p = and(cb, p, cb.like(path, makeLike(value.toString())));
					} else if (ValueFilter.OP_IN.equals(c.getOp())) {
						p = and(cb, p, cb.in(path).value(getCollection(value)));
					} else if(ValueFilter.OP_IS_NULL.equals(c.getOp())) {
						if("1".equals(value.toString()) || "true".equals(value.toString())) {
							p = and(cb, p, cb.isNull(path));
						} else {
							p = and(cb, p, cb.isNotNull(path));
						}
					}
				}
			}
			if (null != this.modifier) {
				p = this.modifier.onSearch(root, cb, p);
			}
			
			if (null != p) {
				query.where(p);
			}
			if (!StringUtils.isEmpty(search.getSort())) {
				List<Order> list = new ArrayList<Order>();
				String[] sorts = search.getSort().split(",", -1);
				
				for (String sort : sorts) {
					String[] sa = sort.trim().split(" ", -1);
					if (sa.length > 1 && sa[1].equalsIgnoreCase("DESC")) {
						list.add(cb.desc(root.get(sa[0])));
					}
					else {
						list.add(cb.asc(root.get(sa[0])));
					}
				}
				query.orderBy(list);
			}
		} catch (ServiceException sx) {
			throw sx;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
		return null;
	}
	
	
	private static String makeLike(String in) {
		return "%" + in.replaceAll("%", "%%") + "%";
	}

	private static String makeLeftLike(String in) {
		return in.replaceAll("%", "%%") + "%";
	}

	private static class ValueDesc<Y> {
		Path<Y> path;
		Object value;

		public ValueDesc(Path<Y> path, Object value) {
			super();
			this.path = path;
			this.value = value;
		}
	}
	
	public interface CriteriaModifier<T> {
		Predicate onSearch(Root<T> root, CriteriaBuilder cb, Predicate p);
	}
}
