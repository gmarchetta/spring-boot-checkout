package com.ecomm.checkout.repository;

import com.ecomm.checkout.model.Product;
import com.ecomm.checkout.model.ProductType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In memory data repository initialized with available products. Contains a few methods to add and fetch products to
 * data structures. Products are stored in a map where keys are product ids, and values are the product objects.
 *
 * This class will be replaced once a real DB is implemented, which is quite simple using Spring Data.
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private Map<Long, Product> products = new ConcurrentHashMap<>();
    private Long nextId = 1L;

    /**
     * Constructor for the Database object. On a real DB this should be placed on an initialization script.
     */
    public ProductRepositoryImpl() {
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(5));
        product.setProductName("Lana Pen");
        product.setProductType(ProductType.PEN);
        save(product);

        product = new Product();
        product.setPrice(BigDecimal.valueOf(20));
        product.setProductName("Lana T-Shirt");
        product.setProductType(ProductType.TSHIRT);
        save(product);

        product = new Product();
        product.setPrice(BigDecimal.valueOf(7.5));
        product.setProductName("Lana Coffee Mug");
        product.setProductType(ProductType.MUG);
        save(product);
    }

    /**
     * Saves or update a product in the products map.
     *
     * @param product product to save. If product does not exist in map already, auto generates an ID.
     * @return the new/updated product.
     */
    public Product save(Product product) {
        if(product.getId() == null) {
            product.setId(nextId);
            nextId++;
        }

        products.put(product.getId(), product);
        return product;
    }

    /**
     * Returns the product with the specified ID in DB if it exists, or null if it does not.
     *
     * @param id of the product to fetch
     * @return the product for the specified ID, or null if no product with that id exists.
     */
    public Product findById(Long id) {
        return products.get(id);
    }
}
