package com.at.asset_tracker.market.application.service;

import java.math.BigDecimal;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.at.asset_tracker.market.domain.exception.MarketPriceProviderException;
import com.at.asset_tracker.market.infrastructure.CompositeMarketPriceProvider;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class MarketPriceService {

    private final CompositeMarketPriceProvider provider;
    private final CacheManager cacheManager;

    public MarketPriceService(CompositeMarketPriceProvider provider, CacheManager cacheManager) {
        this.provider = provider;
        this.cacheManager = cacheManager;
    }

    @Cacheable(value = "marketPrices", key = "#symbol + '_' + #type")
    @CircuitBreaker(name = "marketPriceCB", fallbackMethod = "fallbackPrice")
    @Retry(name = "marketPriceRetry")
    public BigDecimal getCurrentPrice(String symbol, String type) {
        return provider.getCurrentPrice(symbol, type);
    }

    public BigDecimal fallbackPrice(String symbol, String type, Throwable t) {

    BigDecimal cached = cacheManager
            .getCache("marketPrices")
            .get(symbol + "_" + type, BigDecimal.class);

    if (cached != null) {
        return cached;
    }

    throw new MarketPriceProviderException("Market temporarily unavailable", t);
}

}
