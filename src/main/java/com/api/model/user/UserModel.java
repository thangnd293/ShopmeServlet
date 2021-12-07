package com.api.model.user;

import java.util.Date;

import org.bson.types.ObjectId;

public class UserModel {
  public enum Role {
    ADMIN,
    USER
  }

  private ObjectId id;
  private String _id;
  private String fname;
  private String lname;
  private String email;
  private String password;
  private String passwordConfirm;
  private String phoneNumber;
  private String photo;
  private Role role;
  private Boolean isVerify;
  private String verifyCode;
  private Date verifyExpireAt;
  private Date changePasswordAt;
  private String passwordResetCode;
  private Date passwordResetExpires;
  private Date createAt;

  public UserModel(ObjectId id, String fname, String lname, String email, String password, String passwordConfirm,
      String phoneNumber, String photo) {
    this.id = id;
    this.fname = fname;
    this.lname = lname;
    this.email = email;
    this.password = password;
    this.passwordConfirm = passwordConfirm;
    this.phoneNumber = phoneNumber;
    this.photo = photo;
  }

  public UserModel() {

  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public String getFname() {
    return fname;
  }

  public void setFname(String fname) {
    this.fname = fname;
  }

  public String getLname() {
    return lname;
  }

  public void setLname(String lname) {
    this.lname = lname;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPasswordConfirm() {
    return passwordConfirm;
  }

  public void setPasswordConfirm(String passwordConfirm) {
    this.passwordConfirm = passwordConfirm;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public Boolean isIsVerify() {
    return isVerify;
  }

  public void setIsVerify(Boolean isVerify) {
    this.isVerify = isVerify;
  }

  public Date getChangePasswordAt() {
    return changePasswordAt;
  }

  public void setChangePasswordAt(Date changePasswordAt) {
    this.changePasswordAt = changePasswordAt;
  }

  public String getPasswordResetCode() {
    return passwordResetCode;
  }

  public void setPasswordResetCode(String passwordResetCode) {
    this.passwordResetCode = passwordResetCode;
  }

  public Date getPasswordResetExpires() {
    return passwordResetExpires;
  }

  public void setPasswordResetExpires(Date passwordResetExpires) {
    this.passwordResetExpires = passwordResetExpires;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getVerifyCode() {
    return verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }

  public Date getVerifyExpireAt() {
    return verifyExpireAt;
  }

  public void setVerifyExpireAt(Date verifyExpireAt) {
    this.verifyExpireAt = verifyExpireAt;
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }

}
