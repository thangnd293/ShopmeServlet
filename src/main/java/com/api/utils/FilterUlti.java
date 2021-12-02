package com.api.utils;

import com.api.dao.user.UserDAO;
import com.api.model.user.UserModel;
import com.api.model.user.UserModel.Role;
import org.bson.types.ObjectId;

public class FilterUlti {
    public static UserModel checkAdmin(String id) throws Exception {
        UserDAO userDAO = new UserDAO();
        UserModel user = userDAO.getOne(new ObjectId(id));

        if (user == null) {
            throw new Exception("User does not exist!");
        }
        if (user.getRole() != Role.ADMIN) {
            throw new Exception("You do not have permission!!");
        }
        return user;
    }
}
