package com.api.model.bill;

import com.api.model.common.IMapping;
import com.google.gson.JsonObject;

public class ItemMapping implements IMapping<ItemModel> {
    public static final ItemModel map(JsonObject object) {
        String sku = object.get("sku") != null ? object.get("sku").getAsString() : null;
        String name = object.get("name") != null ? object.get("name").getAsString() : null;
        int quantity = object.get("quantity") != null ? object.get("quantity").getAsInt() : 0;
        JsonObject unitAmount = object.get("unit_amount") != null ? object.get("unit_amount").getAsJsonObject() : null;
        double price = 0;
        if(unitAmount != null) {
            price = unitAmount.get("value") != null ? unitAmount.get("value").getAsDouble() : 0;
        }
        // double price = object.get("unit_amount.value") != null ? object.get("unit_amount.value").getAsDouble() : 0;
        double total = price * quantity;

        return new ItemModel(null, name, sku, price, quantity, total);
    }
}
