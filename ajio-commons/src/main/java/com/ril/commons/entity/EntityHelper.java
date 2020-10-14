package com.ril.commons.entity;

public class EntityHelper {
  public static <Entity> boolean isSoftDeletable(Entity entity) {
    if (entity instanceof CoreEntity) return true;
    return false;
  }
}