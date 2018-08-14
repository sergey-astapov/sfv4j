package org.sfv4j

import scala.util.parsing.combinator.RegexParsers

object Sfv4jLexer extends RegexParsers {
  private def length: Parser[Sfv4jToken] = "\\d+".r ^^ (str => LengthToken(str.toInt))

  private def fixLength: Parser[Sfv4jToken] = "!" ^^ (_ => FixLengthToken)

  private def rangeLength: Parser[Sfv4jToken] = "-" ^^ (_ => RangeLengthToken)

  private def multiLine: Parser[Sfv4jToken] = "*" ^^ (_ => MultiLineToken)

  private def lengthType: Parser[Sfv4jToken] = fixLength | rangeLength | multiLine

  private def numericDigit: Parser[Sfv4jToken] = "n" ^^ (_ => NumericDigitToken)

  private def alphaLetter: Parser[Sfv4jToken] = "a" ^^ (_ => AlphaLetterToken)

  private def anyLetter: Parser[Sfv4jToken] = "c" ^^ (_ => AnyLetterToken)

  private def hexLetter: Parser[Sfv4jToken] = "h" ^^ (_ => HexLetterToken)

  private def swiftChar: Parser[Sfv4jToken] = "x" ^^ (_ => SwiftCharToken)

  private def edifactChar: Parser[Sfv4jToken] = "y" ^^ (_ => EdifactCharToken)

  private def infoServiceChar: Parser[Sfv4jToken] = "z" ^^ (_ => InfoServiceCharToken)

  private def char: Parser[Sfv4jToken] = numericDigit |
    alphaLetter | anyLetter | hexLetter |
    swiftChar | edifactChar | infoServiceChar

  private def empty: Parser[Sfv4jToken] = "e" ^^ (_ => EmptyToken)

  private def optional: Parser[Sfv4jToken] = "?" ^^ (_ => OptionalToken)

  private def dateOf4: Parser[Sfv4jToken] = "4!n" ^^ (_ => DateToken(4))

  private def dateOf6: Parser[Sfv4jToken] = "4!n" ^^ (_ => DateToken(6))

  private def dateOf8: Parser[Sfv4jToken] = "4!n" ^^ (_ => DateToken(8))

  private def date: Parser[Sfv4jToken] = dateOf4 | dateOf6 | dateOf8

  def tokens: Parser[List[Sfv4jToken]] = phrase(rep(date | length | lengthType | char | empty | optional))

  def apply(code: String): Either[LexerError, List[Sfv4jToken]] = {
    parse(tokens, code) match {
      case NoSuccess(msg, next) => Left(LexerError(Location(next.pos.line, next.pos.column), msg))
      case Success(result, _) => Right(result)
    }
  }
}
