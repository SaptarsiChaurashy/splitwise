package com.ril.commons.utils;

import com.ril.commons.trywith.Failure;
import com.ril.commons.trywith.Try;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Applicatives {
  public static <R, S, T> Try<T> applicativeRun(Try<R> opR, Try<S> opS, BiFunction<R, S, T> fn) {
    return opR.flatMap(r -> opS.map(s -> fn.apply(r, s)));
  }

  public static <R, S, T> Try<T> applicativeRun(
      Supplier<Try<R>> opR, Supplier<Try<S>> opS, BiFunction<R, S, T> fn) {
    return opR.get().flatMap(r -> opS.get().map(s -> fn.apply(r, s)));
  }

  public static <A, B, C, D> Try<D> applicativeRun(
      Supplier<Try<A>> supA,
      Supplier<Try<B>> supB,
      Supplier<Try<C>> supC,
      TriFunction<A, B, C, D> fn) {
    return supA.get().flatMap(a -> supB.get().flatMap(b -> supC.get().map(c -> fn.apply(a, b, c))));
  }

  public static <T, R, S, U> Try<U> applicativeRun(
      Try<T> opT, Try<R> opR, Try<S> opS, TriFunction<T, R, S, U> fn) {
    return opT.flatMap(t -> opR.flatMap(r -> opS.map(s -> fn.apply(t, r, s))));
  }

  public static <R, S, T, U, V> Try<V> applicativeRun(
      Try<R> opR, Try<S> opS, Try<T> opT, Try<U> opU, QuadFunction<R, S, T, U, V> fn) {
    Try<V> tryV =
        opR.flatMap(r -> opS.flatMap(s -> opT.flatMap(t -> opU.map(u -> fn.apply(r, s, t, u)))));
    if (tryV.isSuccess()) return tryV;

    List<Try<?>> exceptions = Arrays.asList(opR, opS, opT, opU);

    List<String> exs =
        exceptions.stream()
            .filter(Try::isFailure)
            .map(t -> (Failure<?>) t)
            .map(f -> f.recover(Throwable::getMessage).get())
            .collect(Collectors.toList());
    return new Failure<>(new IllegalArgumentException(Json.serialize(exs)));
  }

  public static <T> void applicativeRun(Try<T> option2Try, Try<Void> allMatch) {}

  public interface TriFunction<T, R, S, U> {
    U apply(T t, R r, S s);
  }

  public interface QuadFunction<R, S, T, U, V> {
    V apply(R r, S s, T t, U u);
  }

  public interface PentFunction<A, B, C, D, E, F> {
    F apply(A a, B b, C c, D d, E e);
  }
}
