package org.sfv4j;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Sfv4jConstraintValidatorTest {
    private static final Logger LOG = LoggerFactory.getLogger(Sfv4jConstraintValidatorTest.class);

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void test() {
        Set<ConstraintViolation<TestField>> set = validator.validate(new TestField(null));
        set.forEach(v -> LOG.info(v.toString()));

        assertThat(set.size(), is(0));
    }
}
