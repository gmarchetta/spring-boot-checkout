package com.ecomm.checkout.service.sales;

import com.ecomm.checkout.exception.DiscountTypeNotImplementedException;
import com.ecomm.checkout.model.sales.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Class that uses the strategy pattern to build a calculator based on the sales available and the basket item
 */
@Service
public class SalesFactory {
    @Autowired
    @Qualifier("bulkPurchaseCalculator")
    private SalesCalculator bulkPurchaseCalculator;

    @Autowired
    @Qualifier("buyTwoOneFreeSalesCalculator")
    private SalesCalculator buyTwoOneFreeSalesCalculator;

    /**
     * Method that gets sales that are eligible for the item product and quantity, and uses the sale information to
     * build a calculator for the Sale type
     * @return the calculator to use based on the sale type
     */
    public SalesCalculator getSaleCalculator(Sale sale) {
        SalesCalculator calculator = null;
        if (sale == null) {
            return null;
        }

        switch (sale.getSaleType()) {
            case BULK_PURCHASE:
                calculator = bulkPurchaseCalculator;
                break;
            case BUY_TWO_ONE_FREE:
                calculator = buyTwoOneFreeSalesCalculator;
                break;
            default:
                throw new DiscountTypeNotImplementedException();
        }

        return calculator;
    }
}
