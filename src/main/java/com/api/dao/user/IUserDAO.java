package com.api.dao.user;

import java.util.ArrayList;

import com.api.model.user.UserModel;
import com.mongodb.BasicDBObject;

import org.bson.types.ObjectId;

public interface IUserDAO {
  UserModel addOne(UserModel user);

  UserModel getOne(ObjectId id);

  UserModel getOne(String email);

  UserModel getOne(BasicDBObject filter);

  ArrayList<UserModel> getAllUser();

  Boolean deleteOne(ObjectId id);

  UserModel updateOne(ObjectId id, UserModel newUser);
}
