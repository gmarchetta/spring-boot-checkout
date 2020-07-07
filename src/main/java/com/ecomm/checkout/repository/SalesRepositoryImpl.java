package com.ecomm.checkout.repository;

import com.ecomm.checkout.model.ProductType;
import com.ecomm.checkout.model.sales.Sale;
import com.ecomm.checkout.model.sales.SaleType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Interface defining database operations that can be done on Sales. On a later stage of development this
 * interface will extend Spring Data CrudRepository interface, which uses Spring Data JPA to create repository
 * implementations automatically, at runtime, from a repository interface.
 *
 * See https://spring.io/guides/gs/accessing-data-jpa/ for more info.
 */
@Repository
public class SalesRepositoryImpl implements SalesRepository {
    private Map<Long, Sale> sales = new ConcurrentHashMap<>();
    private Long nextId = 1L;

    /**
     * Initializing data
     */
    public SalesRepositoryImpl() {
        Sale sale = new Sale();
        sale.setActive(true);
        sale.setAffectedProductType(ProductType.TSHIRT);
        sale.setDiscountPercentage(new BigDecimal(25));
        sale.setMinimumProductQuantity(3L);
        sale.setSaleType(SaleType.BULK_PURCHASE);
        save(sale);

        sale = new Sale();
        sale.setActive(true);
        sale.setAffectedProductType(ProductType.PEN);
        sale.setDiscountPercentage(BigDecimal.ZERO);
        sale.setMinimumProductQuantity(3L);
        sale.setSaleType(SaleType.BUY_TWO_ONE_FREE);
        save(sale);
    }

    /**
     * Method to find an active sale for the specified product type and quantity
     * @param productType that we want to analyze to find an eligible sale
     * @param quantity basket item quantity that we want to analyze to find an eligible sale
     * @return an eligible sale, or null if none is available
     */
    @Override
    public Sale findActiveSaleForProductType(ProductType productType, Long quantity) {
        Sale activeSale = null;
        Optional<Sale> optional =
                sales.values().stream()
                        .filter(sale -> sale.isActive() == true && sale.getAffectedProductType() == productType && sale.getMinimumProductQuantity() <= quantity)
                        .sorted(Comparator.comparing(Sale::getDiscountPercentage).reversed())
                        .findFirst();

        if(optional.isPresent()) {
            activeSale = optional.get();
        }
        return activeSale;
    }

    /**
     * Saves or update a sale in the sales map.
     *
     * @param sale sale to save. If sale does not exist in map already, auto generates an ID.
     * @return the new/updated sale.
     */
    public Sale save(Sale sale) {
        if(sale.getId() == null) {
            sale.setId(nextId);
            nextId++;
        }

        sales.put(sale.getId(), sale);
        return sale;
    }
}
