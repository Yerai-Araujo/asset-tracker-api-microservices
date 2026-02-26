package com.at.asset_tracker.portfolio.infrastructure.persistence.entity;

import java.util.HashSet;
import java.util.Set;

import com.at.asset_tracker.user.infrastructure.persistence.entity.UserEntity;

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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name = "portfolios",
    indexes = {
        @Index(name = "idx_portfolio_user", columnList = "user_id")
    }
)
public class PortfolioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(
        mappedBy = "portfolio",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<PortfolioItemEntity> items = new HashSet<>();


}

