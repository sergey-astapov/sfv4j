package org.sfv4j

import org.scalatest.FunSuite

class Sfv4jCompilerTest extends FunSuite {
  val compiler = Sfv4jCompiler()

  test("Up to 2 digits") {
    assertResult(
      Right(AndThenRule(MaxLengthRule(2), NumericDigitRule))
    )(compiler.compile("2n"))
  }

  test("Exactly 3 uppercase letters") {
    assertResult(
      Right(AndThenRule(FixLengthRule(3), AlphaLetterRule))
    )(compiler.compile("3!a"))
  }

  test("Up to 4 lines of up to 35 characters each") {
    assertResult(
      Right(AndThenRule(MultiLineRule(4, 35),SwiftCharRule))
    )(compiler.compile("4*35x"))
  }

  test("At least 16 and up to 64 hexademical digits") {
    assertResult(
      Right(AndThenRule(RangeLengthRule(16, 64),HexLetterRule))
    )(compiler.compile("16-64h"))
  }

  test("Empty") {
    assertResult(Right(EmptyRule))(compiler.compile("e"))
  }

  test("Optional") {
    assertResult(Right(OptionalRule))(compiler.compile("?"))
  }

  test("Parse error should contains error location") {
    assertResult(
      Left(LexerError(Location(1, 5), "'?' expected but 'j' found"))
    )(compiler.compile("4*35j"))
  }
}
