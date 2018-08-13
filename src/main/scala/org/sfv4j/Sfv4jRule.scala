package org.sfv4j

sealed trait Sfv4jRule

case class AndThenRule(rule1: Sfv4jRule, rule2: Sfv4jRule) extends Sfv4jRule

case class DateRule(value: Int) extends Sfv4jRule

sealed trait LengthRestriction

case class MaxLengthRestriction(max: Int) extends LengthRestriction

case class RangeLengthRestriction(min: Int, max: Int) extends LengthRestriction

case class FixLengthRestriction(length: Int) extends LengthRestriction

case class MultiLineRestriction(lines: Int, length: Int) extends LengthRestriction

sealed trait CharType

case object NumericDigitType extends CharType
case object AlphaLetterType extends CharType
case object AnyLetterType extends CharType
case object HexLetterType extends CharType
case object SwiftCharType extends CharType
case object EdifactCharType extends CharType
case object InfoServiceCharType extends CharType

case class CharRule(lr: LengthRestriction, ct: CharType) extends Sfv4jRule

case object OptionalRule extends Sfv4jRule

case object EmptyRule extends Sfv4jRule
