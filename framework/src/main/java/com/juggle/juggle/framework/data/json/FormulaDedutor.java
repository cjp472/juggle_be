package com.juggle.juggle.framework.data.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import com.juggle.juggle.framework.data.json.context.ExtViewContext;
import com.juggle.juggle.framework.data.json.context.KeyValue;
import com.juggle.juggle.framework.data.json.load.Calculator;
import com.juggle.juggle.framework.data.json.meta.Formula;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.annotation.AnnotationUtils;

public class FormulaDedutor {
	public static Collection<KeyValue> deduce(Object bean, Collection<KeyValue> extendObjs) {
		Collection<KeyValue> formulaRets = new ArrayList<>();
		
		Set<Formula> formulas = AnnotationUtils.getDeclaredRepeatableAnnotations(bean.getClass(), Formula.class);
		if (!formulas.isEmpty()) {
			formulaRets = new ArrayList<>();
			try {
				boolean inside = ExtViewContext.get().isInsideCollection();
		        for (Formula formula : formulas) {
					if (isInGroup(formula) &&
							(!inside || formula.batch())) {
			        	Class<Calculator> clazz = (Class<Calculator>)formula.calc();
			        	if (null != clazz) {
			        		Calculator calc = clazz.newInstance();
			        		Object value = calc.calc(bean, extendObjs);
			        		KeyValue kv = new KeyValue(formula.value(), value);
			        		formulaRets.add(kv);
			        	}
					}
		        }
			}
			catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		else {
			formulaRets = CollectionUtils.EMPTY_COLLECTION;
		}
        return formulaRets;
	}

	private static boolean isInGroup(Formula formula) {
		String defaultGroup = null == ExtViewContext.get().getGroup() ? "default" : ExtViewContext.get().getGroup();
		return ArrayUtils.contains(formula.groups(), defaultGroup);
	}
}
