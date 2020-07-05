package com.ecomm.checkout.controller;

import com.ecomm.checkout.model.Basket;
import com.ecomm.checkout.model.BasketStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Class containing integration tests for BasketController. It will spin up a server and send requests to it, parsing
 * the response and doing assertions on it.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasketControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testRequestToCreateBasketSuccess() {
        ResponseEntity<Basket> response = this.restTemplate.postForEntity("http://localhost:" + port + "/basket", "",
                Basket.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Basket basket = response.getBody();
        Assertions.assertNotNull(basket);
        Assertions.assertNotNull(basket.getId());
        Assertions.assertNotNull(basket.getCreated());
        Assertions.assertNotNull(basket.getExpiration());
        Assertions.assertTrue(basket.getProducts().isEmpty());
        Assertions.assertEquals(BasketStatus.DRAFT, basket.getStatus());
    }
}
