package org.sfv4j;

import org.junit.Before;
import org.junit.Test;
import sun.reflect.annotation.AnnotationParser;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Sfv4jConstraintValidatorTest {
    private Sfv4jConstraintValidator validator;

    @Before
    public void before() {
        validator = new Sfv4jConstraintValidator();
    }

    @Test
    public void test2n() {
        validator.initialize(annotation("2n"));
        assertThat(validator.isValid(null, null), is(false));
        assertThat(validator.isValid(1, null), is(true));
        assertThat(validator.isValid("11", null), is(true));
        assertThat(validator.isValid(111, null), is(false));
    }

    private static Sfv4j annotation(String specs) {
        Map<String, Object> values = new HashMap<>();
        values.put("value", specs);
        return (Sfv4j) AnnotationParser.annotationForMap(Sfv4j.class, values);
    }
}
