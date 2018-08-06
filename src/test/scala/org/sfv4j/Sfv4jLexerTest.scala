package org.sfv4j

import org.scalatest.FunSuite
import org.sfv4j.Sfv4jAst._

class Sfv4jLexerTest extends FunSuite {
  test("Max Length") {
    assertResult(Right(List(MaxLength(3))))(Sfv4jLexer("3"))
  }

  test("Fix Length") {
    assertResult(Right(List(FixLength(3))))(Sfv4jLexer("3!"))
  }

  test("Range Length") {
    assertResult(Right(List(RangeLength(3, 10))))(Sfv4jLexer("3-10"))
  }

  test("Empty") {
    assertResult(Right(List(EmptyToken)))(Sfv4jLexer("e"))
  }

  test("Optional") {
    assertResult(Right(List(OptionalToken)))(Sfv4jLexer("?"))
  }
}
