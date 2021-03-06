package com.api.service.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import com.api.dao.category.CategoryDAO;
import com.api.dao.filter.FilterDAO;
import com.api.dao.product.ProductDAO;
import com.api.model.category.CategoryModel;
import com.api.model.filter.FilterModel;
import com.api.model.product.ProductModel;
import com.api.model.variant.VariantModel;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;

import org.bson.conversions.Bson;

public class ProductService implements IProductService {

    @Override
    public ProductModel getProduct(String id) throws Exception {
        ProductDAO productDao = new ProductDAO();
        ProductModel product = productDao.getOne(id);
        if (product == null) {
            throw new Exception("Product does not exist!!");
        }

        BasicDBObject filter = BasicDBObject.parse(
                String.format("{\"$and\": [ { \"brandId\": \"%s\" }, { \"_id\": { \"$not\": { \"$eq\": \"%s\" } } } ]}",
                        product.getBrandId(), id));
        ArrayList<ProductModel> relateProducts = productDao.getAll(product.getCategoryPath(), filter, null);
        // Giam tai du lieu khong can thiet
        for (ProductModel p : relateProducts) {
            preparePrint(p);
        }
        if (relateProducts.size() > 0) {
            product.setRelateProducts(relateProducts);
        }

        product.setFacets(null);
        return product;
    }

    @Override
    public ArrayList<ProductModel> getAllProduct(BasicDBObject query) {
        ProductDAO productDao = new ProductDAO();
        ArrayList<ProductModel> productList = new ArrayList<ProductModel>();

        productList = productDao.getAll(query, null, null);

        return productList;
    }

    @Override
    public ArrayList<ProductModel> getAllProduct(String categoryId, String[] filter, String sortParam)
            throws Exception {
        ProductDAO productDao = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        String categoryPath = "/";
        Bson query = null;
        if (categoryId == null) {
            query = Filters.regex("categoryPath", categoryPath);
        } else {
            CategoryModel category = categoryDAO.getOne(categoryId);
            categoryPath = categoryId == null ? "/" : category.getPath();

            if (categoryId.startsWith("88")) {
                query = Filters.eq("brand", category.getName());
                if (categoryId.equals("8836") ||
                        categoryId.equals("8837") ||
                        categoryId.equals("8893")) {
                    query = BasicDBObject.parse("{}");
                }
            } else {
                query = Filters.regex("categoryPath", category.getPath());
            }
        }

        ArrayList<ProductModel> productList = new ArrayList<ProductModel>();

        productList = productDao.getAll(query, filterProduct(filter), sortProduct(sortParam));

        return productList;
    }

    @Override
    public ArrayList<ProductModel> getProductFeatures() {
        ProductDAO productDao = new ProductDAO();

        ArrayList<ProductModel> productList = new ArrayList<ProductModel>();
        BasicDBObject filter = new BasicDBObject("isFeatured", true);
        BasicDBObject sort = new BasicDBObject("createAt", -1);
        productList = productDao.getAll("/", filter, sort);

        for (ProductModel product : productList) {
            preparePrint(product);
        }

        return productList;
    }

    @Override
    public ProductModel addProduct(ProductModel product) throws Exception {

        if (product.getCategories() == null || product.getBrandId() == null || product.getImageCovers() == null
                || product.getImages() == null || product.getColor() == null || product.getVariants() == null) {
            throw new Exception("Missing values!!");
        }

        ProductDAO productDao = new ProductDAO();

        Random rand = new Random();
        String id = Integer.toString(rand.nextInt(100000));

        while (productDao.getOne(id) != null) {
            id = Integer.toString(rand.nextInt(100000));
        }
        product.setId(id);
        // Ti???n x??? l?? d??? li???u
        handleProductData(product);
        product.setIsFeatured(false);
        productDao.addOne(product);
        product.setFacets(null);
        return product;
    }

