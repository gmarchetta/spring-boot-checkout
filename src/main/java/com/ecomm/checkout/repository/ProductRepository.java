package com.ecomm.checkout.repository;

import com.ecomm.checkout.model.Product;

/**
 * Interface defining database operations that can be done on products. On a later stage of development this
 * interface will extend Spring Data CrudRepository interface, which uses Spring Data JPA to create repository
 * implementations automatically, at runtime, from a repository interface.
 *
 * For now, we will include the same methods included in CrudRepository so later we can swap Repository
 * implementations to use the auto-implementation feature of Spring Data.
 *
 * See https://spring.io/guides/gs/accessing-data-jpa/ for more info.
 */
public interface ProductRepository {
    /**
     * Saves a product to DB. Returns the saved product. If the product already exists, this method updates it. If it
     * does not, it auto generates an id and inserts a new record.
     *
     * @param product to create/update
     * @return the saved product
     */
    Product save(Product product);

    /**
     * Finds a product by ID in DB.
     * @param id
     * @return
     */
    Product findById(Long id);
}
