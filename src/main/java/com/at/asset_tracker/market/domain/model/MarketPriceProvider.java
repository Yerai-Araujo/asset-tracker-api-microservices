package com.at.asset_tracker.market.domain.model;

import java.math.BigDecimal;



public interface MarketPriceProvider {

    boolean supports(String type);

    BigDecimal getCurrentPrice(String symbol, String type);
}