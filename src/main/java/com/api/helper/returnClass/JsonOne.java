package com.api.helper.returnClass;

import com.google.gson.Gson;

public class JsonOne <Model> {
    private String status;
    private Model data;

    public JsonOne(Model data) {
        this.status = "success";
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Model getData() {
        return data;
    }

    public void setData(Model data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this).replace("\\\"", "");
    }
}
