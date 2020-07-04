package com.ecomm.checkout.repository;

import com.ecomm.checkout.model.Basket;
import com.ecomm.checkout.model.BasketStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BasketRepositoryTest {
    @Autowired
    private BasketRepository basketRepository;

    @Test
    public void testInsertNewBasket() {
        Basket basket = new Basket();
        Assertions.assertNull(basket.getId());
        basket = basketRepository.save(basket);
        Assertions.assertNotNull(basket.getId());

        Basket fetchedBasket = basketRepository.findById(basket.getId());
        Assertions.assertNotNull(fetchedBasket);
    }

    @Test
    public void testUpdateBasket() {
        Basket basket = new Basket();
        basket.setStatus(BasketStatus.DRAFT);
        basket = basketRepository.save(basket);

        Basket fetchedBasket = basketRepository.findById(basket.getId());
        fetchedBasket.setStatus(BasketStatus.CANCELLED);
        basketRepository.save(fetchedBasket);

        Basket updatedBasket = basketRepository.findById(basket.getId());
        Assertions.assertEquals(BasketStatus.CANCELLED, updatedBasket.getStatus());
    }

    @Test
    public void testFindBasketByUserIdFindsDraftBasket() {
        Basket basket = new Basket();
        basket.setStatus(BasketStatus.DRAFT);
        basket.setUserId(3L);
        basketRepository.save(basket);

        Basket result = basketRepository.findByUserId(3L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3L, basket.getUserId());
    }

    @Test
    public void testFindBasketByUserIdBasketCancelledNoResults() {
        Basket basket = new Basket();
        basket.setStatus(BasketStatus.CANCELLED);
        basket.setUserId(5L);
        basket = basketRepository.save(basket);

        Basket savedBasket = basketRepository.findById(basket.getId());
        Assertions.assertNotNull(savedBasket);

        Basket result = basketRepository.findByUserId(5L);
        Assertions.assertNull(result);
    }
}
