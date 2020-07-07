package com.ecomm.checkout.service.sales;

import com.ecomm.checkout.model.sales.Sale;
import com.ecomm.checkout.model.sales.SaleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SalesFactoryTest {
    @Autowired
    private SalesFactory factory;

    @Test
    public void testGetSaleCalculatorBulkPurchaseSale() {
        Sale sale = new Sale();
        sale.setSaleType(SaleType.BULK_PURCHASE);

        SalesCalculator calculator = factory.getSaleCalculator(sale);
        Assertions.assertTrue(calculator instanceof BulkPurchaseSalesCalculator);
    }

    @Test
    public void testGetSaleCalculatorBuyTwoOneFreeSale() {
        Sale sale = new Sale();
        sale.setSaleType(SaleType.BUY_TWO_ONE_FREE);

        SalesCalculator calculator = factory.getSaleCalculator(sale);
        Assertions.assertTrue(calculator instanceof BuyTwoOneFreeSalesCalculator);
    }
}
