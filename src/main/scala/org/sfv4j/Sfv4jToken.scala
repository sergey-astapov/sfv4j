package org.sfv4j

import scala.util.parsing.input.Positional

sealed abstract trait Sfv4jToken extends Positional

case class LengthToken(value: Int) extends Sfv4jToken

case object FixLengthToken extends Sfv4jToken

case object RangeLengthToken extends Sfv4jToken

case object MultiLineToken extends Sfv4jToken

case class DateToken(value: Int) extends Sfv4jToken

case object NumericDigitToken extends Sfv4jToken

case object AlphaLetterToken extends Sfv4jToken

case object AnyLetterToken extends Sfv4jToken

case object HexLetterToken extends Sfv4jToken

case object SwiftCharToken extends Sfv4jToken

case object EdifactCharToken extends Sfv4jToken

case object InfoServiceCharToken extends Sfv4jToken

case object EmptyToken extends Sfv4jToken

case object OptionalToken extends Sfv4jToken