    @Override
    public ProductModel updateProduct(String id, ProductModel newProduct) throws Exception {
        ProductDAO productDAO = new ProductDAO();

        ProductModel product = productDAO.getOne(id);

        if (product == null) {
            throw new Exception("Product does not exist!!");
        }

        this.updateProduct(product, newProduct);

        // Ti???n x??? l?? d??? li???u
        handleProductData(product);

        productDAO.updateOne(id, product);
        product.setFacets(null);
        return product;
    }

    @Override
    public void deleteProduct(String id) throws Exception {
        ProductDAO productDAO = new ProductDAO();
        Boolean isSuccess = productDAO.deleteOne(id);
        if (isSuccess == false) {
            throw new Exception("Deletion failed!!");
        }
    }

    @Override
    public ProductModel getVariant(String id) throws Exception {
        ProductDAO productDAO = new ProductDAO();
        try {
            ProductModel product = productDAO.getVariant(id);
            if (product == null) {
                throw new Exception();
            }
            return product;

        } catch (Exception e) {
            throw new Exception("Variant does not exist!!");
        }
    }

    private void handleProductData(ProductModel product) throws Exception {
        FilterDAO filterDao = new FilterDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        ProductDAO productDAO = new ProductDAO();
        // X??? l?? s???n ph???m thu???c category n??o
        String categoryPath = "";

        for (String categoryId : product.getCategories()) {
            CategoryModel category = categoryDAO.getOne(categoryId);
            if (category == null) {
                throw new Exception("Category does not exist. Please try again!!");
            }
            categoryPath += category.getPath() + ";";
        }

        product.setCategoryPath(categoryPath);

        String slug = product.getName().trim().toLowerCase().replace(" ", "-");
        product.setSlug(slug);

        Date createAt = new Date();
        product.setCreateAt(createAt);

        product.setFacets(new ArrayList<FilterModel>());

        // X??? l?? brand c???a s???n ph???m
        FilterModel brand = filterDao.getOne(product.getBrandId());
        if (brand == null) {
            throw new Exception("Brand does not exist. Please try again!!");
        }

        product.getFacets().add(brand);
        product.setBrand(brand.getName());

        // X??? l?? color c???a s???n ph???m
        FilterModel color = filterDao.getOne(product.getColor());
        if (color == null) {
            throw new Exception("Color does not exist. Please try again!!");
        }
        product.getFacets().add(color);

        // X??? l?? filter cho s???n ph???m
        for (String filterId : product.getFilters()) {
            FilterModel f = filterDao.getOne(filterId);
            if (f == null) {
                throw new Exception("Filter does not exist. Please try again!!");
            }
            product.getFacets().add(f);
        }

        double price = 9999;
        double discountPrice = 9999;

        if (product.getVariants() == null || product.getVariants().size() == 0) {
            throw new Exception("A product must have at least one variant!!");
        }
        // X??? l?? bi???n th???
        for (VariantModel variant : product.getVariants()) {

            if (variant.getId() == null) {
                Random rand = new Random();
                String variantId = product.getId() + Integer.toString(rand.nextInt(1000000));

                while (productDAO.getVariant(variantId) != null) {
                    variantId = Integer.toString(rand.nextInt(1000000));
                }

                variant.setId(variantId);
            }

            FilterModel f = filterDao.getOne(variant.getSizeId());

            if (f == null) {
                throw new Exception("Size does not exist. Please try again!!");
            }
            int count = 0;

            for (VariantModel v : product.getVariants()) {
                if (v.getSizeId().equals(variant.getSizeId())) {
                    count++;
                }
            }

            if (count >= 2) {
                throw new Exception("Variant with size '" + f.getName() + "' already exists. Please try again!!");
            }

            if (variant.getPrice() < variant.getDiscountPrice()) {
                throw new Exception("Discount price must be smaller price");
            }

            if (variant.getPrice() <= 0 || variant.getDiscountPrice() <= 0) {
                throw new Exception("Price and discount price must be greater 0$");
            }

            if (variant.getPrice() < price) {
                price = variant.getPrice();
                discountPrice = variant.getDiscountPrice();
            }

            variant.setSize(f.getName());

            product.getFacets().add(f);
        }

        product.setPrice(price);
        product.setDiscountPrice(discountPrice);
    }

