package com.ecomm.checkout.repository;

import com.ecomm.checkout.model.Product;
import com.ecomm.checkout.model.ProductType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsertNewProduct() {
        Product product = new Product();
        Assertions.assertNull(product.getId());
        product = productRepository.save(product);
        Assertions.assertNotNull(product.getId());

        Product fetchedProduct = productRepository.findById(product.getId());
        Assertions.assertNotNull(fetchedProduct);
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product();
        product.setProductName("Some product");
        product = productRepository.save(product);

        Product fetchedProduct = productRepository.findById(product.getId());
        fetchedProduct.setProductName("Updated product name");
        productRepository.save(fetchedProduct);

        Product updatedProduct = productRepository.findById(product.getId());
        Assertions.assertEquals("Updated product name", updatedProduct.getProductName());
    }

    @Test
    public void testFindExistingProducts() {
        Product pen = productRepository.findById(1L);
        Assertions.assertEquals(ProductType.PEN, pen.getProductType());

        Product tshirt = productRepository.findById(2L);
        Assertions.assertEquals(ProductType.TSHIRT, tshirt.getProductType());

        Product mug = productRepository.findById(3L);
        Assertions.assertEquals(ProductType.MUG, mug.getProductType());
    }
}
