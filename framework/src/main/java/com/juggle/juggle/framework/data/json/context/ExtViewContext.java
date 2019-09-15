package com.juggle.juggle.framework.data.json.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtViewContext {
	private static Logger LOG = LoggerFactory.getLogger(ExtViewContext.class);
	private static ThreadLocal<Context> CONTEXT = new ThreadLocal<>();
	
	public static void addExtend(Object object, String key, Object value) {
		LOG.debug("Add Extend for  " + object);

		ExtViewContext.track(object);
		Context ctx = CONTEXT.get();
		Collection<KeyValue> extendSet = (TreeSet<KeyValue>)ctx.keep.get(object.toString());
		
		if (null == extendSet) {
			extendSet = new TreeSet<>();
			ctx.keep.put(object.toString(), extendSet);
		}
		extendSet.add(new KeyValue(key, value));
	}

	public static Collection<KeyValue> getExtends(Object object) {
		LOG.debug("get Extend for  " + object);
		
		Context ctx = CONTEXT.get();
		return (Collection<KeyValue>)ctx.keep.get(object.toString());
	}
	
	public static void track(Object object) {
		Context ctx = CONTEXT.get();
		if (ctx.track.contains(object.toString())) {
			ctx.duplicateTrack++;
			if (ctx.duplicateTrack > 2000) {
				throw new RuntimeException("There might be infinite loop!");
			}
		}
		else {
			ctx.track.add(object.toString());
		}
	}
	
	public static Context get() {
		Context context = CONTEXT.get();
		if (null == context) {
			context = new Context();
			CONTEXT.set(context);
		}
		return context;
	}
	
	public static boolean isTrack(Object obj) {
		Context ctx = CONTEXT.get();
		return ctx.track.contains(obj.toString());
	}
	
	public static void destroy() {
		CONTEXT.remove();
	}
	
	public static class Context {
		private Map<String, Object> keep = new HashMap<>();
		private Set<Object> track = new HashSet<>();
		private int duplicateTrack = 0;
		private int level;
		private boolean insideCollection;
		private String group;
		
		public Map<String, Object> getKeep() {
			return keep;
		}
		public void setKeep(Map<String, Object> keep) {
			this.keep = keep;
		}
		public Set<Object> getTrack() {
			return track;
		}
		public void setTrack(Set<Object> track) {
			this.track = track;
		}
		
		public void addLevel() {
			level++;
		}
		
		public void subLevel() {
			level--;
		}
		
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
		public boolean isInsideCollection() {
			return insideCollection;
		}
		public void setInsideCollection(boolean insideCollection) {
			this.insideCollection = insideCollection;
		}

		public int getDuplicateTrack() {
			return duplicateTrack;
		}

		public void setDuplicateTrack(int duplicateTrack) {
			this.duplicateTrack = duplicateTrack;
		}

		public String getGroup() {
			return group;
		}

		public void setGroup(String group) {
			this.group = group;
		}
	}
}
