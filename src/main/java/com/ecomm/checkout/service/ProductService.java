package com.ecomm.checkout.service;

import com.ecomm.checkout.model.Product;
import com.ecomm.checkout.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product findById(Long productId) {
        return productRepository.findById(productId);
    }
}
