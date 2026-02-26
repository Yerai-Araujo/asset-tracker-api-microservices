package com.at.asset_tracker.portfolio.application.dto.request;

import java.math.BigDecimal;

public record AddPortfolioItemRequest(
        Long assetId,
        BigDecimal quantity
) {
    
}
