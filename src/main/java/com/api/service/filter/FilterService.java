package com.api.service.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.api.dao.category.CategoryDAO;
import com.api.dao.filter.FilterDAO;
import com.api.model.category.CategoryModel;
import com.api.model.facet.Facet;
import com.api.model.filter.FilterModel;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;

import org.bson.conversions.Bson;

public class FilterService implements IFilterService {

    // @Override
    // public FilterModel getFilter(String id) throws Exception {
    //     FilterDAO filterDAO = new FilterDAO();

    //     return filterDAO.getOne(id);
    // }

    @Override
    public ArrayList<FilterModel> getTag() throws Exception {
        FilterDAO filterDAO = new FilterDAO();
        ArrayList<FilterModel> filters = filterDAO.getAll();
        ArrayList<FilterModel> tags = new ArrayList<FilterModel>();
        for (FilterModel filter : filters) {
            if(filter.getType().equals("/") && (filter.getName().equals("Categories") || filter.getName().equals("Genders") || filter.getName().equals("Products"))) {
                tags.add(filter);
            }
        }

        for (FilterModel tag : tags) {
            ArrayList<FilterModel> children = new ArrayList<FilterModel>();
            tag.setChildren(children);

            for (FilterModel filter : filters) {
                if(filter.getType().equals(tag.getName())) {
                    children.add(filter);
                }
            }
        }
        return tags;
    }

    @Override
    public ArrayList<FilterModel> getSize() throws Exception {
        FilterDAO filterDAO = new FilterDAO();
        BasicDBObject query = BasicDBObject.parse("{ '$or': [ {'name': 'Clothing sizes'}, {'name': 'Shoe sizes'}, {'name': 'Default'}, { 'type': 'Clothing sizes' }, { 'type': 'Shoe sizes' }]}");
        ArrayList<FilterModel> filters = filterDAO.getAll(query);
        ArrayList<FilterModel> sizes = new ArrayList<FilterModel>();

        for (FilterModel filter : filters) {
            if(filter.getName().equals("Clothing sizes") || filter.getName().equals("Shoe sizes") || filter.getName().equals("Default")) {
                sizes.add(filter);
            }
        }

        for (FilterModel size : sizes) {
            ArrayList<FilterModel> children = new ArrayList<FilterModel>();
            size.setChildren(children);

            for (FilterModel filter : filters) {
                if(filter.getType().equals(size.getName())) {
                    children.add(filter);
                }    
            }
        }
        return sizes;
    }

    @Override
    public ArrayList<FilterModel> getColor() throws Exception {
        FilterDAO filterDAO = new FilterDAO();
        BasicDBObject query = BasicDBObject.parse("{ '$or': [ {'name': 'Colors'}, { 'type': 'Colors' } ] }");
        ArrayList<FilterModel> filters = filterDAO.getAll(query);
        ArrayList<FilterModel> colors = new ArrayList<FilterModel>();

        for (FilterModel filter : filters) {
            if(filter.getName().equals("Colors")) {
                colors.add(filter);
                break;
            }
        }

        for (FilterModel color : colors) {
            ArrayList<FilterModel> children = new ArrayList<FilterModel>();
            color.setChildren(children);

            for (FilterModel filter : filters) {
                if(filter.getType().equals(color.getName())) {
                    children.add(filter);
                }    
            }
        }
        return colors;
    }

    @Override
    public ArrayList<FilterModel> getBrand() throws Exception {
        FilterDAO filterDAO = new FilterDAO();
        BasicDBObject query = BasicDBObject.parse("{ '$or': [ {'name': 'Brands'}, { 'type': 'Brands' } ] }");
        ArrayList<FilterModel> filters = filterDAO.getAll(query);
        ArrayList<FilterModel> brands = new ArrayList<FilterModel>();

        for (FilterModel filter : filters) {
            if(filter.getName().equals("Brands")) {
                brands.add(filter);
                break;
            }
        }

        for (FilterModel brand : brands) {
            ArrayList<FilterModel> children = new ArrayList<FilterModel>();
            brand.setChildren(children);

            for (FilterModel filter : filters) {
                if(filter.getType().equals(brand.getName())) {
                    children.add(filter);
                }    
            }
        }
        return brands;
    }

    @Override
    public ArrayList<Facet> getFacets(String categoryId) throws Exception {
        CategoryDAO categoryDAO = new CategoryDAO();
        FilterDAO filterDAO = new FilterDAO();

        CategoryModel category = categoryDAO.getOne(categoryId);
        if (category == null) {
            throw new Exception("Invalid category!!");
        }

        Bson query;

        if (categoryId.startsWith("88")) {
            query = Filters.eq("brand", category.getName());
            if (
              categoryId.equals("8836") ||
              categoryId.equals("8837") ||
              categoryId.equals("8893")
            ) {
                query = BasicDBObject.parse("{}");
            }
          } else {
              query = Filters.regex("categoryPath", category.getPath());
          }

        ArrayList<FilterModel> filters = filterDAO.getAll(query);
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
