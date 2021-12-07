package com.api.model.bill;

import com.api.model.common.IMapping;
import com.google.gson.JsonObject;

public class ShippingAddressMapping implements IMapping<ShippingAddressModel> {
    public static final ShippingAddressModel map(JsonObject object) {
        String city = object.get("city") != null ? object.get("city").getAsString() : null;
        String countryCode = object.get("country_code") != null ? object.get("country_code").getAsString() : null;
        String postalCode = object.get("postal_code") != null ? object.get("postal_code").getAsString() : null;
        String state = object.get("state") != null ? object.get("state").getAsString() : null;
        
        return new ShippingAddressModel(null, city, state, postalCode, countryCode);
    }
}