    private BasicDBObject filterProduct(String[] filter) {
        String queryFilter = "";
        ArrayList<ArrayList<String>> filterList = null;
        if (filter != null && filter.length > 0) {
            Arrays.sort(filter);
            filterList = new ArrayList<ArrayList<String>>();
            ArrayList<String> orList = new ArrayList<String>();
            int n = filter.length;
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(filter[0]);
            filterList.add(temp);

            for (int i = 1; i < n; i++) {
                if (!filter[i].substring(0, 3).equals(filter[i - 1].substring(0, 3))) {
                    temp = new ArrayList<String>();
                    filterList.add(temp);
                }
                temp.add(filter[i]);
            }

            for (ArrayList<String> arrayList : filterList) {
                String orQuery = "{ $or: [";
                orQuery += String.format("{'facets._id': '%s'}", arrayList.get(0));
                for (int i = 1; i < arrayList.size(); i++) {
                    orQuery += String.format(", {'facets._id': '%s'}", arrayList.get(i));
                }
                orQuery += "] }";
                orList.add(orQuery);
            }

            queryFilter = "{ $and: [";
            queryFilter += orList.get(0);
            for (int i = 1; i < orList.size(); i++) {
                queryFilter += ", " + orList.get(i);
            }

            queryFilter += " ] }";
        }

        BasicDBObject filters;

        try {
            filters = BasicDBObject.parse(queryFilter);
        } catch (Exception e) {
            filters = null;
        }

        return filters;
    }

    private BasicDBObject sortProduct(String sortParam) {
        if (sortParam == null) {
            return BasicDBObject.parse("{ createAt: -1}");
        }

        String sortField = sortParam;
        int sortType = 1;
        if (sortParam.startsWith("-")) {
            sortField = sortParam.substring(1);
            sortType = -1;
        }
        BasicDBObject sort = new BasicDBObject(sortField, sortType);

        return sort;
    }

    private void updateProduct(ProductModel product, ProductModel newProduct) {
        String name = newProduct.getName();
        ArrayList<String> categories = newProduct.getCategories();
        String brandId = newProduct.getBrandId();
        String color = newProduct.getColor();
        ArrayList<String> imageCovers = newProduct.getImageCovers();
        ArrayList<String> images = newProduct.getImages();
        String longDescription = newProduct.getLongDescription();
        String shortDescription = newProduct.getShortDescription();
        Boolean isFeatured = newProduct.getIsFeatured();
        ArrayList<String> filters = newProduct.getFilters();
        Date createAt = newProduct.getCreateAt();
        ArrayList<VariantModel> variants = newProduct.getVariants();

        product.setName(name != null ? name : product.getName());
        product.setCategories(categories != null ? categories : product.getCategories());
        product.setBrandId(brandId != null ? brandId : product.getBrandId());
        product.setColor(color != null ? color : product.getColor());
        product.setImages(images != null ? images : product.getImages());
        product.setImageCovers(imageCovers != null ? imageCovers : product.getImageCovers());
        product.setLongDescription(longDescription != null ? longDescription : product.getLongDescription());
        product.setShortDescription(shortDescription != null ? shortDescription : product.getShortDescription());
        product.setIsFeatured(isFeatured != null ? isFeatured : product.getIsFeatured());
        product.setFilters(filters != null ? filters : product.getFilters());
        product.setCreateAt(createAt != null ? createAt : product.getCreateAt());
        product.setVariants(variants != null ? variants : product.getVariants());
    }

    private void preparePrint(ProductModel product) {
        product.setFacets(null);
        product.setLongDescription(null);
        product.setShortDescription(null);
    }
}
