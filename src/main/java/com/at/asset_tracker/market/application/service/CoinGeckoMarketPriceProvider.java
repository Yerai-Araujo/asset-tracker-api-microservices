package com.at.asset_tracker.market.application.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.at.asset_tracker.portfolio.domain.model.enums.AssetType;

import lombok.extern.java.Log;

import com.at.asset_tracker.market.domain.model.MarketPriceProvider;
import com.at.asset_tracker.market.domain.exception.MarketPriceProviderException;

@Component
public class CoinGeckoMarketPriceProvider implements MarketPriceProvider {

    private final WebClient webClient;

    public CoinGeckoMarketPriceProvider(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://api.coingecko.com/api/v3")
                .build();
    }

    @Override
    public boolean supports(AssetType type) {
        return type == AssetType.CRYPTO;
    }

    @Override
    public BigDecimal getCurrentPrice(String symbol, AssetType type) {

        String id = mapSymbolToId(symbol);

        Map response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/simple/price")
                        .queryParam("ids", id)
                        .queryParam("vs_currencies", "usd")
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response == null || !response.containsKey(id)) {
            throw new MarketPriceProviderException("Invalid crypto response");
        }

        Map priceData = (Map) response.get(id);
        return new BigDecimal(priceData.get("usd").toString());
    }

    private String mapSymbolToId(String symbol) {
        if ("BTC".equalsIgnoreCase(symbol)) return "bitcoin";
        throw new UnsupportedOperationException("Unsupported crypto: " + symbol);
    }
}