package com.api.model.mongo;

import org.bson.types.ObjectId;

public abstract class MongoBuilder<T> implements IMongoBuilder<T> {
  private ObjectId id;

  @Override
  public T addId(ObjectId id) {
    this.id = id;
    return (T) this;
  }
}
