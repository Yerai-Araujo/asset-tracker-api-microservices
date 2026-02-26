package com.at.asset_tracker.portfolio.infrastructure.persistence.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Index;


@Getter
@Setter
@Entity
@Table(
    name = "portfolio_items",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"portfolio_id", "asset_id"}
    ),
    indexes = {
        @Index(name = "idx_portfolio_item_portfolio", columnList = "portfolio_id"),
        @Index(name = "idx_portfolio_item_asset", columnList = "asset_id")
    }
)

public class PortfolioItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "portfolio_id")
    private PortfolioEntity portfolio;

    //@ManyToOne(optional = false)
    @JoinColumn(name = "asset_id", nullable = false)
    private Long assetId;

    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;

}

