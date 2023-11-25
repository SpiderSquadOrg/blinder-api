package com.blinder.api.common.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class MinListSizeValidator implements ConstraintValidator<MinListSize, List<?>> {
    private int minSize;

    @Override
    public void initialize(MinListSize constraintAnnotation) {
        this.minSize = constraintAnnotation.minSize();
    }

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        return value == null || value.size() >= minSize;
    }
}
