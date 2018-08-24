package org.sfv4j

import java.lang.reflect.Field

sealed trait Sfv4jResult {
  def isSuccess: java.lang.Boolean = this == Sfv4jSuccess
}

case object Sfv4jSuccess extends Sfv4jResult

case class Sfv4jFailure(msg: String) extends Sfv4jResult

class Sfv4jValidator(compiler: Sfv4jCompiler) {
  def validate(obj: Any): Sfv4jResult = {
    val errors = obj.getClass.getDeclaredFields
      .map(f => {
        f.setAccessible(true)
        f
      })
      .filter(f => f.isAnnotationPresent(classOf[Sfv4j]))
      .map(f => compiler.compile(key(f)).flatMap(r => r.validate(f.get(obj))))
      .flatMap(_.left.toOption).toList
    if (errors.isEmpty) Sfv4jSuccess else Sfv4jFailure(errors.reduce((e1, e2) => e1 + e2).toString)
  }

  def validateField(obj: Any, spec: String): Sfv4jResult = compiler.compile(spec)
    .flatMap(r => r.validate(obj)) match {
    case Right(true) => Sfv4jSuccess
    case Right(false) => Sfv4jFailure(s"'$spec' failed, obj: $obj")
    case Left(e) => Sfv4jFailure(e.toString)
  }

  private def key(field: Field) = {
    val value = field.getAnnotation(classOf[Sfv4j]).value
    if (value.isEmpty) field.getName else value
  }
}

object Sfv4jValidator {
  def apply(): Sfv4jValidator = apply(new Sfv4jCompiler())

  def apply(compiler: Sfv4jCompiler): Sfv4jValidator = new Sfv4jValidator(compiler)
}
