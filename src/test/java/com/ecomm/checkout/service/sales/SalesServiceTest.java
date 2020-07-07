package com.ecomm.checkout.service.sales;

import com.ecomm.checkout.model.BasketItem;
import com.ecomm.checkout.model.Product;
import com.ecomm.checkout.model.ProductType;
import com.ecomm.checkout.model.sales.Sale;
import com.ecomm.checkout.model.sales.SaleType;
import com.ecomm.checkout.repository.SalesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SalesServiceTest {
    @Autowired
    private SalesService salesService;

    @MockBean
    private SalesFactory salesFactory;

    @MockBean
    private SalesRepository salesRepository;

    @Test
    public void testCalculateDiscountNoDiscount() {
        Sale sale = new Sale();
        sale.setSaleType(SaleType.BULK_PURCHASE);
        sale.setMinimumProductQuantity(3L);
        sale.setActive(true);
        sale.setDiscountPercentage(new BigDecimal(10));
        given(salesRepository.findActiveSaleForProductType(any(ProductType.class), anyLong())).willReturn(sale);

        BasketItem item = new BasketItem();
        item.setQuantity(1L);

        Product product = new Product();
        product.setProductType(ProductType.TSHIRT);
        product.setPrice(new BigDecimal(5));
        item.setProduct(product);
        Assertions.assertEquals(BigDecimal.ZERO, salesService.calculateDiscount(item));
        verify(salesFactory, VerificationModeFactory.times(1)).getSaleCalculator(any(Sale.class));
    }

    @Test
    public void testCalculateDiscountBulkDiscountNotEnoughQuantityZeroDiscount() {
        Sale sale = new Sale();
        sale.setSaleType(SaleType.BULK_PURCHASE);
        sale.setMinimumProductQuantity(3L);
        sale.setActive(true);
        sale.setDiscountPercentage(new BigDecimal(10));
        given(salesRepository.findActiveSaleForProductType(any(ProductType.class), anyLong())).willReturn(sale);

        given(salesFactory.getSaleCalculator(any(Sale.class))).willReturn(new BulkPurchaseSalesCalculator());

        Product product = new Product();
        product.setProductType(ProductType.TSHIRT);
        product.setPrice(new BigDecimal(10));

        BasketItem item = new BasketItem();
        item.setQuantity(1L);
        item.setProduct(product);

        Assertions.assertEquals(BigDecimal.ZERO, salesService.calculateDiscount(item));
    }

    @Test
    public void testCalculateDiscountBulkDiscount() {
        Sale sale = new Sale();
        sale.setSaleType(SaleType.BULK_PURCHASE);
        sale.setMinimumProductQuantity(3L);
        sale.setActive(true);
        sale.setDiscountPercentage(new BigDecimal(10));
        given(salesRepository.findActiveSaleForProductType(any(ProductType.class), anyLong())).willReturn(sale);

        given(salesFactory.getSaleCalculator(any(Sale.class))).willReturn(new BulkPurchaseSalesCalculator());

        Product product = new Product();
        product.setProductType(ProductType.TSHIRT);
        product.setPrice(new BigDecimal(5));

        BasketItem item = new BasketItem();
        item.setQuantity(3L);
        item.setProduct(product);

        Assertions.assertEquals(new BigDecimal(1.5), salesService.calculateDiscount(item));
    }
}
