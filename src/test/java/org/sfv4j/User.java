package org.sfv4j;

public class User {
    @Sfv4j("10a")
    private final String firstName;
    @Sfv4j("10a")
    private final String lastName;
    @Sfv4j("YYMMDD")
    private final Integer birthday;

    public User(String firstName, String lastName, Integer birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }
}
