package org.sfv4j

import org.scalatest.FunSuite
import org.sfv4j.Sfv4jError.{LexerError, Location}

class Sfv4jLexerTest extends FunSuite {
  test("Up to 2 digits") {
    assertResult(Right(List(LengthToken(2), NumericDigitToken)))(Sfv4jLexer("2n"))
  }

  test("Exactly 3 uppercase letters") {
    assertResult(Right(List(LengthToken(3), FixLengthToken, AlphaLetterToken)))(Sfv4jLexer("3!a"))
  }

  test("Up to 4 lines of up to 35 characters each") {
    assertResult(
      Right(List(LengthToken(4), MultiLineToken, LengthToken(35), SwiftCharToken))
    )(Sfv4jLexer("4*35x"))
  }

  test("At least 16 and up to 64 hexademical digits") {
    assertResult(
      Right(List(LengthToken(16), RangeLengthToken, LengthToken(64), HexLetterToken))
    )(Sfv4jLexer("16-64h"))
  }

  test("Empty") {
    assertResult(Right(List(EmptyToken)))(Sfv4jLexer("e"))
  }

  test("Optional") {
    assertResult(Right(List(OptionalToken)))(Sfv4jLexer("?"))
  }

  test("Parse error should contains error location") {
    assertResult(Left(LexerError(Location(1, 5), "'?' expected but 'j' found")))(Sfv4jLexer("4*35j"))
  }
}
