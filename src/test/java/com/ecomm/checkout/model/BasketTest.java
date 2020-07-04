package com.ecomm.checkout.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasketTest {
    @Test
    public void testAddProduct() {
        Basket basket = new Basket();
        Assertions.assertTrue(basket.getProducts().isEmpty());
        basket.addProduct(new Product());
        Assertions.assertEquals(1, basket.getProducts().size());
        Assertions.fail();
    }

    @Test
    public void testCreatedAndExpirationDate() {
        Basket basket = new Basket();
        Assertions.assertNotNull(basket.getCreated());
        Assertions.assertNotNull(basket.getExpiration());
        Assertions.assertEquals(basket.getCreated().plusDays(2).getDayOfMonth(),
                basket.getExpiration().getDayOfMonth());
    }
}
