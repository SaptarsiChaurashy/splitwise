package com.ril.commons.tryWith;

import com.ril.commons.trywith.Try;
import org.junit.Assert;
import org.junit.Test;

public class TryTest {
  @Test
  public void isSuccess() throws Exception {
    Try<String> stringTry = Try.with(() -> "Value");
    Assert.assertTrue(stringTry.isSuccess());
    Assert.assertFalse(stringTry.isFailure());
  }

  @Test
  public void isFailure() throws Exception {
    Try exTry =
        Try.with(
            () -> {
              throw new IllegalArgumentException();
            });
    Assert.assertTrue(exTry.isFailure());

    exTry =
        Try.withThrowable(
            () -> {
              throw new Throwable();
            });

    Assert.assertTrue(exTry.isFailure());
    Assert.assertFalse(exTry.isSuccess());
  }

  @Test
  public void get() throws Exception {
    Try<String> stringTry = Try.with(() -> "Value");
    Assert.assertEquals(stringTry.get(), "Value");
  }

  @Test
  public void map() throws Exception {
    Try<String> stringTry = Try.with(() -> "Value").map(s -> "Value2");
    Assert.assertEquals(stringTry.get(), "Value2");
  }

  @Test
  public void flatMap() throws Exception {}

  @Test
  public void forEach() throws Exception {
    final Long[] counter = {0L};
    Try.with(() -> 2L).forEach(aLong -> counter[0]++);
    Assert.assertEquals(counter[0], Long.valueOf(1));
    Try.with(() -> 2L).map(aLong -> aLong * 2).forEach(aLong -> counter[0]++);
    Assert.assertEquals(counter[0], Long.valueOf(2));
  }

  @Test
  public void forEachFailure() throws Exception {
    final String[] counter = {""};
    Try.with(
            () -> {
              throw new IllegalArgumentException("Thrown");
            })
        .forEachFailure(throwable -> counter[0] = throwable.getMessage());
    Assert.assertEquals(counter[0], "Thrown");
  }

  @Test
  public void recover() throws Exception {
    Try<String> recoveredTry =
        Try.with(
                () -> {
                  throw new IllegalArgumentException("Thrown");
                })
            .recover(throwable -> "Value");
    Assert.assertTrue(recoveredTry.isSuccess());
    Assert.assertEquals(recoveredTry.get(), "Value");
  }

  @Test
  public void recoverMap() throws Exception {}

  @Test
  public void withSupplier() throws Exception {
    Try<Long> longTry = Try.with(() -> 2L);
    Assert.assertTrue(longTry.isSuccess());
    Assert.assertEquals(longTry.get(), Long.valueOf(2L));
  }

  @Test
  public void withConsumer() throws Exception {
    final String[] counter = {""};
    Try<String> stringTry = Try.with(t -> counter[0] = t, "Value");
    Assert.assertTrue(stringTry.isSuccess());
    Assert.assertEquals(stringTry.get(), "Value");
    Assert.assertEquals(counter[0], "Value");

    stringTry = Try.with(t -> counter[0] = t, "Value").map(String::toLowerCase);
    Assert.assertTrue(stringTry.isSuccess());
    Assert.assertEquals(stringTry.get(), "value");
    Assert.assertEquals(counter[0], "Value");
  }

  @Test
  public void withThrowableSupplier() throws Exception {
    Try<String> recoveredTry =
        Try.withThrowable(
                () -> {
                  throw new Throwable("Thrown");
                })
            .recover(throwable -> "Value");

    Assert.assertTrue(recoveredTry.isSuccess());
    Assert.assertEquals(recoveredTry.get(), "Value");
  }

  @Test
  public void withThrowableConsumer() throws Exception {
    Try<String> throwTry =
        Try.withThrowable(
            t -> {
              throw new Throwable("Thrown");
            },
            "Value");

    Assert.assertTrue(throwTry.isFailure());

    throwTry = Try.withThrowable(t -> {}, "Value");

    Assert.assertTrue(throwTry.isSuccess());
    Assert.assertEquals(throwTry.get(), "Value");
  }

  @Test(expected = RuntimeException.class)
  public void runtimeExceptionOnGetFromFailure() {
    Try<String> throwTry =
        Try.withThrowable(
            t -> {
              throw new Throwable("Thrown");
            },
            "Value");
    throwTry.get();
  }
}
