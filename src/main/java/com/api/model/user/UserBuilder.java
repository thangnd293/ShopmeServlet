package com.api.model.user;

import com.api.model.mongo.IMongoBuilder;

import org.bson.types.ObjectId;

public class UserBuilder implements IMongoBuilder<UserBuilder>, IUserBuilder {
  
  private ObjectId id;
  private String fname;
  private String lname;
  private String email;
  private String password;
  private String passwordConfirm;
  private String phoneNumber;
  private String photo;
  @Override
  public UserBuilder addId(ObjectId id) {
    this.id = id;
    return this;
  }
  @Override
  public UserBuilder addEmail(String email) {
    this.email = email;
    return this;
  }
  @Override
  public UserBuilder addFname(String fname) {
    this.fname = fname;
    return this;
  }
  @Override
  public UserBuilder addLname(String lname) {
    this.lname = lname;
    return this;
  }
  @Override
  public UserBuilder addPassword(String password) {
    this.password = password;
    return this;
  }
  @Override
  public UserBuilder addPasswordConfirm(String passwordConfirm) {
    this.passwordConfirm = passwordConfirm;
    return this;
  }
  @Override
  public UserBuilder addPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }
  @Override
  public UserBuilder addPhoto(String photo) {
    this.photo = photo;
    return this;
  }

  @Override
  public UserModel build() {
    return new UserModel(id, fname, lname, email, password, passwordConfirm, phoneNumber, photo);
  }


}
