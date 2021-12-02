package com.api.model.common;

import com.google.gson.JsonObject;

public interface IMapping<T> {
  static <T> T map(JsonObject object) {
    return null;
  }
}
