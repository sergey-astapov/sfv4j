package org.sfv4j

import org.sfv4j.Sfv4jError.CompilationError

object Sfv4jCompiler {
  def apply(code: String): Either[CompilationError, Sfv4jRule] = {
    for {
      tokens <- Sfv4jLexer(code)
      ast <- Sfv4jRuleParser(tokens)
    } yield ast
  }
}