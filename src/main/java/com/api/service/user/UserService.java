package com.api.service.user;

import java.util.ArrayList;

import com.api.dao.user.UserDAO;
import com.api.model.user.UserModel;

import org.bson.types.ObjectId;

public class UserService implements IUserService {
  @Override
  public ArrayList<UserModel> getAllUser() {
    UserDAO userDAO = new UserDAO();
    return userDAO.getAll();
  }

  @Override
  public UserModel getUserByID(ObjectId id) {
    UserDAO userDAO = new UserDAO();
    return userDAO.getOne(id);
  }

  @Override
  public UserModel getUserByUsername(String email) {
    UserDAO userDAO = new UserDAO();
    return userDAO.getOne(email);
  }

  @Override
  public void deleteUser(ObjectId id) throws Exception {
    UserDAO userDAO = new UserDAO();
    userDAO.deleteOne(id);
  }
}
