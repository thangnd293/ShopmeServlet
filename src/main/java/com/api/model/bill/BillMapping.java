package com.api.model.bill;

import java.util.ArrayList;
import java.util.Date;

import com.api.model.common.IMapping;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class BillMapping implements IMapping<BillModel> {
    public static final BillModel map(JsonObject object) {
        JsonObject jsonShippingAddress = object.get("shipping_address").getAsJsonObject();

        ArrayList<ItemModel> items = null;
        JsonArray jsonItems = object.get("data").getAsJsonArray();
        if (jsonItems != null) {
            items = new ArrayList<ItemModel>();

            for (int i = 0; i < jsonItems.size(); i++) {
                JsonObject jsonItem = jsonItems.get(i).getAsJsonObject();
                ItemModel item = ItemMapping.map(jsonItem);
                items.add(item);
            }
        }

        int quantity = object.get("quantityTotal") != null ? object.get("quantityTotal").getAsInt() : 0;

        double amount = object.get("total") != null ? object.get("total").getAsDouble() : 0;

        Date createAt = new Date();

        ShippingAddressModel shippingAdress = ShippingAddressMapping.map(jsonShippingAddress);

        return new BillModel(null, shippingAdress, items, quantity, amount, createAt);
    }

}
