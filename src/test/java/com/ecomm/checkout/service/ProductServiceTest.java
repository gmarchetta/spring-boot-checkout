package com.ecomm.checkout.service;

import com.ecomm.checkout.model.Product;
import com.ecomm.checkout.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Class containing unit tests for ProductService. ProductRepository is mocked so we can focus on the logic in
 * ProductService
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void testFindById() {
        Product product = new Product();
        given(productRepository.findById(1L)).willReturn(product);
        Product foundProduct = productService.findById(1L);
        Assertions.assertEquals(product, foundProduct);
        verify(productRepository, VerificationModeFactory.times(1)).findById(1L);
    }
}
