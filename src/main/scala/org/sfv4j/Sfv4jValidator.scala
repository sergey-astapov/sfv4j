package org.sfv4j

import java.lang.reflect.Field

sealed trait Sfv4jResult {
  def isSuccess: java.lang.Boolean = this == Sfv4jSuccess
}

case object Sfv4jSuccess extends Sfv4jResult

case class Sfv4jFailure(msg: String) extends Sfv4jResult

class Sfv4jValidator {
  def validate(obj: Any): Sfv4jResult = {
    val errors = obj.getClass.getDeclaredFields
      .map(f => {
        f.setAccessible(true)
        f
      })
      .filter(f => f.isAnnotationPresent(classOf[Sfv4j]))
      .map(f => Sfv4jCompiler(key(f)).flatMap(r => r.validate(f.get(obj))))
      .flatMap(_.left.toOption).toList
    if (errors.isEmpty) Sfv4jSuccess else Sfv4jFailure(errors.reduce((e1, e2) => e1 + e2).toString)
  }

  private def key(field: Field) = {
    val value = field.getAnnotation(classOf[Sfv4j]).value
    if (value.isEmpty) field.getName else value
  }
}
