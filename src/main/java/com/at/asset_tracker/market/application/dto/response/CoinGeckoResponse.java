package com.at.asset_tracker.market.application.dto.response;

import java.math.BigDecimal;
import java.util.Map;

public record CoinGeckoResponse(
        Map<String, Map<String, BigDecimal>> data
) {}