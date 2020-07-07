package com.ecomm.checkout.model.sales;

import com.ecomm.checkout.model.ProductType;

import java.math.BigDecimal;

/**
 * POJO to model available sales. Sales are not hardcoded.
 *
 * isActive: allows to enable or disable this sale
 * saleType: allows to define which type of sale this object references. Useful for building the correct
 * sale/discount calculator
 * affectedProductType: defines which products are eligible for this sale
 * minimumProductQuantity: defines what's the minimum amount of product required for this sale to apply
 * discountPercentage: discount percentage on this item, if applicable. Value between 0 and 100
 */
public class Sale {
    private Long id;
    private boolean isActive;
    private SaleType saleType;
    private Long minimumProductQuantity;
    private BigDecimal discountPercentage;
    private ProductType affectedProductType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public SaleType getSaleType() {
        return saleType;
    }

    public void setSaleType(SaleType saleType) {
        this.saleType = saleType;
    }

    public Long getMinimumProductQuantity() {
        return minimumProductQuantity;
    }

    public void setMinimumProductQuantity(Long minimumProductQuantity) {
        this.minimumProductQuantity = minimumProductQuantity;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public ProductType getAffectedProductType() {
        return affectedProductType;
    }

    public void setAffectedProductType(ProductType affectedProductType) {
        this.affectedProductType = affectedProductType;
    }
}
