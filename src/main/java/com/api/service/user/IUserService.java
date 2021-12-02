package com.api.service.user;

import java.util.ArrayList;

import com.api.model.user.UserModel;

import org.bson.types.ObjectId;

public interface IUserService {
  UserModel addUser(UserModel user);

  UserModel getUserByID(ObjectId id);

  UserModel getUserByUsername(String userName);

  ArrayList<UserModel> getAllUser();

  void deleteUser(ObjectId id) throws Exception;

}
