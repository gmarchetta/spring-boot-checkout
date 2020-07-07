package com.ecomm.checkout.repository;

import com.ecomm.checkout.model.ProductType;
import com.ecomm.checkout.model.sales.Sale;
import com.ecomm.checkout.model.sales.SaleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class SalesRepositoryTest {
    @Autowired
    private SalesRepository salesRepository;

    @Test
    public void testFindActiveSaleForProductTypeNoSalesForMugReturnsNull() {
        Sale sale = salesRepository.findActiveSaleForProductType(ProductType.MUG, 10L);
        Assertions.assertNull(sale);
    }

    @Test
    public void testFindActiveSaleForTshirtReturnsSale() {
        Sale sale = salesRepository.findActiveSaleForProductType(ProductType.TSHIRT, 10L);
        Assertions.assertNotNull(sale);
        Assertions.assertEquals(ProductType.TSHIRT, sale.getAffectedProductType());
        Assertions.assertEquals(new BigDecimal(25), sale.getDiscountPercentage());
        Assertions.assertEquals(3L, sale.getMinimumProductQuantity());
        Assertions.assertEquals(SaleType.BULK_PURCHASE, sale.getSaleType());
    }

    @Test
    public void testFindActiveSaleForTshirtNotEnoughQuantityReturnsNull() {
        Sale sale = salesRepository.findActiveSaleForProductType(ProductType.TSHIRT, 2L);
        Assertions.assertNull(sale);
    }

    @Test
    public void testFindActiveSaleForPenReturnsSale() {
        Sale sale = salesRepository.findActiveSaleForProductType(ProductType.PEN, 10L);
        Assertions.assertNotNull(sale);
        Assertions.assertEquals(ProductType.PEN, sale.getAffectedProductType());
        Assertions.assertEquals(new BigDecimal(0), sale.getDiscountPercentage());
        Assertions.assertEquals(3L, sale.getMinimumProductQuantity());
        Assertions.assertEquals(SaleType.BUY_TWO_ONE_FREE, sale.getSaleType());
    }

    @Test
    public void testFindActiveSaleForPenNotEnoughQuantityReturnsNull() {
        Sale sale = salesRepository.findActiveSaleForProductType(ProductType.PEN, 2L);
        Assertions.assertNull(sale);
    }
}
