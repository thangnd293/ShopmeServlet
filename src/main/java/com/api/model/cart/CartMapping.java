package com.api.model.cart;

import java.util.ArrayList;

import com.api.model.common.IMapping;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CartMapping implements IMapping<CartModel> {
    public static final CartModel map(JsonObject object) {
        ArrayList<LineItem> items = null;
        JsonArray jsonItems = object.getAsJsonArray("items");
        if(jsonItems != null) {
            items = new ArrayList<LineItem>();
            for (int i = 0; i < jsonItems.size(); i++) {
                LineItem item = LineItemMapping.map(jsonItems.get(i).getAsJsonObject());
                items.add(item);
              }
        }
        return new CartModel(null, items, 0, 0);
    }
}
