package com.api.helper.returnClass;

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

}
