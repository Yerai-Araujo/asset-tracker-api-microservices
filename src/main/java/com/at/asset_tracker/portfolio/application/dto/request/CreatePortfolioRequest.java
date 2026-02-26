package com.at.asset_tracker.portfolio.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreatePortfolioRequest(

        @NotNull(message = "UserId is required")
        Long userId
) {}

