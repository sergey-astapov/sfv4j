package org.sfv4j

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.{NoPosition, Position, Reader}

object Sfv4jRuleParser extends Parsers {
  override type Elem = Sfv4jToken

  def program: Parser[Sfv4jRule] = {
    phrase(block)
  }

  def block: Parser[Sfv4jRule] = {
    rep1(date |
      fixLength | rangeLength | multiLine | maxLength |
      numericDigit | alphaLetter | anyLetter | hexLetter |
      swiftChar | edifactChar | infoServiceChar |
      optional | empty) ^^ {
      stmtList => stmtList reduceRight AndThenRule
    }
  }

  private def dateToken = accept("date", {
    case dt @ DateToken(_) => dt
  })

  private def date = dateToken ^^ (dt => DateRule(dt.format))

  private def lengthToken = accept("length", {
    case lt @ LengthToken(_) => lt
  })

  private def maxLength = lengthToken ^^ (lt => MaxLengthRule(lt.value))

  private def fixLength = (lengthToken ~ FixLengthToken) ^^ {
    case lt ~ _ => FixLengthRule(lt.value)
  }

  private def rangeLength = (lengthToken ~ RangeLengthToken ~ lengthToken) ^^ {
    case lt1 ~ _ ~ lt2 => RangeLengthRule(lt1.value, lt2.value)
  }

  private def multiLine = (lengthToken ~ MultiLineToken ~ lengthToken) ^^ {
    case lt1 ~ _ ~ lt2 => MultiLineRule(lt1.value, lt2.value)
  }

  private def numericDigit = NumericDigitToken ^^ (_ => NumericDigitRule)

  private def alphaLetter = AlphaLetterToken ^^ (_ => AlphaLetterRule)

  private def anyLetter = AlphaLetterToken ^^ (_ => AlphaLetterRule)

  private def hexLetter = HexLetterToken ^^ (_ => HexLetterRule)

  private def swiftChar = SwiftCharToken ^^ (_ => SwiftCharRule)

  private def edifactChar = EdifactCharToken ^^ (_ => EdifactCharRule)

  private def infoServiceChar = InfoServiceCharToken ^^ (_ => InfoServiceCharRule)

  private def optional = OptionalToken ^^ (_ => OptionalRule)

  private def empty = EmptyToken ^^ (_ => EmptyRule)

  def apply(tokens: Seq[Elem]): Either[ParserError, Sfv4jRule] = {
    val reader = new Sfv4jTokenReader(tokens)
    program(reader) match {
      case NoSuccess(msg, _) => Left(ParserError(msg))
      case Success(result, _) => Right(result)
    }
  }
}

class Sfv4jTokenReader(tokens: Seq[Sfv4jToken]) extends Reader[Sfv4jToken] {
  override def first: Sfv4jToken = tokens.head

  override def atEnd: Boolean = tokens.isEmpty

  override def pos: Position = NoPosition

  override def rest: Reader[Sfv4jToken] = new Sfv4jTokenReader(tokens.tail)
}
