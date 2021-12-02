package com.api.service.filter;

import java.util.ArrayList;

import com.api.model.facet.Facet;
import com.api.model.filter.FilterModel;

public interface IFilterService {
    FilterModel getFilter(String id) throws Exception;

    ArrayList<FilterModel> getTopFilter();

    ArrayList<FilterModel> getSubFilters(String id) throws Exception;

    ArrayList<FilterModel> getSubFiltersByType(String type) throws Exception;

    ArrayList<Facet> getFacets(String categoryId) throws Exception;

}
