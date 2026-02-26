package com.at.asset_tracker.portfolio.application.dto.response;

import java.math.BigDecimal;


public record PortfolioItemResponse(
        Long id,
        Long assetId,
        BigDecimal quantity
) {}
