package org.sfv4j

object Sfv4jCompiler {
  def apply(code: String): Either[Sfv4jError, Sfv4jRule] = {
    for {
      tokens <- Sfv4jLexer(code)
      ast <- Sfv4jRuleParser(tokens)
    } yield ast
  }
}