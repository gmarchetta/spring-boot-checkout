package com.ecomm.checkout.controller;

import com.ecomm.checkout.controller.requests.AddProductDto;
import com.ecomm.checkout.model.Basket;
import com.ecomm.checkout.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/basket/{basketId}")
    public ResponseEntity deleteBasket(@PathVariable Long basketId) {
        basketService.deleteBasket(basketId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/basket/{basketId}/products")
    public ResponseEntity<Basket> addProductToBasket(@PathVariable Long basketId,
                                                    @RequestBody AddProductDto productDto) {
        Basket basket = basketService.addProductToBasket(productDto.getProductId(), basketId, productDto.getQuantity());
        return new ResponseEntity(basket, HttpStatus.OK);
    }

    @GetMapping("/basket/{basketId}/total")
    public ResponseEntity<String> getBasketTotal(@PathVariable Long basketId) {
        String basketTotal = basketService.getBasketTotal(basketId);
        return new ResponseEntity(basketTotal, HttpStatus.OK);
    }
}
