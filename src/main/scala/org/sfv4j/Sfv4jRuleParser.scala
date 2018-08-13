package org.sfv4j

import org.sfv4j.Sfv4jError.ParserError

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.{NoPosition, Position, Reader}

object Sfv4jRuleParser extends Parsers {
  override type Elem = Sfv4jToken

  def program: Parser[Sfv4jRule] = {
    phrase(block)
  }

  def block: Parser[Sfv4jRule] = {
    rep1(fixLength | rangeLength) ^^ {
      stmtList => stmtList reduceRight AndThenRule
    }
  }

  def length: Parser[LengthToken] = accept("length", {
    case lt@LengthToken(_) => lt
  })

  private def fixLength =
    (length ~ FixLengthToken ~ NumericDigitToken) ^^ {
      case lt ~ _ ~ _ => CharRule(FixLengthRestriction(lt.value), NumericDigitType)
    }

  private def rangeLength =
    (length ~ FixLengthToken ~ NumericDigitToken) ^^ {
      case lt ~ _ ~ _ => CharRule(FixLengthRestriction(lt.value), NumericDigitType)
    }

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
