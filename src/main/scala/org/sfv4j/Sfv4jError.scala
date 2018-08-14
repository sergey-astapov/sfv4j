package org.sfv4j

sealed trait Sfv4jError {
  def +(other: Sfv4jError): Sfv4jError
}

case class LexerError(location: Location, msg: String) extends Sfv4jError {
  override def +(other: Sfv4jError): Sfv4jError = LexerError(location, s"$msg, $other")
}

case class ParserError(msg: String) extends Sfv4jError {
  override def +(other: Sfv4jError): Sfv4jError = ParserError(s"$msg, $other")
}

case class ValidationError(msg: String) extends Sfv4jError {
  override def +(other: Sfv4jError): Sfv4jError = ValidationError(s"$msg, $other")
}

case class Location(line: Int, column: Int) {
  override def toString = s"$line:$column"
}
