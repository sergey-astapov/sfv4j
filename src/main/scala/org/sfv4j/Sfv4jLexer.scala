package org.sfv4j

import org.sfv4j.Sfv4jAst._
import org.sfv4j.Sfv4jError.{LexerError, Location}

import scala.util.parsing.combinator.RegexParsers


object Sfv4jLexer extends RegexParsers {
  private def maxLength = "\\d+".r ^^ {
    str => MaxLength(str.toInt)
  }

  private def fixLength = "\\d+".r ^^ {
    str => MaxLength(str.toInt)
  }

  private def numericDigit    = "n" ^^ (_ => NumericDigit)
  private def alphaLetter     = "a" ^^ (_ => AlphaLetter)
  private def charLetter      = "c" ^^ (_ => CharLetter)
  private def hexLetter       = "h" ^^ (_ => HexLetter)
  private def swiftChar       = "x" ^^ (_ => SwiftChar)
  private def edifactChar     = "y" ^^ (_ => EdifactChar)
  private def infoServiceChar = "z" ^^ (_ => InfoServiceChar)
  private def empty           = "e" ^^ (_ => EmptyToken)
  private def optional        = "?" ^^ (_ => OptionalToken)

  def tokens: Parser[List[Token]] = {
    phrase(rep(maxLength | fixLength | empty | optional))
  }

  def apply(code: String): Either[LexerError, List[Token]] = {
    parse(tokens, code) match {
      case NoSuccess(msg, next) => Left(LexerError(Location(next.pos.line, next.pos.column), msg))
      case Success(result, _) => Right(result)
    }
  }
}
