package org.sfv4j

import org.sfv4j.Sfv4jError.{Location, ParserError}

import scala.util.parsing.combinator.{Parsers, RegexParsers}
import scala.util.parsing.input.{NoPosition, Position, Reader}

object Sfv4jParser extends RegexParsers {
//  override type Elem = Sfv4jToken
//
//  def program: Parser[Sfv4jRule] = {
//    //phrase(block)
//    throw new NotImplementedError
//  }

//  def block: Parser[Sfv4jRule] = {
//    rep1(statement) ^^ { case stmtList => stmtList reduceRight AndThen }
//  }

//  def apply(tokens: Seq[Elem]): Either[ParserError, Sfv4jRule] = {
//    val reader = new Sfv4jTokenReader(tokens)
//    program(reader) match {
//      case NoSuccess(msg, next) => Left(ParserError(msg))
//      case Success(result, next) => Right(result)
//    }
//  }

//  def apply(tokens: Seq[Sfv4jToken]): Either[ParserError, Sfv4jRule] = tokens.toList match {
//    case DateToken(n) :: tail => parseOptional(tail) map (r => DateRule(n) + r)
//    case
//  }
//
//  private def parseOptional(tokens: Seq[Sfv4jToken]): Either[ParserError, Sfv4jRule] = tokens.toList match {
//    case Nil => Right(EmptyRule)
//    case OptionalToken :: Nil => Right(OptionalRule)
//    case other => Left(ParserError(s"unsupported tokens: $other"))
//  }

}

class Sfv4jTokenReader(tokens: Seq[Sfv4jToken]) extends Reader[Sfv4jToken] {
  override def first: Sfv4jToken = tokens.head
  override def atEnd: Boolean = tokens.isEmpty
  override def pos: Position = NoPosition
  override def rest: Reader[Sfv4jToken] = new Sfv4jTokenReader(tokens.tail)
}
