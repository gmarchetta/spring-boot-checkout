package com.ecomm.checkout.service;

import com.ecomm.checkout.exception.BasketAlreadyConfirmedException;
import com.ecomm.checkout.exception.ResourceNotFoundException;
import com.ecomm.checkout.model.Basket;
import com.ecomm.checkout.model.BasketItem;
import com.ecomm.checkout.model.BasketStatus;
import com.ecomm.checkout.model.Product;
import com.ecomm.checkout.repository.BasketRepository;
import com.ecomm.checkout.service.sales.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;

/**
 * Business logic class for performing operations on Basket
 */
@Service
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private SalesService salesService;

    /**
     * Creates a new basket object, sets its status to draft and stores it in persistence.
     *
     * @return the Basket that was created
     */
    public Basket createBasket() {
        Basket basket = new Basket();
        basket.setStatus(BasketStatus.DRAFT);
        return basketRepository.save(basket);
    }

    /**
     * Soft deletes the basket for the specified ID. Final result is that the basket status is set to CANCELLED. Data
     * is not hard deleted so it can be used later to learn about customer preferences, or understand how products
     * are performing.
     *
     * If the basket does not exist, a ResourceNotFoundException is thrown.
     * If the basket is in the CANCELLED status, a ResourceNotFoundException is thrown since logically it does not
     * exist.
     * If the basket is in the CONFIRMED status, a Conflict error is raised since we consider that an order was
     * already placed using this basket.
     *
     * @param basketId the basket identifier for the basket we want to delete
     */
    public void deleteBasket(Long basketId) {
        Basket basket = findById(basketId);
        basket.setStatus(BasketStatus.CANCELLED);
        basketRepository.save(basket);
    }

    /**
     * Adds the specified quantity of product to the basket
     * @param productId identifier for the product we want to add to a basket
     * @param basketId identifier for the basket that we want to add a product to
     * @param quantity quantity of product to add to the basket
     * @return the updated basket
     */
    public Basket addProductToBasket(Long productId, Long basketId, Long quantity) {
        Basket basket = findById(basketId);
        Product product = productService.findById(productId);
        basket.addProduct(product, quantity);
        basketRepository.save(basket);
        return basket;
    }

    /**
     * Method that calculates the total amount for a basket identified by the specified basketId, subtracting
     * discounts for any active sale that applies to the basket
     * @param basketId identifier for the basket for which we want to know the total amount
     * @return the localized amount in euros
     */
    public String getBasketTotal(Long basketId) {
        Basket basket = basketRepository.findById(basketId);
        BigDecimal amount = BigDecimal.ZERO;
        for(BasketItem item : basket.getBasketItems()) {
            amount = amount.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
            amount = amount.subtract(salesService.calculateDiscount(item));
        }

        return localizeMoney(amount);
    }

    public String localizeMoney(BigDecimal amount) {
        Currency euro = Currency.getInstance("EUR");
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(euro);
        return format.format(amount);
    }

    private Basket findById(Long basketId) {
        Basket basket = basketRepository.findById(basketId);
        if(basket == null || BasketStatus.CANCELLED == basket.getStatus()) {
            throw new ResourceNotFoundException();
        } else if(BasketStatus.CONFIRMED == basket.getStatus()) {
            throw new BasketAlreadyConfirmedException();
        }
        return basket;
    }
}
