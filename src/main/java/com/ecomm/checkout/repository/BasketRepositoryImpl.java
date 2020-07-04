package com.ecomm.checkout.repository;

import com.ecomm.checkout.model.Basket;
import com.ecomm.checkout.model.BasketStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * In memory data repository to store baskets. Baskets are stored in maps where keys are basket ids, values are the
 * basket objects.
 *
 * This class will be replaced once a real DB is implemented, which is quite simple using Spring Data.
 */
@Component
public class BasketRepositoryImpl implements BasketRepository {
    private Map<Long, Basket> baskets = new HashMap<>();
    private Long nextId = 1L;

    /**
     * If the basket has no id, auto generate one and save the new basket. If it has an id, override what the map has
     * on that position.
     * @param basket
     * @return
     */
    public Basket save(Basket basket) {
        if(basket.getId() == null) {
            basket.setId(nextId);
            nextId++;
        }

        baskets.put(basket.getId(), basket);
        return basket;
    }

    /**
     * Returns a basket with the specified userId, or null if no basket with that id exists.
     * @param id
     * @return
     */
    public Basket findById(Long id) {
        return baskets.get(id);
    }

    /**
     * Method to find draft basket by its owner id. Implementation is awful but enough for the test. On a real DB
     * queries would be better
     * @param userId
     * @return
     */
    public Basket findByUserId(Long userId) {
        Basket result = null;
        for(Basket basket : baskets.values()) {
            if(basket.getUserId() == userId && BasketStatus.DRAFT == basket.getStatus()) {
                result = basket;
                break;
            }
        }

        return result;
    }
}
