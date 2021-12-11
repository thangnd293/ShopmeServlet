package com.api.service.user;

import java.util.ArrayList;
import java.util.Date;

import com.api.dao.user.UserDAO;
import com.api.helper.Check;
import com.api.helper.Encryption;
import com.api.model.user.UserModel;
import com.api.model.user.UserModel.Role;

import org.bson.types.ObjectId;

public class UserService implements IUserService {
  @Override
  public ArrayList<UserModel> getAllUser() {
    UserDAO userDAO = new UserDAO();
    ArrayList<UserModel> users = userDAO.getAll();
    for (UserModel user : users) {
      user.set_id(user.getId().toString());
    }
    return users;
  }

  @Override
  public UserModel getUserByID(ObjectId id) {
    UserDAO userDAO = new UserDAO();
    UserModel user = userDAO.getOne(id);
    user.set_id(user.getId().toString());
    return user;
  }

  @Override
  public UserModel getUserByEmail(String email) {
    UserDAO userDAO = new UserDAO();
    UserModel user = userDAO.getOne(email);
    user.set_id(user.getId().toString());
    return user;
  }

  @Override
  public void deleteUser(ObjectId id) throws Exception {
    UserDAO userDAO = new UserDAO();
    userDAO.deleteOne(id);
  }

  @Override
  public UserModel updateRole(String id, String newRoleStr) throws Exception {
    UserDAO userDAO = new UserDAO();
    UserModel user = userDAO.getOne(new ObjectId(id)); 

    if(user == null) {
      throw new Exception("User does not exist");
    }

    Role newRole = newRoleStr.equals("ADMIN") ? Role.ADMIN : Role.USER;

    user.setRole(newRole);

    UserModel newUser = userDAO.updateOne(new ObjectId(id), user);

    preparePrintUser(newUser);
    return newUser;
  }

  @Override
  public UserModel updatePhoto(ObjectId id, UserModel user) throws Exception {
    UserDAO userDAO = new UserDAO();
    user = userDAO.updateOne(id, user);

    preparePrintUser(user);
    return user;
  }

  @Override
  public UserModel updatePassword(UserModel user, String passwordCurrent, String password, String passwordConfirm) throws Exception {

    if(password == null || passwordConfirm == null || passwordCurrent == null) {
      throw new Exception("Missing value, please try again");
    }

    String realPass = Encryption.decrypt(user.getPassword(), Encryption.key());

    boolean checkCorrectPassword = realPass.equals(passwordCurrent);
    if(!checkCorrectPassword) {
      throw new Exception("Incorrect password");
    }

    boolean checkSamePassword = passwordConfirm.equals(password);
    if(!checkSamePassword) {
      throw new Exception("The password is not the same");
    }

    if(!Check.isValidPassword(password)) {
      throw new Exception("Password must be at least 8 characters!!");
    }

    String passHash = Encryption.encrypt(password, Encryption.key());
    user.setPassword(passHash);

    Date today = new Date();
    user.setChangePasswordAt(today);
    UserDAO userDAO = new UserDAO();
    user = userDAO.updateOne(user.getId(), user);
    preparePrintUser(user);
    return user;
  }

  public static void preparePrintUser(UserModel user) {
    user.set_id(user.getId().toString());
    user.setIsVerify(null);
    user.setPassword(null);
    user.setPasswordConfirm(null);
    user.setPasswordResetExpires(null);
    user.setPasswordResetCode(null);
    user.setId(null);
  }
}
