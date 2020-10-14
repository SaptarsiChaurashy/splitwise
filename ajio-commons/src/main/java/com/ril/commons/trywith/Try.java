package com.ril.commons.trywith;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Try<T> {

  static <T> Try<T> with(Supplier<T> supplier) {
    Objects.requireNonNull(supplier);
    SupplierThrowsException<T, Throwable> supplierThrowsException = supplier::get;
    return Try.withThrowable(supplierThrowsException);
  }

  static <T, E extends Throwable> Try<T> withThrowable(SupplierThrowsException<T, E> ste) {
    Objects.requireNonNull(ste);
    try {
      return new Success<>(ste.get());
    } catch (Throwable e) {
      return new Failure<>(e);
    }
  }

  static <T, E extends Throwable> Try<T> withThrowable(ConsumerThrowsException<T, E> ste, T t) {
    Objects.requireNonNull(ste);
    SupplierThrowsException<T, E> supplierWithException =
        () -> {
          ste.accept(t);
          return t;
        };
    try {
      return new Success<>(supplierWithException.get());
    } catch (Throwable e) {
      return new Failure<>(e);
    }
  }

  static <T> Try<T> with(Consumer<T> fn, T t) {
    Objects.requireNonNull(fn);
    Supplier<T> supplier =
        () -> {
          fn.accept(t);
          return t;
        };
    return Try.with(supplier);
  }

  boolean isSuccess();

  boolean isFailure();

  T get();

  <R> Try<R> map(Function<? super T, R> fn);

  Try<T> peek(Consumer<? super T> fn);

  <R> Try<R> flatMap(Function<? super T, Try<R>> function);

  <R> Try<R> recover(Function<Throwable, R> fn);

  <R> Try<R> recoverMap(Function<Throwable, Try<R>> function);

  void forEach(Consumer<? super T> fn);

  void forEachFailure(Consumer<Throwable> fn);
}
