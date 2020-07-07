package com.ecomm.checkout.service.sales;

import com.ecomm.checkout.model.BasketItem;
import com.ecomm.checkout.model.sales.Sale;
import com.ecomm.checkout.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service that contains methods to operate with sales
 */
@Service
public class SalesService {
    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private SalesFactory salesFactory;

    /**
     * Method to get a sale for which the specified item is eligible. Only one sale can be applied at the time. If
     * more than one sale applies to the item, we return the one with the bigger discount
     * @param item for which we want to get an eligible sale
     * @return sale for which the specified item is eligible
     */
    public Sale getSalesForItem(BasketItem item) {
        return salesRepository.findActiveSaleForProductType(item.getProduct().getProductType(),
                item.getQuantity());
    }

    /**
     * Method to calculate the discount for a specified item. It gets a sale that is eligible for this item, and then
     * uses the salesFactory to get the sale calculator. Then uses the calculator to calculate the discount
     * @param item for which we want to calculate discounts
     * @return the discount to apply
     */
    public BigDecimal calculateDiscount(BasketItem item) {
        BigDecimal discount = BigDecimal.ZERO;
        Sale sale = getSalesForItem(item);
        SalesCalculator saleCalculator = salesFactory.getSaleCalculator(sale);
        if(saleCalculator != null) {
            discount = saleCalculator.calculateDiscount(sale, item);
        }

        return discount;
    }
}
