package org.sfv4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.util.Try;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Sfv4jValidatorTest {
    private static final Logger LOG = LoggerFactory.getLogger(Sfv4jValidatorTest.class);

    private static Sfv4jValidator validator = new Sfv4jValidator();

    @Test
    public void test() {
        Try<Boolean> res = validator.validate(new TestField(null));
        assertThat(res.isSuccess(), is(true));
    }
}
