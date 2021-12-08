package com.api.dao.user;

import java.util.ArrayList;

import com.api.model.user.UserModel;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public interface IUserDAO {
  UserModel addOne(UserModel user);

  UserModel getOne(ObjectId id);

  UserModel getOne(String email);

  UserModel getOne(Bson filter);

  ArrayList<UserModel> getAll();

  ArrayList<UserModel> getUsers(int limit);

  Boolean deleteOne(ObjectId id);

  UserModel updateOne(ObjectId id, UserModel newUser);
}
