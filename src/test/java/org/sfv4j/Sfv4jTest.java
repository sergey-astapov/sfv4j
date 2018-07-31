package org.sfv4j;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Sfv4jTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static class TestField {
        @Sfv4j("?(10n)")
        private final String field;

        public TestField(String field) {
            this.field = field;
        }
    }

    @Test
    public void test() {
        Set<ConstraintViolation<TestField>> set = validator.validate(new TestField(null));
        assertThat(set.size(), is(0));
    }
}
