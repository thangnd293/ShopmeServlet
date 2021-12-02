package com.api.model.user;

import com.api.model.common.IMapping;
import com.google.gson.JsonObject;

public class UserMapping implements IMapping<UserModel> {
  public static final UserModel map(JsonObject object) {
    String fname = object.get("fname") != null ? object.get("fname").getAsString() : null;
    String lname = object.get("lname") != null ? object.get("lname").getAsString() : null;
    String email = object.get("email") != null ? object.get("email").getAsString() : null;
    String password = object.get("password") != null ? object.get("password").getAsString() : null;
    String passwordConfirm = object.get("passwordConfirm") != null ? object.get("passwordConfirm").getAsString() : null;
    String phoneNumber = object.get("phoneNumber") != null ? object.get("phoneNumber").getAsString() : null;
    String photo = object.get("photo") != null ? object.get("photo").getAsString() : null;

    return new UserBuilder()
              .addFname(fname)
              .addLname(lname)
              .addEmail(email)
              .addPassword(password)
              .addPasswordConfirm(passwordConfirm)
              .addPhoneNumber(phoneNumber)
              .addPhoto(photo)
              .build();
  }
}
