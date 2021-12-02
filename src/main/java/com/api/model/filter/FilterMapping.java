package com.api.model.filter;

import com.api.model.common.IMapping;
import com.google.gson.JsonObject;

public class FilterMapping implements IMapping<FilterModel>{
    public static final FilterModel map(JsonObject object) {
        String id = object.get("id").toString();
        String type = object.get("type").toString();
        String name = object.get("name").toString();

        return new FilterModel(id, type, name);
    }
}
