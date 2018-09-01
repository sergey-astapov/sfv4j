package org.sfv4j

import org.scalatest.FunSuite

class Sfv4jRuleTest extends FunSuite {
  test("MMDD rule") {
    assertResult(Right(true))(DateRule(DateFmt4).validate(1201))
    assertResult(
      Left(ValidationError("DateRule(DateFmt4) failed, arg: 1301"))
    )(DateRule(DateFmt4).validate(1301))
    assertResult(
      Left(ValidationError("DateRule(DateFmt4) failed, arg: 1232"))
    )(DateRule(DateFmt4).validate(1232))
  }

  test("YYMMDD rule") {
    assertResult(Right(true))(DateRule(DateFmt6).validate(181201))
    assertResult(
      Left(ValidationError("DateRule(DateFmt6) failed, arg: 181301"))
    )(DateRule(DateFmt6).validate(181301))
    assertResult(
      Left(ValidationError("DateRule(DateFmt6) failed, arg: 181232"))
    )(DateRule(DateFmt6).validate(181232))
  }

  test("YYYYMMDD rule") {
    assertResult(Right(true))(DateRule(DateFmt8).validate(20181201))
    assertResult(
      Left(ValidationError("DateRule(DateFmt8) failed, arg: 20181301"))
    )(DateRule(DateFmt8).validate(20181301))
    assertResult(
      Left(ValidationError("DateRule(DateFmt8) failed, arg: 20181232"))
    )(DateRule(DateFmt8).validate(20181232))
  }

  test("up to 4 lines of up to 5 characters") {
    assertResult(
      Right(true)
    )(MultiLineRule(4, 5).validate(
      """12345
        |qwert
        |asdfg
        |zxcvb""".stripMargin))
  }

  test("up to 3 lines of up to 5 characters - lines number error") {
    assertResult(
      Left(ValidationError(
        """MultiLineRule(3,5) failed, arg: 12345
          |qwert
          |asdfg
          |zxcvb""".stripMargin))
    )(MultiLineRule(3, 5).validate(
      """12345
        |qwert
        |asdfg
        |zxcvb""".stripMargin))
  }

  test("up to 3 lines of up to 5 characters - line length error") {
    assertResult(
      Left(ValidationError(
        """MultiLineRule(3,5) failed, arg: 12345
          |qwerty
          |asdfg""".stripMargin))
    )(MultiLineRule(3, 5).validate(
      """12345
        |qwerty
        |asdfg""".stripMargin))
  }
}
