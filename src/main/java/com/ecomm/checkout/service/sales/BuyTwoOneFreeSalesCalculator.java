package com.ecomm.checkout.service.sales;

import com.ecomm.checkout.model.BasketItem;
import com.ecomm.checkout.model.sales.Sale;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Calculator for buy one, get one free
 */
@Service
@Qualifier("buyTwoOneFreeSalesCalculator")
public class BuyTwoOneFreeSalesCalculator implements SalesCalculator {
    /**
     * Method to calculate discounts applicable for the item. It checks that the sale is active, before returning the discount.
     *
     * If the sale is not active, discount is zero.
     *
     * If the item is eligible for this sale, discount is calculated doing: product price * product quantity for free
     * (one every 3 products in the basket)
     * discount
     * percentage
     * @param sale eligible for the item
     * @param item for which we want to calculate discounts
     * @return a big decimal representing the discount for the specified basket item
     */
    @Override
    public BigDecimal calculateDiscount(Sale sale, BasketItem item) {
        BigDecimal discount = BigDecimal.ZERO;
        if(sale.isActive()) {
            Long freeItems = item.getQuantity()/3;
            discount = item.getProduct().getPrice().multiply(new BigDecimal(freeItems));
        }

        return discount;
    }
}
