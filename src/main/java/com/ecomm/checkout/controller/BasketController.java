package com.ecomm.checkout.controller;

import com.ecomm.checkout.model.Basket;
import com.ecomm.checkout.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Class for handling requests for the Basket model. It has a dependency with the BasketService which is responsible 
 * for the business logic. No business logic should live here, only logic related to the web layer
 */
@Controller
public class BasketController {
    @Autowired
    private BasketService basketService;

    @PostMapping("/basket")
    public ResponseEntity<Basket> createBasket() {
        Basket basket = basketService.createBasket();
        return new ResponseEntity(basket, HttpStatus.CREATED);
    }
}
