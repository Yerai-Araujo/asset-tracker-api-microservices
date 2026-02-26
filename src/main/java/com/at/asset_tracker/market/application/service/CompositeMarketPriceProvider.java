package com.at.asset_tracker.market.application.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.at.asset_tracker.portfolio.domain.model.enums.AssetType;
import com.at.asset_tracker.market.domain.model.MarketPriceProvider;
import com.at.asset_tracker.market.domain.exception.MarketPriceProviderException;

@Primary
@Component
public class CompositeMarketPriceProvider implements MarketPriceProvider {

    private final List<MarketPriceProvider> providers;

    public CompositeMarketPriceProvider(List<MarketPriceProvider> providers) {
        this.providers = providers;
    }

    @Override
    public boolean supports(AssetType type) {
        return true;
    }


    public BigDecimal getCurrentPrice(String symbol, AssetType type) {

        return providers.stream()
                .filter(p -> p.supports(type))
                .findFirst()
                .orElseThrow(() -> new MarketPriceProviderException("No provider for type"))
                .getCurrentPrice(symbol, type);
    }
}
