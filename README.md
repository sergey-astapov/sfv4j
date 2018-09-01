sfv4j
[![Travis](https://img.shields.io/travis/sergey-astapov/sfv4j.svg)](https://travis-ci.org/sergey-astapov/sfv4j)
=========

This is Simple Field Validation library for Java.

This library provides simple API to validate class instance fields for correctness with complex validation rules.

## How to Use

Consider following class **User**
```
public class User {
    @Sfv4j("10a")
    private final String firstName;
    
    @Sfv4j("10-20a")
    private final String lastName;
    
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
```
**User** class has following restrictions for its fields:
* *firstName* - up to 10 uppercase alphabetic letters
* *lastName* - at least 10 and up to 20 uppercase alphabetic letters

Let's validate the instance of the **User** class for correctness
```
Sfv4jValidator validator = new Sfv4jValidator(new Sfv4jCompiler());
Sfv4jResult res = validator.validate(new User(null, null));
if (!res.isSuccess()) {
    Sfv4jFailure failure = (Sfv4jFailure)res;
    System.out.println(failure.msg());
}
```

## Validation Rules

### Restrictions on Length

* **nn** - max length (min is 1)
* **nn-nn** - min and max length
* **nn!** - fixed length
* **nn\*nn** - max number of lines times max line length

### Types of Characters Allowed

* **n** - 0-9 digits only
* **a** - A-Z letters only
* **c** - A-Z letters and 0-9 digits only
* **h** - A-F hex letters only
* **x** - SWIFT character set
* **y** - EDIFACT character set

### Date Formats

* **MMDD** - month and day
* **YYMMDD** - year, month and day
* **YYYYMMDD** - year, month and day

### Special

* **e** - blank
* **?** - optional

## Examples

* **2n** - up to 2 digits
* **3!a** - exactly 3 uppercase letters
* **4\*35x** - up to 4 lines of up to 35 characters each
* **16-64h** - at least 16 and up to 64 hex digits


