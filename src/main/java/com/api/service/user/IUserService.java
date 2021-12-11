package com.api.service.user;

import java.util.ArrayList;

import com.api.model.user.UserModel;

import org.bson.types.ObjectId;

public interface IUserService {
  UserModel getUserByID(ObjectId id);

  UserModel getUserByEmail(String email);

  ArrayList<UserModel> getAllUser();

  void deleteUser(ObjectId id) throws Exception;

  UserModel updateRole(String id, String newRoleStr) throws Exception;

  UserModel updatePhoto(ObjectId id, UserModel user) throws Exception;

  UserModel updatePassword(UserModel user, String passwordCurrent, String password, String passwordConfirm) throws Exception;
}
