package com.api.model.mongo;

import org.bson.types.ObjectId;

public interface IMongoBuilder<T> {
  T addId(ObjectId id);
}
