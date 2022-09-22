package net.danizen.bakeoff.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ FIELD, METHOD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = PunctuationFreeValidator.class)
@Documented
public @interface PunctuationFree {
    String message() default "should not contain punctuation characters";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
