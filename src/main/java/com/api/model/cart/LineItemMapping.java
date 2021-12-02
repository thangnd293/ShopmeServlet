package com.api.model.cart;

import com.api.model.common.IMapping;
import com.google.gson.JsonObject;

public class LineItemMapping implements IMapping<LineItem>{
    public static final LineItem map(JsonObject object) {
        String variantId = object.get("variantId").getAsString();
        int quantity = object.get("quantity").getAsInt();

        return new LineItem(variantId, quantity, 0, null);
    }
    
}
