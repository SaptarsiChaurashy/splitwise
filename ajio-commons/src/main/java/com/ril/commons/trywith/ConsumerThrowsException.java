package com.ril.commons.trywith;

public interface ConsumerThrowsException<T, E extends Throwable> {
  void accept(T t) throws E;
}
