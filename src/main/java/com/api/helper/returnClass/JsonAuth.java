package com.api.helper.returnClass;

import com.api.model.user.UserModel;

public class JsonAuth {
    private String status;
    private String token;
    private UserModel data;

    public JsonAuth(String token, UserModel data) {
        this.status = "success";
        this.token = token;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }

}
