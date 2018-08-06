package org.sfv4j

import scala.util.parsing.input.Positional

object Sfv4jAst {
  sealed trait Token extends Positional

  case class MaxLength(value: Int) extends Token
  case class FixLength(value: Int) extends Token
  case class RangeLength(min: Int, max: Int) extends Token
  case class MultiLine(maxLines: Int, maxLength: Int) extends Token
  case object NumericDigit extends Token
  case object AlphaLetter extends Token
  case object CharLetter extends Token
  case object HexLetter extends Token
  case object SwiftChar extends Token
  case object EdifactChar extends Token
  case object InfoServiceChar extends Token
  case object EmptyToken extends Token
  case object OptionalToken extends Token
}
