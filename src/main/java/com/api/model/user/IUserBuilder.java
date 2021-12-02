package com.api.model.user;


import org.bson.types.ObjectId;

public interface IUserBuilder {

  UserBuilder addId(ObjectId id);

  UserBuilder addFname(String fname);

  UserBuilder addLname(String lname);

  UserBuilder addEmail(String email);

  UserBuilder addPassword(String password);

  UserBuilder addPasswordConfirm(String confirm);

  UserBuilder addPhoneNumber(String phoneNumber);

  UserBuilder addPhoto(String photo);

  UserModel build();
}
