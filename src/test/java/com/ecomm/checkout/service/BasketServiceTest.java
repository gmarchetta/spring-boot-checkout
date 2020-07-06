package com.ecomm.checkout.service;

import com.ecomm.checkout.model.Basket;
import com.ecomm.checkout.repository.BasketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;

/**
 * Class containing unit tests for BaskterService. BasketRepository is mocked so we can focus on the logic in
 * ProductService
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BasketServiceTest {
    @Autowired
    private BasketService basketService;

    @MockBean
    private BasketRepository basketRepository;

    @Test
    public void testCreateBasket() {
        Basket basket = new Basket();
        basket.setId(1L);
        given(basketRepository.save(any(Basket.class))).willReturn(basket);

        Basket createdBasket = basketService.createBasket();
        Assertions.assertNotNull(createdBasket.getId());
    }
}
