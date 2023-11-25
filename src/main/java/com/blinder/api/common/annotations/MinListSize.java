package com.blinder.api.common.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinListSizeValidator.class)
@NotNull(message = "List cannot be null")
@ReportAsSingleViolation
public @interface MinListSize {
    String message() default "List must have at least {minSize} elements";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int minSize() default 3;
}
