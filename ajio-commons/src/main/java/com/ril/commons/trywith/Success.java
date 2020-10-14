package com.ril.commons.trywith;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class Success<T> implements Try<T>  {
  private T object;

  public Success(T t) {
    this.object = t;
  }

  @Override
  public boolean isSuccess() {
    return true;
  }

  @Override
  public boolean isFailure() {
    return false;
  }

  @Override
  public T get() {
    return object;
  }

  @Override
  public <R> Try<R> map(Function<? super T, R> fn) {
    Objects.requireNonNull(fn);
    try {
      return new Success<>(fn.apply(object));
    } catch (Throwable throwable) {
      return new Failure<>(throwable);
    }
  }

  @Override
  public Try<T> peek(Consumer<? super T> fn) {
    Objects.requireNonNull(fn);
    fn.accept(object);
    try {
      return new Success<>(object);
    } catch (Throwable throwable) {
      return new Failure<>(throwable);
    }
  }

  @Override
  public <R> Try<R> flatMap(Function<? super T, Try<R>> function) {
    Objects.requireNonNull(function);
    try {
      return function.apply(object);
    } catch (Throwable throwable) {
      return new Failure<>(throwable);
    }
  }

  @Override
  public void forEach(Consumer<? super T> fn) {
    fn.accept(object);
  }

  @Override
  public void forEachFailure(Consumer<Throwable> fn) {}

  @Override
  public <R> Try<R> recover(Function<Throwable, R> fn) {
    return (Try<R>) this;
  }

  @Override
  public <R> Try<R> recoverMap(Function<Throwable, Try<R>> function) {
    return (Try<R>) this;
  }
}
