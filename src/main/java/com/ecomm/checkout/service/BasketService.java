package com.ecomm.checkout.service;

import com.ecomm.checkout.model.Basket;
import com.ecomm.checkout.model.BasketStatus;
import com.ecomm.checkout.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;

    public Basket createBasket() {
        Basket basket = new Basket();
        basket.setStatus(BasketStatus.DRAFT);
        return basketRepository.save(basket);
    }
}
