package com.juggle.juggle.framework.cache.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Cache {
	String sector() default "juggle";
	String value() default "";
	int lifetime() default 60 * 15;
	boolean cacheNull() default true; 
}