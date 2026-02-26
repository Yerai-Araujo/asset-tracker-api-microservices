package com.at.asset_tracker.market.domain.model;

import java.math.BigDecimal;

import com.at.asset_tracker.portfolio.domain.model.enums.AssetType;


public interface MarketPriceProvider {

    boolean supports(AssetType type);

    BigDecimal getCurrentPrice(String symbol, AssetType type);
}