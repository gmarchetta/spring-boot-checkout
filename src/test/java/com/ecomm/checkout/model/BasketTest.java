package com.ecomm.checkout.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests on the Basket model to ensure add product logic works and protect it from future changes
 */
public class BasketTest {
    @Test
    public void testAddProduct() {
        Basket basket = new Basket();
        Assertions.assertTrue(basket.getBasketItems().isEmpty());
        basket.addProduct(new Product(), 1L);
        Assertions.assertEquals(1, basket.getBasketItems().size());
    }

    @Test
    public void testAddTwoProductsSeveralTimesAndCheckQuantities() {
        Basket basket = new Basket();
        Assertions.assertTrue(basket.getBasketItems().isEmpty());

        Product firstProduct = new Product();
        firstProduct.setId(1L);

        Product secondProduct = new Product();
        secondProduct.setId(2L);

        basket.addProduct(firstProduct, 1L);
        basket.addProduct(secondProduct, 3L);
        basket.addProduct(firstProduct, 5L);
        basket.addProduct(secondProduct, 7L);

        Assertions.assertEquals(2, basket.getBasketItems().size());
        BasketItem firstItem =
                basket.getBasketItems().stream().filter(item -> firstProduct.getId() == item.getProduct().getId()).findFirst().get();
        Assertions.assertEquals(6L, firstItem.getQuantity());

        BasketItem secondItem =
                basket.getBasketItems().stream().filter(item -> secondProduct.getId() == item.getProduct().getId()).findFirst().get();
        Assertions.assertEquals(10L, secondItem.getQuantity());
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
