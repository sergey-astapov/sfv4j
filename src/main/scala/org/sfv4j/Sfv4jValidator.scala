package org.sfv4j

import java.lang.reflect.Field

import org.slf4j.LoggerFactory

import scala.util.Try

class Sfv4jValidator {
  private val LOG = LoggerFactory.getLogger(classOf[Sfv4jValidator])

  def validate(obj: Any): Try[java.lang.Boolean] = Try {
    val objectClass = obj.getClass
    val map = objectClass.getDeclaredFields
      .map(f => {
        f.setAccessible(true)
        f
      })
      .filter(f => f.isAnnotationPresent(classOf[Sfv4j]))
      .map(f => (key(f), f.get(obj)))
      .toMap
    LOG.info("map: {}", map)
    true
  }

  private def key(field: Field) = {
    val value = field.getAnnotation(classOf[Sfv4j]).value
    if (value.isEmpty) field.getName else value
  }
}
