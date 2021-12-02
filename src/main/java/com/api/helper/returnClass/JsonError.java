package com.api.helper.returnClass;

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

}
