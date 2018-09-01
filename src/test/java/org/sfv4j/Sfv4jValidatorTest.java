package org.sfv4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Sfv4jValidatorTest {
    private static final Logger LOG = LoggerFactory.getLogger(Sfv4jValidatorTest.class);

    private static Sfv4jValidator validator = new Sfv4jValidator(new Sfv4jCompiler());

    @Test
    public void test() {
        User user = new User(null, null, null);
        Sfv4jResult res = validator.validate(user);
        LOG.info("res: {}", res);
        assertThat(res.isSuccess(), is(false));
        Sfv4jFailure failure = (Sfv4jFailure)res;
        assertThat(failure.msg().isEmpty(), is(false));
    }
}
