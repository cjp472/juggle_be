package com.juggle.juggle.framework.data.json;

import com.juggle.juggle.framework.data.json.context.ExtViewContext;
import com.juggle.juggle.framework.data.json.load.ILoader;
import com.juggle.juggle.framework.data.json.load.LoaderFactory;
import com.juggle.juggle.framework.data.json.meta.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ExtRetriever {
	
	private static ExtRetriever INSTANCE = new ExtRetriever();
	
	private ExtRetriever() {
	}
	
	public static ExtRetriever getInstance() {
		return INSTANCE;
	}
	
	private Object findValue(Object obj, boolean raw, String name) {
		try {
			if (raw) {
				return this.findFieldValue(obj, name);
			}
			else {
				return this.findMethodValue(obj, name);
			}
		}
		catch (Throwable ex) {
			return null;
		}
	}
	
	private Object findFieldValue(Object obj, String fieldName) {
		try {
			return PropertyUtils.getProperty(obj, fieldName);
		}
		catch (Throwable ex) {
			return null;
		}
	}

	private Object findMethodValue(Object obj, String methodName) {
		try {
			Method m = obj.getClass().getDeclaredMethod(methodName);
			return m.invoke(obj);
		}
		catch (Throwable ex) {
			return null;
		}
	}
	
	public void retrieve(Object obj) throws Exception {
		if (null == obj) {
			return;
		}
		
		Collection<ExtendAnnotation> refs = findExtendAnnotations(obj);
		if (refs.isEmpty()) {
			return;
		}
		else {
			if (ExtViewContext.isTrack(obj)) {
				return;
			}

			int level = ExtViewContext.get().getLevel();
			boolean inside = ExtViewContext.get().isInsideCollection();
			
			for (ExtendAnnotation f: refs) {
				if (f.getMapping() instanceof One) {
					One one = (One)f.getMapping();
					if (isInGroup(one) &&
							(!inside || one.batch())) {
						Object v =this.findValue(obj, f.raw, StringUtils.isEmpty(one.src()) ? f.getName() : one.src());
						if (null == v) {
							continue;
						}
						ILoader loader = LoaderFactory.getInstance().createLoader(one.loader());
						Object r = loader.loadOne(v, one);
						ExtViewContext.addExtend(obj, one.value(), r);
					}
				}
				else if (f.getMapping() instanceof Many) {
					Many many = (Many)f.getMapping();

					if (isInGroup(many) &&
							(!inside || many.batch())) {
						Object v =this.findValue(obj, f.raw, StringUtils.isEmpty(many.src()) ? f.getName() : many.src());
						if (null == v) {
							continue;
						}
						ILoader loader = LoaderFactory.getInstance().createLoader(many.loader());
						
						List rs = loader.loadMany(v, many);
						ExtViewContext.addExtend(obj, many.value(), rs);
					}
				}
				else if (f.getMapping() instanceof Many2Many) {
					
					Many2Many m2m = (Many2Many)f.getMapping();
					if (isInGroup(m2m) &&
							(!inside || m2m.batch())) {
						Object v =this.findValue(obj, f.raw, StringUtils.isEmpty(m2m.src()) ? f.getName() : m2m.src());
						if (null == v) {
							continue;
						}
						ILoader loader = LoaderFactory.getInstance().createLoader(m2m.loader());
						
						List rs = loader.loadMany2Many(v, m2m);
						ExtViewContext.addExtend(obj, m2m.value(), rs);
					}
				}
			}
		}
	}
	
	public void retrieve(Collection col) throws Exception {
		if (col.isEmpty()) {
			return;
		}
		Object first = col.iterator().next();
		Collection<ExtendAnnotation> annotations = findExtendAnnotations(first);
		for (ExtendAnnotation f : annotations) {
			if (f.getMapping() instanceof One) {
				One one = (One)f.getMapping();
				ILoader loader = LoaderFactory.getInstance().createLoader(one.loader());
				
				if (isInGroup(one) && one.batch()) {
					Set<Object> values = new HashSet<>();
					for (Object obj : col) {
						ExtViewContext.track(obj);
						Object v =this.findValue(obj, f.raw, StringUtils.isEmpty(one.src()) ? f.getName() : one.src());
						if (null != v) {
							values.add(v);
						}
					}
					Map batch = loader.loadOneInBatch(values, one);
					for (Object obj : col) {
						Object v =this.findValue(obj, f.raw, StringUtils.isEmpty(one.src()) ? f.getName() : one.src());
						if (null != v) {
							Object r = batch.get(v);
							if (null != r) {
								ExtViewContext.addExtend(obj, one.value(), r);
							}
						}
					}
				}
			}
			else if (f.getMapping() instanceof Many) {
				Many many = (Many)f.getMapping();
				if (isInGroup(many) && many.batch()) {
					ILoader loader = LoaderFactory.getInstance().createLoader(many.loader());
					for (Object obj : col) {
						Object v =this.findValue(obj, f.raw, StringUtils.isEmpty(many.src()) ? f.getName() : many.src());
						if (null != v) {
							List rs = loader.loadMany(v, many);
							if (null != rs && !rs.isEmpty()) {
								ExtViewContext.addExtend(obj, many.value(), rs);
							}
						}
					}
				}
			}
			else if (f.getMapping() instanceof Many2Many) {
				Many2Many m2m = (Many2Many)f.getMapping();
				if (isInGroup(m2m) && m2m.batch()) {
					ILoader loader = LoaderFactory.getInstance().createLoader(m2m.loader());
					for (Object obj : col) {
						Object v =this.findValue(obj, f.raw, StringUtils.isEmpty(m2m.src()) ? f.getName() : m2m.src());
						if (null != v) {
							List rs = loader.loadMany2Many(v, m2m);
							if (null != rs && !rs.isEmpty()) {
								ExtViewContext.addExtend(obj, m2m.value(), rs);
							}
						}
					}
				}
			}
		}
	}

	private Collection<ExtendAnnotation> buildExtendAnnotations(Collection<ExtendAnnotation> refs, Annotation[] as, String name) {
		for (Annotation a : as) {
			if (a instanceof One || a instanceof Many || a instanceof Many2Many) {
				if (refs == null) {
					refs = new ArrayList<>();
				}
				refs.add(new ExtendAnnotation(true, name, a));
			} else if (a instanceof Ones) {
				if (refs == null) {
					refs = new ArrayList<>();
				}
				Ones ones = (Ones)a;
				for (One o : ones.value()) {
					refs.add(new ExtendAnnotation(true, name, o));
				}
			}
			else if (a instanceof Manys) {
				if (refs == null) {
					refs = new ArrayList<>();
				}
				Manys manys = (Manys)a;
				for (Many o : manys.value()) {
					refs.add(new ExtendAnnotation(true, name, o));
				}
			}
		}
		return refs;
	}
	
	public Collection<ExtendAnnotation> findExtendAnnotations(Object value) throws Exception {
		Collection<ExtendAnnotation> refs = null;
		ExtView extendType = value.getClass().getAnnotation(ExtView.class);
		if (null != extendType) {
			Annotation[] clazzAs = value.getClass().getDeclaredAnnotations();
			refs = this.buildExtendAnnotations(refs, clazzAs, value.getClass().getSimpleName());

			Field[] fields = value.getClass().getDeclaredFields();
			for (Field f : fields) {
				Annotation[] as = f.getDeclaredAnnotations();
				refs = this.buildExtendAnnotations(refs, as, f.getName());
			}
		}
		if (null == refs) {
			refs = CollectionUtils.EMPTY_COLLECTION;
		}
		
		return refs;
	}

	private static boolean isDefault(String[] groups) {
		return (null == groups || groups.length == 0 || ArrayUtils.contains(groups, "default"));
	}

	private static boolean isInGroup(One formula) {
		return isDefault(formula.groups()) || ArrayUtils.contains(formula.groups(), ExtViewContext.get().getGroup());
	}

	private static boolean isInGroup(Many formula) {
		return isDefault(formula.groups()) || ArrayUtils.contains(formula.groups(), ExtViewContext.get().getGroup());
	}

	private static boolean isInGroup(Many2Many formula) {
		return isDefault(formula.groups()) || ArrayUtils.contains(formula.groups(), ExtViewContext.get().getGroup());
	}

	public class ExtendAnnotation {
		private boolean raw;
		private String name;
		private Object mapping;
		public ExtendAnnotation(boolean raw, String name, Object mapping) {
			super();
			this.raw = raw;
			this.name = name;
			this.mapping = mapping;
		}
		public Object getMapping() {
			return mapping;
		}
		public void setMapping(Object mapping) {
			this.mapping = mapping;
		}
		public boolean isRaw() {
			return raw;
		}
		public void setRaw(boolean raw) {
			this.raw = raw;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
