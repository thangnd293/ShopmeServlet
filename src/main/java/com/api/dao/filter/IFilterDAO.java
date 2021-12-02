package com.api.dao.filter;

import java.util.ArrayList;

import com.api.model.filter.FilterModel;

public interface IFilterDAO {
    FilterModel getOne(String id);
    
    ArrayList<FilterModel> getFilterByType(String type);

    ArrayList<FilterModel> getFilters(String categoryPath);

}
