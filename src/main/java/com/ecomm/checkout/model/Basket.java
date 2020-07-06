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
    private List<BasketItem> items;
    private LocalDate created;
    private LocalDate expiration;

    public Basket() {
        this.created = LocalDate.now();
        this.expiration = created.plusDays(2);
        items = new ArrayList<>();
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

    public List<BasketItem> getBasketItems() {
        return items;
    }

    public void setBasketItems(List<BasketItem> items) {
        this.items = items;
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
    public void addProduct(Product product, Long quantity) {
        BasketItem item =
                this.items.stream().filter(currentItem -> product.getId() == currentItem.getProduct().getId()).findAny().orElse(null);

        if(item == null) {
            item = new BasketItem();
            item.setProduct(product);
            this.items.add(item);
        }

        item.setQuantity(item.getQuantity()+quantity);
    }
}
