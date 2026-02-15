package com.at.asset_tracker.domain.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(
    name = "portfolios",
    indexes = {
        @Index(name = "idx_portfolio_user", columnList = "user_id")
    }
)
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(
        mappedBy = "portfolio",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<PortfolioItem> items = new HashSet<>();

    public Portfolio() {}

    public Portfolio(User user) {
        this.user = user;
    }

    public Set<PortfolioItem> getItems() {
        return this.items;
    }

    public void addAsset(Asset asset, BigDecimal quantity) {
        PortfolioItem item = items.stream()
            .filter(i -> i.getAsset().equals(asset))
            .findFirst()
            .orElseGet(() -> {
                PortfolioItem newItem = new PortfolioItem(this, asset, BigDecimal.ZERO);
                items.add(newItem);
                return newItem;
            });

        item.increaseQuantity(quantity);
    }

    public void removeAsset(Asset asset) {
        items.removeIf(i -> i.getAsset().equals(asset));
    }
}

