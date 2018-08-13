package org.sfv4j

import scala.util.parsing.input.Positional

sealed abstract trait Sfv4jToken extends Positional

case class LengthToken(value: Int) extends Sfv4jToken

sealed abstract trait LengthRestrictionToken extends Sfv4jToken

case object FixLengthToken extends LengthRestrictionToken
case object RangeLengthToken extends LengthRestrictionToken
case object MultiLineToken extends LengthRestrictionToken

case class DateToken(value: Int) extends Sfv4jToken

sealed abstract trait CharToken extends Sfv4jToken

case object NumericDigitToken extends CharToken
case object AlphaLetterToken extends CharToken
case object AnyLetterToken extends CharToken
case object HexLetterToken extends CharToken
case object SwiftCharToken extends CharToken
case object EdifactCharToken extends CharToken
case object InfoServiceCharToken extends CharToken

case object EmptyToken extends Sfv4jToken
case object OptionalToken extends Sfv4jToken


