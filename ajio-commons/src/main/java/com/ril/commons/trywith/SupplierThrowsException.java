package com.ril.commons.trywith;

public interface SupplierThrowsException<T, E extends Throwable> {

  T get()  throws E;
}
