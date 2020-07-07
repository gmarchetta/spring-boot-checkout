package com.ecomm.checkout.service.sales;

import com.ecomm.checkout.model.BasketItem;
import com.ecomm.checkout.model.Product;
import com.ecomm.checkout.model.ProductType;
import com.ecomm.checkout.model.sales.Sale;
import com.ecomm.checkout.model.sales.SaleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class BulkPurchaseSalesCalculatorTests {
    @Autowired
    private BulkPurchaseSalesCalculator calculator;

    @Test
    public void testBulkPurchaseCalculateDiscount() {
        BasketItem basketItem = new BasketItem();
        basketItem.setQuantity(3L);

        Product product = new Product();
        product.setProductType(ProductType.TSHIRT);
        product.setPrice(new BigDecimal(5));
        basketItem.setProduct(product);

        Sale sale = new Sale();
        sale.setSaleType(SaleType.BULK_PURCHASE);
        sale.setMinimumProductQuantity(3L);
        sale.setActive(true);
        sale.setDiscountPercentage(new BigDecimal(10));

        BigDecimal discount = calculator.calculateDiscount(sale, basketItem);
        Assertions.assertEquals(new BigDecimal(1.5), discount);
    }

    @Test
    public void testBulkPurchaseCalculateDiscountInactiveSaleZeroDiscount() {
        BasketItem basketItem = new BasketItem();
        basketItem.setQuantity(3L);

        Product product = new Product();
        product.setProductType(ProductType.TSHIRT);
        product.setPrice(new BigDecimal(5));
        basketItem.setProduct(product);

        Sale sale = new Sale();
        sale.setSaleType(SaleType.BULK_PURCHASE);
        sale.setMinimumProductQuantity(3L);
        sale.setActive(false);
        sale.setDiscountPercentage(new BigDecimal(10));

        BigDecimal discount = calculator.calculateDiscount(sale, basketItem);
        Assertions.assertEquals(BigDecimal.ZERO, discount);
    }

    @Test
    public void testBulkPurchaseCalculateDiscountNotEnoughItemsZeroDiscount() {
        BasketItem basketItem = new BasketItem();
        basketItem.setQuantity(3L);

        Product product = new Product();
        product.setProductType(ProductType.TSHIRT);
        product.setPrice(new BigDecimal(5));
        basketItem.setProduct(product);

        Sale sale = new Sale();
        sale.setSaleType(SaleType.BULK_PURCHASE);
        sale.setMinimumProductQuantity(5L);
        sale.setActive(true);
        sale.setDiscountPercentage(new BigDecimal(10));

        BigDecimal discount = calculator.calculateDiscount(sale, basketItem);
        Assertions.assertEquals(BigDecimal.ZERO, discount);
    }
}
