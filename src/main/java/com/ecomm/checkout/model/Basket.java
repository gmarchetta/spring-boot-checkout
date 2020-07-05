package com.ecomm.checkout.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A POJO representing a Basket.
 */
public class Basket {
    private Long id;
    private BasketStatus status;
    private List<Product> products;
    private LocalDate created;
    private LocalDate expiration;

    public Basket() {
        this.created = LocalDate.now();
        this.expiration = created.plusDays(2);
        products = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BasketStatus getStatus() {
        return status;
    }

    public void setStatus(BasketStatus status) {
        this.status = status;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    /**
     * Convenient method to add a product to the basket
     * @param product
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }
}
