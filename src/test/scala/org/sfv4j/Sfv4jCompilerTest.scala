package org.sfv4j

import org.scalatest.FunSuite
import org.sfv4j.Sfv4jError.{LexerError, Location}

class Sfv4jCompilerTest extends FunSuite {
  test("Up to 2 digits") {
    assertResult(
      Right(CharRule(MaxLengthRestriction(2), NumericDigitType))
    )(Sfv4jCompiler("2n"))
  }

  test("Exactly 3 uppercase letters") {
    assertResult(
      Right(CharRule(FixLengthRestriction(3), AlphaLetterType))
    )(Sfv4jCompiler("3!a"))
  }

  test("Up to 4 lines of up to 35 characters each") {
    assertResult(
      Right(CharRule(MultiLineRestriction(4, 35), SwiftCharType))
    )(Sfv4jCompiler("4*35x"))
  }

  test("At least 16 and up to 64 hexademical digits") {
    assertResult(
      Right(CharRule(RangeLengthRestriction(16, 64), HexLetterType))
    )(Sfv4jCompiler("16-64h"))
  }

  test("Empty") {
    assertResult(Right(EmptyRule))(Sfv4jCompiler("e"))
  }

  test("Optional") {
    assertResult(Right(OptionalRule))(Sfv4jCompiler("?"))
  }

  test("Parse error should contains error location") {
    assertResult(Left(LexerError(Location(1, 5), "'?' expected but 'j' found")))(Sfv4jCompiler("4*35j"))
  }
}
