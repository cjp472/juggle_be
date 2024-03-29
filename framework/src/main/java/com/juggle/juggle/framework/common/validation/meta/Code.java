package com.juggle.juggle.framework.common.validation.meta;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {})
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
@Pattern(regexp = "")
public @interface Code {
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@OverridesAttribute(constraint = Pattern.class, name = "regexp")
	String regexp() default "[0-9a-zA-Z\\_\\.]*";

	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "{Code.incorrect}";

}
