package com.ecomm.checkout.repository;

import com.ecomm.checkout.model.Basket;

/**
 * Interface defining database operations that can be done on baskets. On a later stage of development this
 * interface will extend Spring Data CrudRepository interface, which uses Spring Data JPA to create repository
 * implementations automatically, at runtime, from a repository interface.
 *
 * For now, we will include the same methods included in CrudRepository so later we can swap Repository
 * implementations to use the auto-implementation feature of Spring Data.
 *
 * See https://spring.io/guides/gs/accessing-data-jpa/ for more info.
 */
public interface BasketRepository {
    /**
     * Saves a basket to DB. Returns the saved basket. If the basket already exists, this method updates it. If it
     * does not, it auto generates an id and inserts a new record.
     *
     * @param basket to create/update
     * @return the saved basket
     */
    Basket save(Basket basket);

    /**
     * Finds a basket in DB for the specified ID.
     *
     * @param id for the basket to find
     * @return the basket for the specified id, or null if it does not exist
     */
    Basket findById(Long id);
}
