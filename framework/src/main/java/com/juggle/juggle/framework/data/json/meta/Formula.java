package com.juggle.juggle.framework.data.json.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.juggle.juggle.framework.data.json.load.Calculator;

@Repeatable(value = Formulas.class) 
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Formula {
	String value() default "ret";
	String expression() default "";
	Class<? extends Calculator> calc();
	boolean batch() default true;
	String[] groups() default {"default"};
}
