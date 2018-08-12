package org.sfv4j

object Sfv4jError {
  trait CompilationError
  case class LexerError(location: Location, msg: String) extends CompilationError
  case class ParserError(msg: String) extends CompilationError

  case class Location(line: Int, column: Int) {
    override def toString = s"$line:$column"
  }
}
