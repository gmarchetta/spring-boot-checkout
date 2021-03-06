package com.ecomm.checkout.controller;

import com.ecomm.checkout.controller.requests.AddProductDto;
import com.ecomm.checkout.model.Basket;
import com.ecomm.checkout.model.BasketStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Class containing integration tests for BasketController. It will spin up a server and send requests to it, parsing
 * responses and doing assertions on them.
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
        Assertions.assertTrue(basket.getBasketItems().isEmpty());
        Assertions.assertEquals(BasketStatus.DRAFT, basket.getStatus());
    }

    @Test
    public void testRequestToDeleteNonExistingBasketNotFound() {
        ResponseEntity response = this.restTemplate.exchange("http://localhost:" + port + "/basket/1",
                HttpMethod.DELETE, new HttpEntity(null), String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRequestToDeleteExistingDraftBasketSuccess() {
        ResponseEntity<Basket> createResponse = this.restTemplate.postForEntity("http://localhost:" + port + "/basket",
                "", Basket.class);
        Basket createdBasket = createResponse.getBody();
        ResponseEntity deleteResponse =
                this.restTemplate.exchange("http://localhost:" + port + "/basket/" + createdBasket.getId(),
                HttpMethod.DELETE, new HttpEntity(null), String.class);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }

    @Test
    public void testRequestToDeleteExistingCancelledBasketBasketNotFound() {
        ResponseEntity<Basket> createResponse = this.restTemplate.postForEntity("http://localhost:" + port + "/basket",
                "", Basket.class);
        Basket createdBasket = createResponse.getBody();
        this.restTemplate.exchange("http://localhost:" + port + "/basket/" + createdBasket.getId(),
                HttpMethod.DELETE, new HttpEntity(null), String.class);
        ResponseEntity deleteResponse =
                this.restTemplate.exchange("http://localhost:" + port + "/basket/" + createdBasket.getId(),
                        HttpMethod.DELETE, new HttpEntity(null), String.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
    }

    @Test
    public void testRequestToAddProductToBasketSuccess() {
        AddProductDto dto = new AddProductDto();
        dto.setProductId(1L);
        dto.setQuantity(2L);

        ResponseEntity<Basket> createBasketResponse = this.restTemplate.postForEntity("http://localhost:" + port +
                        "/basket", "", Basket.class);
        Assertions.assertEquals(HttpStatus.CREATED, createBasketResponse.getStatusCode());
        Basket basket = createBasketResponse.getBody();
        Assertions.assertTrue(basket.getBasketItems().isEmpty());

        ResponseEntity<Basket> addProductResponse =
                this.restTemplate.postForEntity("http://localhost:" + port + "/basket/" + basket.getId() + "/products", dto,
                Basket.class);
        Basket basketWithProduct = addProductResponse.getBody();
        Assertions.assertEquals(1L, basketWithProduct.getBasketItems().size());
        Assertions.assertEquals(2L, basketWithProduct.getBasketItems().get(0).getQuantity());
        Assertions.assertEquals(1L, basketWithProduct.getBasketItems().get(0).getProduct().getId());

        ResponseEntity<Basket> secondAddProductResponse =
                this.restTemplate.postForEntity("http://localhost:" + port + "/basket/" + basket.getId() + "/products", dto,
                        Basket.class);
        basketWithProduct = secondAddProductResponse.getBody();
        Assertions.assertEquals(1L, basketWithProduct.getBasketItems().size());
        Assertions.assertEquals(4L, basketWithProduct.getBasketItems().get(0).getQuantity());
        Assertions.assertEquals(1L, basketWithProduct.getBasketItems().get(0).getProduct().getId());
    }

    @Test
    public void testRequestToGetTotalAfterAddingTwoProductsSuccess() {
        Basket basket =
                this.restTemplate.postForEntity("http://localhost:" + port + "/basket", "", Basket.class).getBody();

        AddProductDto dto = new AddProductDto();
        dto.setProductId(1L);
        dto.setQuantity(2L);
        this.restTemplate.postForEntity("http://localhost:" + port + "/basket/" + basket.getId() + "/products", dto,
                Basket.class);

        dto = new AddProductDto();
        dto.setProductId(2L);
        dto.setQuantity(2L);
        this.restTemplate.postForEntity("http://localhost:" + port + "/basket/" + basket.getId() + "/products", dto,
                Basket.class);

        String total = this.restTemplate.getForEntity("http://localhost:" + port + "/basket/" + basket.getId() +
                        "/total", String.class).getBody();
        Assertions.assertEquals("€50.00", total);
    }

    @Test
    public void testRequestToGetTotalAfterAddingFourPensDiscountAppliesSuccess() {
        Basket basket =
                this.restTemplate.postForEntity("http://localhost:" + port + "/basket", "", Basket.class).getBody();

        AddProductDto dto = new AddProductDto();
        dto.setProductId(1L);
        dto.setQuantity(2L);
        this.restTemplate.postForEntity("http://localhost:" + port + "/basket/" + basket.getId() + "/products", dto,
                Basket.class);

        dto = new AddProductDto();
        dto.setProductId(1L);
        dto.setQuantity(2L);
        this.restTemplate.postForEntity("http://localhost:" + port + "/basket/" + basket.getId() + "/products", dto,
                Basket.class);

        String total = this.restTemplate.getForEntity("http://localhost:" + port + "/basket/" + basket.getId() +
                "/total", String.class).getBody();
        // 4 pens at 5 each. Total would be 20, but we get one for free due to sale. So total is 15
        Assertions.assertEquals("€15.00", total);
    }
}
