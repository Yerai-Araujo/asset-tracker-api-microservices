package com.at.asset_tracker.market.infrastructure;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.at.asset_tracker.market.domain.model.MarketPriceProvider;
import com.at.asset_tracker.market.domain.exception.MarketPriceProviderException;

@Component
public class CoinGeckoMarketPriceProvider implements MarketPriceProvider {

    private final WebClient marketWebClient;

    public CoinGeckoMarketPriceProvider(@Qualifier("coinGeckoWebClient") WebClient marketWebClient) {
        this.marketWebClient = marketWebClient;
    }

    @Override
    public boolean supports(String type) {
        return "CRYPTO".equals(type);
    }

    @Override
    public BigDecimal getCurrentPrice(String symbol, String type) {

        String id = mapSymbolToId(symbol);

        try {
            Map<?, ?> response = marketWebClient.get()
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
            Map<?, ?> priceData = (Map<?, ?>) response.get(id);
            return new BigDecimal(priceData.get("usd").toString());
        } catch (MarketPriceProviderException e) {
            throw e;
        } catch (Exception e) {
            throw new MarketPriceProviderException("External API timeout", e);
        }

    }

    private String mapSymbolToId(String symbol) {
        if ("BTC".equalsIgnoreCase(symbol))
            return "bitcoin";
        throw new UnsupportedOperationException("Unsupported crypto: " + symbol);
    }
}