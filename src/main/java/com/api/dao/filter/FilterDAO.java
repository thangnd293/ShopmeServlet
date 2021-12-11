package com.api.dao.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

import com.api.config.database.DatabaseConnect;
import com.api.model.filter.FilterModel;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class FilterDAO implements IFilterDAO {
    private MongoCollection<FilterModel> filterCollection = new DatabaseConnect().getCollection("filter",
            FilterModel.class);

    @Override
    public FilterModel getOne(String id) {
        FilterModel filter = filterCollection.find(eq("_id", id)).first();
        return filter;
    }

    @Override
    public ArrayList<FilterModel> getAll() {
        ArrayList<FilterModel> filterList = new ArrayList<FilterModel>();
        MongoCursor<FilterModel> cursor = filterCollection.find().iterator();

        while (cursor.hasNext()) {
            filterList.add(cursor.next());
        }

        return filterList;
    }

    @Override
    public ArrayList<FilterModel> getAll(Bson query) {
        ArrayList<FilterModel> filterList = new ArrayList<FilterModel>();

        Consumer<Document> addFilter = new Consumer<Document>() {
            @Override
            public void accept(final Document doc) {
                Document d = (Document) doc.get("_id");
                String id = (String) d.get("_id");
                String type = (String) d.get("type");
                String name = (String) d.get("name");
                if(!name.equals("Default")) {
                    filterList.add(new FilterModel(id, type, name, null));
                }
            }
        };

        MongoCollection<Document> DocCollection = new DatabaseConnect().getCollection("product", Document.class);
        DocCollection.aggregate(Arrays.asList(Aggregates.match(query),
                Aggregates.unwind("$facets"), Aggregates.group("$facets"))).forEach(addFilter);
 
        return filterList;
    }

    @Override
    public ArrayList<FilterModel> getAll(BasicDBObject query) {
        ArrayList<FilterModel> filters = new ArrayList<FilterModel>();

        MongoCursor<FilterModel> cursor = filterCollection.find(query).iterator();

        while (cursor.hasNext()) {
            filters.add(cursor.next());
        }

        return filters;
    }

}
