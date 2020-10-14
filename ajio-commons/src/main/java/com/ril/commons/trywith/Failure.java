package com.ril.commons.trywith;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class Failure<T> implements Try<T> {
  private Throwable throwable;

  public Failure(Throwable throwable) {
    this.throwable = throwable;
  }

  @Override
  public boolean isSuccess() {
    return false;
  }

  @Override
  public boolean isFailure() {
    return true;
  }

  @Override
  public T get() {
    throw new RuntimeException(this.throwable);
  }

  @Override
  public <R> Try<R> map(Function<? super T, R> fn) {
    return (Try<R>) this;
  }

  @Override
  public Try<T> peek(Consumer<? super T> fn) {
    return (Try<T>) this;
  }

  @Override
  public <R> Try<R> flatMap(Function<? super T, Try<R>> function) {
    return (Try<R>) this;
  }

  @Override
  public void forEach(Consumer<? super T> fn) {}

  @Override
  public void forEachFailure(Consumer<Throwable> fn) { // This can throw exceptions. Todo
    fn.accept(throwable);
  }

  @Override
  public <R> Try<R> recover(Function<Throwable, R> fn) {
    Objects.requireNonNull(fn);
    try {
      return new Success<>(fn.apply(throwable));
    } catch (Throwable throwable) {
      return new Failure<>(throwable);
    }
  }

  @Override
  public <R> Try<R> recoverMap(Function<Throwable, Try<R>> fn) {
    Objects.requireNonNull(fn);
    try {
      return fn.apply(throwable);
    } catch (Throwable throwable) {
      return new Failure<>(throwable);
    }
  }
}
