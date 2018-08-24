package org.sfv4j

import java.time.MonthDay
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.Locale

import scala.util.Try

sealed trait Sfv4jRule {
  type Result = Either[Sfv4jError, Boolean]

  def validate(obj: Any): Result = validateStr(obj)(filter)

  def filter: (String => Boolean) = s => true

  private def validateStr(obj: Any): (String => Boolean) => Result = f =>
    Option(obj).map(_.toString) match {
      case Some(s) if f(s) => Right(true)
      case opt => Left(ValidationError(s"$this failed, arg: ${opt.orNull}"))
    }
}

case class AndThenRule(r1: Sfv4jRule, r2: Sfv4jRule) extends Sfv4jRule {
  override def validate(obj: Any): Result = (r1.validate(obj), r2.validate(obj)) match {
    case (Right(true), Right(true)) => Right(true)
    case (Left(e1), Left(e2)) => Left(e1 + e2)
    case (Left(e), _) => Left(e)
    case (_, Left(e)) => Left(e)
    case p => Left(ValidationError(s"Unsupported: $p"))
  }
}

sealed trait DateFmt {
  val pattern: String
}

case object DateFmt4 extends DateFmt {
  override val pattern = "MMdd"
}

case object DateFmt6 extends DateFmt {
  override val pattern = "yyMMdd"
}

case object DateFmt8 extends DateFmt {
  override val pattern = "yyyyMMdd"
}

case class DateRule(format: DateFmt) extends Sfv4jRule {
  override def filter: String => Boolean = s => Try {
    DateTimeFormatter
      .ofPattern(format.pattern, Locale.ENGLISH)
      .parse(s, (ta: TemporalAccessor) => MonthDay.from(ta))
  }.isSuccess
}

case class MaxLengthRule(max: Int) extends Sfv4jRule {
  override def filter: String => Boolean = s => s.length <= max
}

case class RangeLengthRule(min: Int, max: Int) extends Sfv4jRule {
  override def filter: String => Boolean = s => s.length >= min && s.length <= max
}

case class FixLengthRule(length: Int) extends Sfv4jRule {
  override def filter: String => Boolean = s => s.length == length
}

case class MultiLineRule(lines: Int, length: Int) extends Sfv4jRule {
  override def filter: String => Boolean = s => s.length <= lines * length
}

case object NumericDigitRule extends Sfv4jRule {
  override def filter: String => Boolean = s => Try(s.toLong).isSuccess
}

case object AlphaLetterRule extends Sfv4jRule {
  override def filter: String => Boolean = s => true
}

case object AnyLetterRule extends Sfv4jRule {
  override def filter: String => Boolean = s => true
}

case object HexLetterRule extends Sfv4jRule {
  override def filter: String => Boolean = s => true
}

case object SwiftCharRule extends Sfv4jRule {
  override def filter: String => Boolean = s => true
}

case object EdifactCharRule extends Sfv4jRule {
  override def filter: String => Boolean = s => true
}

case object InfoServiceCharRule extends Sfv4jRule {
  override def filter: String => Boolean = s => true
}

case object OptionalRule extends Sfv4jRule {
  override def validate(obj: Any): Result = Right(true)
}

case object EmptyRule extends Sfv4jRule {
  override def validate(obj: Any): Result =
    Either.cond(obj == null, true, ValidationError(s"not null arg: $obj"))
}
