package org.sfv4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Sfv4jConstraintValidator implements ConstraintValidator<Sfv4j, Object> {
    private static final Logger LOG = LoggerFactory.getLogger(Sfv4jConstraintValidator.class);

    private String specs;

    @Override
    public void initialize(Sfv4j constraint) {
        this.specs = constraint.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LOG.info("specs: {}, value: {}", specs, value);
        return false;
    }
}
