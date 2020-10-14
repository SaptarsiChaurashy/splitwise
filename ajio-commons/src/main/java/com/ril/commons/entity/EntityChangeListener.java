package com.ril.commons.entity;

import java.util.Optional;
import java.util.function.BiConsumer;

public interface EntityChangeListener<T> extends BiConsumer<Optional<T>, T> {
  void accept(Optional<T> var1, T var2);
}
