package com.api.dao.filter;

import java.util.ArrayList;

import com.api.model.filter.FilterModel;
import com.mongodb.BasicDBObject;

import org.bson.conversions.Bson;

public interface IFilterDAO {
    FilterModel getOne(String id);
    
    ArrayList<FilterModel> getAll();

    ArrayList<FilterModel> getAll(Bson query);

    ArrayList<FilterModel> getAll(BasicDBObject query);
}
