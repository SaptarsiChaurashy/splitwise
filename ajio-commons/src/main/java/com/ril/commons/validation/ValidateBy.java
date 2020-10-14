package com.ril.commons.validation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateBy {

  /**
   * Class name of the validator
   *
   * @return
   */
  Class<?>[] clazz();

  /**
   * Prefix to look for in methods
   *
   * @return
   */
  String methodPrefix() default "is";

  /**
   * If any one validation fails, return
   *
   * @return
   */
  boolean isLazy() default true;
}
