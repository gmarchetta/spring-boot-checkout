package com.ecomm.checkout.service.sales;

import com.ecomm.checkout.model.BasketItem;
import com.ecomm.checkout.model.sales.Sale;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Calculator for bulk purchase
 */
@Service
@Qualifier("bulkPurchaseCalculator")
public class BulkPurchaseSalesCalculator implements SalesCalculator {

    /**
     * Method to calculate discounts applicable for the item. It checks that the basket item is eligible for the
     * specified sale, and that the sale is active, before returning the discount.
     *
     * If the sale is not active or the basket item does not have the minimum required quantity, discount is zero.
     *
     * If the item is eligible for this sale, discount is calculated doing: product price * product quantity * discount
     * percentage
     * @param sale eligible for the item
     * @param item for which we want to calculate discounts
     * @return a big decimal representing the discount for the specified basket item
     */
    @Override
    public BigDecimal calculateDiscount(Sale sale, BasketItem item) {
        BigDecimal discount = BigDecimal.ZERO;
        if(sale.isActive() && item.getQuantity() >= sale.getMinimumProductQuantity()) {
            discount =
                    item.getProduct().getPrice().multiply(sale.getDiscountPercentage().divide(BigDecimal.valueOf(100))).multiply(new BigDecimal(item.getQuantity()));
        }

        return discount;
    }
}
