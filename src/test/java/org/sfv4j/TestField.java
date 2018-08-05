package org.sfv4j;

public class TestField {
    @Sfv4j("3!a")
    private final String field;

    public TestField(String field) {
        this.field = field;
    }
}
