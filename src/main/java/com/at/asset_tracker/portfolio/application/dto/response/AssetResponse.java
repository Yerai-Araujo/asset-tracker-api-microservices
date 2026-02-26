package com.at.asset_tracker.portfolio.application.dto.response;

import com.at.asset_tracker.portfolio.domain.model.enums.AssetType;
import com.at.asset_tracker.portfolio.domain.model.enums.AssetUnit;


public record AssetResponse(
        Long id,
        String symbol,
        AssetType type,
        AssetUnit unit,
        String name
) {}
