package com.juggle.juggle.framework.data.json.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.juggle.juggle.framework.data.json.load.impl.JPALoader;

@Repeatable(value = Manys.class)  
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Many {
	String src() default "";
	String value() default "ext";
	String ref() default "";
	Class<?> target(); 
	Class wrapper() default Void.class;
	String[] fieldset() default {};
	String filter() default "";
	int size() default 100;
	boolean batch() default false;
	Class<?> loader() default JPALoader.class;
	String[] groups() default {"default"};
}
