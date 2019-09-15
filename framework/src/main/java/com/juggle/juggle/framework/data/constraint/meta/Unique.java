package com.juggle.juggle.framework.data.constraint.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target( {java.lang.annotation.ElementType.FIELD })  
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)  
@Documented  
public @interface Unique {  
    String message() default "{com.juggle.validation.Unique.message}";
    String value() default  ALONE;
    String ALONE = "ALONE";
    String[] with() default {};
}  