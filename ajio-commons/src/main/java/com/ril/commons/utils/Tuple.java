package com.ril.commons.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Tuple<K, V> {

  private K left;
  private V right;
}
