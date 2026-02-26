package com.at.asset_tracker.portfolio.domain.model;

import java.math.BigDecimal;


public class PortfolioItem {

    private Long id;

    private Long assetId;

    private BigDecimal quantity;

    PortfolioItem(Long id, Long assetId, BigDecimal quantity) {
        this.id = id;
        this.assetId = assetId;
        this.quantity = quantity;
    }

    void increaseQuantity(BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = this.quantity.add(amount);
    }

    public Long id() {
        return id;
    }

    public Long assetId() {
        return assetId;
    }

    public BigDecimal quantity() {
        return quantity;
    }
}