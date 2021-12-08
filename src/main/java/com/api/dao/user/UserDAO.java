package com.api.dao.user;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.api.config.database.DatabaseConnect;
import com.api.model.user.UserModel;

import static com.mongodb.client.model.Filters.eq;

public class UserDAO implements IUserDAO {
  private MongoCollection<UserModel> userCollection = new DatabaseConnect().getCollection("user",
      UserModel.class);

  @Override
  public UserModel addOne(UserModel user) {
    userCollection.insertOne(user);
    return user;
  }

  @Override
  public UserModel getOne(ObjectId id) {
    UserModel user = userCollection.find(eq(id)).first();
    return user;
  }

  @Override
  public UserModel getOne(String email) {
    UserModel user = userCollection.find(eq("email", email)).first();
    return user;
  }

  @Override
  public UserModel getOne(Bson filter) {
    UserModel user = userCollection.find(filter).first();
    return user;
  }

  @Override
  public ArrayList<UserModel> getAll() {
    ArrayList<UserModel> userList = new ArrayList<UserModel>();

    Consumer<UserModel> addUser = new Consumer<UserModel>() {
      @Override
      public void accept(final UserModel user) {
        userList.add(user);
      }
    };

    userCollection.find().sort(new BasicDBObject("createAt", -1)).forEach(addUser);
    return userList;
  }

  @Override
  public ArrayList<UserModel> getUsers(int limit) {
    ArrayList<UserModel> userList = new ArrayList<UserModel>();

    Consumer<UserModel> addUser = new Consumer<UserModel>() {
      @Override
      public void accept(final UserModel user) {
        userList.add(user);
      }
    };

    userCollection.find().sort(new BasicDBObject("createAt", -1)).limit(limit).forEach(addUser);
    return userList;
  }

  @Override
  public Boolean deleteOne(ObjectId id) {
    return userCollection.deleteOne(eq("_id", id)).getDeletedCount() != 0;
  }

  @Override
  public UserModel updateOne(ObjectId id, UserModel newUser) {
    return userCollection.findOneAndReplace(eq("_id", id), newUser, new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER));
  }
}
