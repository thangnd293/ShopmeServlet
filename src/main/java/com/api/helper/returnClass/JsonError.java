package com.api.helper.returnClass;

import com.google.gson.Gson;

public class JsonError {
    private String status;
    private String message;

    public JsonError() {
        this.message = "";
        this.status = "";
    }

    public JsonError(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this).replace("\\\"", "");
    }
}
