package com.at.asset_tracker.portfolio.application.dto.response;

import java.util.Set;


public record PortfolioResponse(
        Long id,
        Long userId,
        Set<PortfolioItemResponse> items
) {}
