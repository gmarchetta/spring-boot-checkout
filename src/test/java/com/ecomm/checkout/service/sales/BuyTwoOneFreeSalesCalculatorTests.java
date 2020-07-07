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
public class BuyTwoOneFreeSalesCalculatorTests {
    @Autowired
    private BuyTwoOneFreeSalesCalculator calculator;

    @Test
    public void testBuyTwoOneFreeCalculateDiscount() {
        BasketItem basketItem = new BasketItem();
        basketItem.setQuantity(7L);

        Product product = new Product();
        product.setProductType(ProductType.TSHIRT);
        product.setPrice(new BigDecimal(10));
        basketItem.setProduct(product);

        Sale sale = new Sale();
        sale.setSaleType(SaleType.BUY_TWO_ONE_FREE);
        sale.setMinimumProductQuantity(5L);
        sale.setActive(true);
        sale.setDiscountPercentage(new BigDecimal(0));

        BigDecimal discount = calculator.calculateDiscount(sale, basketItem);
        Assertions.assertEquals(new BigDecimal(20), discount);
    }

    @Test
    public void testBuyTwoOneFreeCalculateDiscountInactiveSaleZeroDiscount() {
        BasketItem basketItem = new BasketItem();
        basketItem.setQuantity(3L);

        Product product = new Product();
        product.setProductType(ProductType.MUG);
        product.setPrice(new BigDecimal(5));
        basketItem.setProduct(product);

        Sale sale = new Sale();
        sale.setSaleType(SaleType.BUY_TWO_ONE_FREE);
        sale.setMinimumProductQuantity(3L);
        sale.setActive(false);
        sale.setDiscountPercentage(new BigDecimal(0));

        BigDecimal discount = calculator.calculateDiscount(sale, basketItem);
        Assertions.assertEquals(BigDecimal.ZERO, discount);
    }
}
