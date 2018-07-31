package org.sfv4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Sfv4jValidator implements ConstraintValidator<Sfv4j, String> {
    @Override
    public void initialize(Sfv4j constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }
}
