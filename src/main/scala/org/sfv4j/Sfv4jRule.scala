package org.sfv4j

sealed trait Sfv4jRule {
  def +(other: Sfv4jRule): Sfv4jRule = (this, other) match {
    case (CompRule(rules1), CompRule(rules2)) => CompRule(rules1 ++ rules2)
    case (CompRule(rules1), rule) => CompRule(rules1 ++ List(rule))
    case (rule, CompRule(rules2)) => CompRule(List(rule) ++ rules2)
  }
}

case class CompRule(rules: Seq[Sfv4jRule]) extends Sfv4jRule

case class DateRule(value: Int) extends Sfv4jRule

case object OptionalRule extends Sfv4jRule
case object EmptyRule extends Sfv4jRule
