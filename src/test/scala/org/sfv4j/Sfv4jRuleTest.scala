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
}
