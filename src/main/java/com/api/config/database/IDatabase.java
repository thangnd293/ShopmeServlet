package com.api.config.database;

public interface IDatabase {
  static <Model> Model getCollection(String name, Class<Model> type) {
    return null;
  }
}
