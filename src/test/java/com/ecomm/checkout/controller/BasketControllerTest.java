package com.ecomm.checkout.controller;

import com.ecomm.checkout.model.Basket;
import com.ecomm.checkout.service.BasketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Class containing unit tests for BasketController. Dependencies are mocked so we can scope tests to the Controller
 * only, and manipulate scenarios.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BasketControllerTest {
    @Autowired
    private BasketController basketController;

    @Mock
    private BasketService basketService;

    @Test
    public void testCreateBasketSuccess() {
        Basket basket = new Basket();
        given(basketService.createBasket()).willReturn(basket);
        ResponseEntity<Basket> response = basketController.createBasket();
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCreateBasketExceptionOnService() {
        Basket basket = new Basket();
        given(basketService.createBasket()).willThrow(RuntimeException.class);
        try{
            basketController.createBasket();
        } catch(RuntimeException e) {
            verify(basketService.createBasket(), VerificationModeFactory.times(1));
        }
    }
}
