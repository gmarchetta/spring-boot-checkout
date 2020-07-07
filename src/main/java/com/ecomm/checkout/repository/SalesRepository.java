package com.ecomm.checkout.repository;

import com.ecomm.checkout.model.ProductType;
import com.ecomm.checkout.model.sales.Sale;

/**
 * Interface defining database operations that can be done on sales. On a later stage of development this
 * interface will extend Spring Data CrudRepository interface, which uses Spring Data JPA to create repository
 * implementations automatically, at runtime, from a repository interface.
 *
 * For now, we will include the same methods included in CrudRepository so later we can swap Repository
 * implementations to use the auto-implementation feature of Spring Data.
 *
 * See https://spring.io/guides/gs/accessing-data-jpa/ for more info.
 */
public interface SalesRepository {
    Sale findActiveSaleForProductType(ProductType productType, Long quantity);
}
