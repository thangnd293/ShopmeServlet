package com.api.model.mongo;

import com.api.model.mongo.MongoModel;

import org.bson.types.ObjectId;

public abstract class MongoModel {
  private ObjectId id;

  public MongoModel(ObjectId id) {
    this.id = id;
  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }
}