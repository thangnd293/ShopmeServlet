package com.api.helper;

import javax.servlet.http.HttpServletRequest;

import com.api.dao.user.UserDAO;
import com.api.model.user.UserModel;
import com.api.model.user.UserModel.Role;

import org.bson.types.ObjectId;
import org.json.JSONObject;

import io.jsonwebtoken.Claims;

import java.util.Date;
public final class Check {
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFname(String str) {
        return str.length() >= 1 && str.matches("[a-zA-Z]+");
    }

    public static boolean isLname(String str) {
        return str.length() >= 1 && str.matches("[a-zA-Z ]+");
    }

    public static boolean isValidPassword(String str) {
        return str.length() >= 8;
    }

    public static boolean isValidPhoneNumber(String str) {
        return str.length() >= 8 && str.length() <= 12 && isNumeric(str);
    }

    // public static boolean isValidEmailAddress(String email) {
    // boolean result = true;
    // try {
    // InternetAddress emailAddr = new InternetAddress(email);
    // emailAddr.validate();
    // } catch (AddressException ex) {
    // result = false;
    // }
    // return result;
    // }

    public static void checkLogged(HttpServletRequest req) throws Exception {
        String jwt = req.getHeader("Authorization");
        boolean checkJwtIsNull = jwt == null;
        if (checkJwtIsNull) {
            throw new Exception("You are not logged in");
        }

        Claims claims = TokenJwt.checkJwt(jwt);

        JSONObject userJson = handleData(claims);
        String id = userJson.get("jti") != null ? userJson.get("jti").toString() : null;

        boolean checkIdInvalid = id == null;
        if (checkIdInvalid) {
            throw new Exception("You are not logged in");
        }
        // Kiểm tra người dùng có tồn tại không
        UserDAO userDAO = new UserDAO();
        UserModel user = userDAO.getOne(new ObjectId(id));
        // Kiểm tra người dùng có đổi mật khẩu sau khi token được cấp không
        boolean checkUserNotExist = user == null;
        // today.after(user.getChangePasswordAt()) || user.getRole() != role;

        if(checkUserNotExist) {
            throw new Exception("The user belonging to this token does no longer exist");
        }

        Date today = new Date();
        boolean checkUserChangePassword = user.getChangePasswordAt() == null || today.after(user.getChangePasswordAt());

        if(!checkUserChangePassword) {
            throw new Exception("User recently changed password. Please log in again");
        }

        // Lưu user vào req tiện cho việc sử dụng sau này
        req.setAttribute("user", user);

    }

    public static boolean isUser(UserModel user) {
        return user.getRole() == Role.USER;
    }

    public static boolean isAdmin(UserModel user) {
        return user.getRole() == Role.ADMIN;
    }

    private static JSONObject handleData(Claims claims) {
        JSONObject userJson = new JSONObject();
        claims.forEach((key, value) -> {
            String val = value.toString().replace("\"", "");
            userJson.put(key, val);
        });

        return userJson;
    }
}
