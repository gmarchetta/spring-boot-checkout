package com.ecomm.checkout.service;

import com.ecomm.checkout.model.Basket;
import com.ecomm.checkout.model.Product;
import com.ecomm.checkout.model.ProductType;
import com.ecomm.checkout.repository.BasketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;

/**
 * Class containing unit tests for BaskterService. BasketRepository is mocked so we can focus on the logic in
 * ProductService
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BasketServiceTest {
    @Autowired
    private BasketService basketService;

    @MockBean
    private BasketRepository basketRepository;

    @Test
    public void testCreateBasket() {
        Basket basket = new Basket();
        basket.setId(1L);
        given(basketRepository.save(any(Basket.class))).willReturn(basket);

        Basket createdBasket = basketService.createBasket();
        Assertions.assertNotNull(createdBasket.getId());
    }

    @Test
    public void testGetBasketTotal() {
        Basket basket = new Basket();
        basket.setId(1L);

        Product firstProduct = new Product();
        firstProduct.setId(1L);
        firstProduct.setProductType(ProductType.MUG);
        firstProduct.setPrice(new BigDecimal(5));
        basket.addProduct(firstProduct, 3L);

        Product secondProduct = new Product();
        secondProduct.setId(2L);
        secondProduct.setProductType(ProductType.MUG);
        secondProduct.setPrice(new BigDecimal(20));
        basket.addProduct(secondProduct, 5L);

        given(basketRepository.findById(anyLong())).willReturn(basket);
        Assertions.assertEquals(basketService.localizeMoney(new BigDecimal(115)), basketService.getBasketTotal(1L));
    }

    @Test
    public void testLocalizeMoney() {
        String moneyAmount = basketService.localizeMoney(BigDecimal.ONE);
        Assertions.assertEquals("€1.00", moneyAmount);
    }
}
