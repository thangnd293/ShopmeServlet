package com.api.service.auth;

import com.api.model.user.UserModel;

public interface IAuthService {
  UserModel login(UserModel userLogin) throws Exception;

  UserModel signup(UserModel userSignup) throws Exception;

  UserModel verify(String email, String verifyCode) throws Exception;

  String forgotPassword(String email) throws Exception;

  UserModel resetPassword(String email, String resetCode, String password, String passwordConfirm) throws Exception;
}
