package com.juggle.juggle.framework.common.validation.meta;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = {ChineseLenValidator.class})
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChineseLength {  
    String message() default "{com.juggle.validation.ChineseLength.message}";
    int value() default 100;
    int min() default 0;
    Class<?>[] groups() default {};  
    Class<? extends Payload>[] payload() default {};  
}  