package com.juggle.juggle.framework.data.json.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.juggle.juggle.framework.data.json.load.impl.JPALoader;

@Repeatable(value = Ones.class)  
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface One {
	String src() default "";
	String value() default "ext";
	String ref() default "id";
	Class<?> target(); 
	String[] fieldset() default {};
	Class wrapper() default Void.class;
	String filter() default "";
	Class<?> loader() default JPALoader.class;
	boolean batch() default true;
	String[] groups() default {"default"};
}
