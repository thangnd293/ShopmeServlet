package com.api.service.auth;

import java.time.LocalDateTime;
import java.util.Date;

import com.api.dao.user.IUserDAO;
import com.api.dao.user.UserDAO;
import com.api.helper.Check;
import com.api.helper.Common;
import com.api.helper.Encryption;
import com.api.model.user.UserModel;
import com.api.model.user.UserModel.Role;
import com.mongodb.BasicDBObject;

public class AuthService implements IAuthService {
  @Override
  public UserModel login(UserModel userLogin) throws Exception {

    if (userLogin.getEmail() == null || userLogin.getPassword() == null) {
      throw new Exception("Please provide email and password!!");
    }

    IUserDAO userDAO = new UserDAO();
    UserModel user = userDAO.getOne(userLogin.getEmail());
    boolean checkUserIsNull = user == null;

    if (checkUserIsNull) {
      throw new Exception("Incorrect email or password!!");
    }

    String realPass = Encryption.decrypt(user.getPassword(), Encryption.key());

    boolean checkPasswordIsTrue = userLogin.getPassword().equals(realPass);
    if (!checkPasswordIsTrue) {
      throw new Exception("Incorrect email or password!!");
    }
    user.setPassword(null);
    user.setPasswordConfirm(null);
    user.setRole(null);
    return user;
  }

  @Override
  public UserModel signup(UserModel userSignup) throws Exception {
    if (userSignup.getFname() == null || userSignup.getLname() == null || userSignup.getEmail() == null
        || userSignup.getPassword() == null || userSignup.getPasswordConfirm() == null
        || userSignup.getPhoneNumber() == null) {
      throw new Exception("Missing values");
    }

    userSignup.setFname(userSignup.getFname().trim());
    boolean checkIsValidFname = Check.isFname(userSignup.getFname());

    if (!checkIsValidFname) {
      throw new Exception("Invalid first name!!");
    }

    userSignup.setLname(userSignup.getLname().trim());
    boolean checkIsValidLname = Check.isLname(userSignup.getLname());

    if (!checkIsValidLname) {
      throw new Exception("Invalid last name!!");
    }

    boolean checkIsValidPassword = Check.isValidPassword(userSignup.getPassword());

    if (!checkIsValidPassword) {
      throw new Exception("Password must be at least 8 characters!!");
    }

    boolean checkIsPasswordSame = userSignup.getPassword().equals(userSignup.getPasswordConfirm());
    if (!checkIsPasswordSame) {
      throw new Exception("Password must be the same!!");
    }
    userSignup.setPasswordConfirm(null);

    boolean checkIsValidPhoneNumber = Check.isValidPhoneNumber(userSignup.getPhoneNumber());

    if (!checkIsValidPhoneNumber) {
      throw new Exception("Invalid phone number!!");
    }

    userSignup.setRole(Role.USER);

    IUserDAO userDAO = new UserDAO();
    UserModel user = userDAO.getOne(userSignup.getEmail());
    boolean checkIsUserExists = user != null;
    if (checkIsUserExists) {
      if (user.isIsVerify() == false || user.isIsVerify() == null) {
        userDAO.deleteOne(user.getId());
      } else {
        throw new Exception("Email already exists, please use another email");
      }
    }

    String passHash = Encryption.encrypt(userSignup.getPassword(), Encryption.key());
    userSignup.setPassword(passHash);

    String verifyCode = Common.getRandom();
    userSignup.setVerifyCode(verifyCode);
    userSignup.setVerifyExpireAt(new Date());
    return userDAO.addOne(userSignup);
  }

  @Override
  public UserModel verify(String email, String verifyCode) throws Exception {
    UserDAO userDAO = new UserDAO();
    String query = String.format("{ \"email\": \"%s\", \"verifyCode\": \"%s\"}", email, verifyCode);
    BasicDBObject filter = BasicDBObject.parse(query);
    UserModel user = userDAO.getOne(filter);

    if (user == null || new Date().before(user.getVerifyExpireAt())) {
      throw new Exception("Verification failed!!");
    }

    user.setIsVerify(true);
    user.setVerifyExpireAt(null);
    user.setVerifyCode(null);

    user = userDAO.updateOne(user.getId(), user);
    user.set_id(user.getId().toString());
    user.setIsVerify(null);
    user.setPassword(null);
    user.setPasswordConfirm(null);
    user.setPasswordResetExpires(null);
    user.setPasswordResetCode(null);
    user.setRole(null);

    return user;
  }

  @Override
  public String forgotPassword(String email) throws Exception {
    UserDAO userDAO = new UserDAO();

    UserModel user = userDAO.getOne(email);
    boolean checkUserIsNull = user == null;

    if (checkUserIsNull) {
      throw new Exception("There is no user with email address");
    }

    String resetCode = Common.getRandom();
    String resetCodeHash = Encryption.encrypt(resetCode, Encryption.key());

    user.setPasswordResetCode(resetCodeHash);
    user.setPasswordResetExpires(Common.toDate(LocalDateTime.now().plusMinutes(5)));
    userDAO.updateOne(user.getId(), user);
    return resetCode;
  }

  @Override
  public UserModel resetPassword(String email, String resetCode, String password, String passwordConfirm)
      throws Exception {
    if (email == null || resetCode == null || password == null || passwordConfirm == null) {
      throw new Exception("Missing values!!");
    }

    UserDAO userDAO = new UserDAO();
    
    String query = String.format("{ \"email\": \"%s\"}", email);
    BasicDBObject filter = BasicDBObject.parse(query);
    UserModel user = userDAO.getOne(filter);

    if (user == null || new Date().after(user.getPasswordResetExpires())) {
      throw new Exception("Verify failed. Please try again");
    }

    String realResetCode = Encryption.decrypt(user.getPasswordResetCode(), Encryption.key());

    if(!realResetCode.equals(resetCode)) {
      throw new Exception("Verify failed. Please try again");
    }

    boolean checkIsValidPassword = Check.isValidPassword(password);

    if (!checkIsValidPassword) {
      throw new Exception("Password must be at least 8 characters!!");
    }

    boolean checkIsPasswordSame = password.equals(passwordConfirm);
    if (!checkIsPasswordSame) {
      throw new Exception("Password must be the same!!");
    }
    String passwordHash = Encryption.encrypt(password, Encryption.key());

    user.setPassword(passwordHash);
    user.setPasswordConfirm(null);
    user.setChangePasswordAt(new Date());
    user.setPasswordResetCode(null);
    user.setPasswordResetExpires(null);
    user = userDAO.updateOne(user.getId(), user);

    user.set_id(user.getId().toString());
    user.setIsVerify(null);
    user.setPassword(null);
    user.setRole(null);

    return user;
  }
}