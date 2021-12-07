package com.api.service.filter;

import java.util.ArrayList;

import com.api.model.facet.Facet;
import com.api.model.filter.FilterModel;

public interface IFilterService {
    // FilterModel getFilter(String id) throws Exception;/

    ArrayList<FilterModel> getTag() throws Exception;

    ArrayList<FilterModel> getSize() throws Exception;

    ArrayList<FilterModel> getColor() throws Exception;

    ArrayList<FilterModel> getBrand() throws Exception;

    ArrayList<Facet> getFacets(String categoryId) throws Exception;

}
