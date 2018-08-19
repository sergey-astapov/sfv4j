package org.sfv4j

class Sfv4jCompiler {
  def compile(code: String): Either[Sfv4jError, Sfv4jRule] = {
    for {
      tokens <- Sfv4jLexer(code)
      ast <- Sfv4jRuleParser(tokens)
    } yield ast
  }
}

object Sfv4jCompiler {
  def apply(): Sfv4jCompiler = new Sfv4jCompiler()
}