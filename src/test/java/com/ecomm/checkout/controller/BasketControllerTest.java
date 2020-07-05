package com.ecomm.checkout.controller;

import com.ecomm.checkout.exception.BasketAlreadyConfirmedException;
import com.ecomm.checkout.exception.ResourceNotFoundException;
import com.ecomm.checkout.model.Basket;
import com.ecomm.checkout.service.BasketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
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

    @MockBean
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
        given(basketService.createBasket()).willThrow(RuntimeException.class);
        try{
            basketController.createBasket();
        } catch(RuntimeException e) {
            verify(basketService, VerificationModeFactory.times(1)).createBasket();
        }
    }

    @Test
    public void testDeleteBasketSuccess() {
        ResponseEntity<Basket> response = basketController.deleteBasket(1L);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteBasketThrowsNotFoundException() {
        doThrow(ResourceNotFoundException.class).when(basketService).deleteBasket(1L);
        try{
            basketController.deleteBasket(1L);
        } catch(ResourceNotFoundException e) {
            verify(basketService, VerificationModeFactory.times(1)).deleteBasket(1L);
        }
    }

    @Test
    public void testDeleteBasketThrowsBasketAlreadyConfirmedException() {
        doThrow(BasketAlreadyConfirmedException.class).when(basketService).deleteBasket(1L);
        try{
            basketController.deleteBasket(1L);
        } catch(BasketAlreadyConfirmedException e) {
            verify(basketService, VerificationModeFactory.times(1)).deleteBasket(1L);
        }
    }
}
