package com.ecomm.checkout.service.sales;

import com.ecomm.checkout.model.BasketItem;
import com.ecomm.checkout.model.sales.Sale;

import java.math.BigDecimal;

/**
 * Interface with methods a calculator needs to implement to be incorporated to the Sales framework
 */
public interface SalesCalculator {
    BigDecimal calculateDiscount(Sale sale, BasketItem item);
}
