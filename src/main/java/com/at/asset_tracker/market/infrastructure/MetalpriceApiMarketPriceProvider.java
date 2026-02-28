package com.at.asset_tracker.market.infrastructure;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.at.asset_tracker.market.domain.model.MarketPriceProvider;
import com.at.asset_tracker.market.domain.exception.MarketPriceProviderException;

@Component
public class MetalpriceApiMarketPriceProvider implements MarketPriceProvider {

    private final WebClient marketWebClient;

    @Value("${metalprice.api.key}")
    private String apiKey;

    public MetalpriceApiMarketPriceProvider(@Qualifier("metalPriceWebClient") WebClient marketWebClient) {
        this.marketWebClient = marketWebClient;
    }

    @Override
    public boolean supports(String type) {
        return type.equals("METAL");
    }

    @Override
    public BigDecimal getCurrentPrice(String symbol, String type) {

        try {
            Map<?, ?> response = marketWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/latest")
                            .queryParam("base", "USD")
                            .queryParam("symbols", symbol)
                            .queryParam("api_key", apiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response == null) {
                throw new MarketPriceProviderException("Invalid metal response");
            }

            @SuppressWarnings("unchecked")
            Map<String, Double> rates = (Map<String, Double>) response.get("rates");
            double apiValue = rates.get(symbol);
            double usdPerOunce = 1.0 / apiValue;
            return new BigDecimal(usdPerOunce);
        } catch (MarketPriceProviderException e) {
            throw e;
        } catch (Exception e) {
            throw new MarketPriceProviderException("External API timeout", e);
        }
    }
}
