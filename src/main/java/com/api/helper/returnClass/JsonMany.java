package com.api.helper.returnClass;

import java.util.ArrayList;

import com.google.gson.Gson;

public class JsonMany <Model> {
    private String status;
    private int result;
    private ArrayList<Model> data;

    public JsonMany() {
        this.status = "";
        this.result = 0;
        this.data = new ArrayList<Model>();
    }

    public JsonMany(int result, ArrayList<Model> data) {
        this.status = "success";
        this.result = result;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public ArrayList<Model> getData() {
        return data;
    }

    public void setData(ArrayList<Model> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this).replace("\\\"", "");
    }
}
