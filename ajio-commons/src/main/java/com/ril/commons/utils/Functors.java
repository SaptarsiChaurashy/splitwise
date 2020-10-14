package com.ril.commons.utils;

import com.ril.commons.trywith.Failure;
import com.ril.commons.trywith.Success;
import com.ril.commons.trywith.Try;

import java.util.Optional;

public class Functors {
  public static <T> Try<T> Options2Try(Optional<T> op, Throwable ex) {
    return op.<Try<T>>map(Success::new).orElseGet(() -> new Failure<>(ex));
  }
}
