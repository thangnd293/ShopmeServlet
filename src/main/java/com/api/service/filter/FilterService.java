package com.api.service.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.api.dao.category.CategoryDAO;
import com.api.dao.filter.FilterDAO;
import com.api.model.category.CategoryModel;
import com.api.model.facet.Facet;
import com.api.model.filter.FilterModel;

public class FilterService implements IFilterService {

    @Override
    public FilterModel getFilter(String id) throws Exception {
        FilterDAO filterDAO = new FilterDAO();

        return filterDAO.getOne(id);
    }

    @Override
    public ArrayList<FilterModel> getSubFilters(String id) throws Exception {
        FilterDAO filterDAO = new FilterDAO();
        FilterModel filter = filterDAO.getOne(id);
        if (filter == null) {
            throw new Exception("The corresponding filter could not be found!!");
        }
        return filterDAO.getFilterByType(filter.getName());
    }

    @Override
    public ArrayList<FilterModel> getSubFiltersByType(String type) throws Exception {
        FilterDAO filterDAO = new FilterDAO();
        ArrayList<FilterModel> filters = filterDAO.getFilterByType(type);
        if (filters.size() == 0) {
            throw new Exception("The corresponding filter could not be found!!");
        }
        return filters;
    }

    @Override
    public ArrayList<FilterModel> getTopFilter() {
        FilterDAO filterDAO = new FilterDAO();

        return filterDAO.getFilterByType("/");
    }

    @Override
    public ArrayList<Facet> getFacets(String categoryId) throws Exception {
        CategoryDAO categoryDAO = new CategoryDAO();
        FilterDAO filterDAO = new FilterDAO();

        CategoryModel category = categoryDAO.getOne(categoryId);
        if (category == null) {
            throw new Exception("Invalid category!!");
        }

        ArrayList<FilterModel> filters = filterDAO.getFilters(category.getPath());
        filters.removeIf(filter -> filter.getId() == null);
        Collections.sort(filters, new Comparator<FilterModel>() {
            @Override
            public int compare(FilterModel o1, FilterModel o2) {
                return o1.getType().compareToIgnoreCase(o2.getType());
            }
        });
        ArrayList<Facet> facets = new ArrayList<Facet>();
        int n = filters.size();
        if (n > 0) {
            Facet facet = new Facet(filters.get(0).getType(), new ArrayList<FilterModel>());
            facet.getValues().add(filters.get(0));
            facets.add(facet);
            for (int i = 1; i < n; i++) {
                if (!filters.get(i).getType().equals(filters.get(i - 1).getType())) {
                    facet = new Facet(filters.get(i).getType(), new ArrayList<FilterModel>());
                    facets.add(facet);
                }
                facet.getValues().add(filters.get(i));
            }

        }
        return facets;
    }

}
