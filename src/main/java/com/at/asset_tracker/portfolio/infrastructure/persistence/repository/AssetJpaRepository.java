package com.at.asset_tracker.portfolio.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.at.asset_tracker.portfolio.infrastructure.persistence.entity.AssetEntity;

public interface AssetJpaRepository
        extends JpaRepository<AssetEntity, Long> {

    boolean existsBySymbol(String symbol);
}
