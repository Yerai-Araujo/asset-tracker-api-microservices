package com.at.asset_tracker.portfolio.domain.model;

import com.at.asset_tracker.portfolio.domain.model.enums.AssetType;
import com.at.asset_tracker.portfolio.domain.model.enums.AssetUnit;

public class Asset {

    private final Long id;
    private final String symbol;
    private final AssetType type;
    private final AssetUnit unit;
    private final String name;

    public Asset(Long id, String symbol, AssetType type, AssetUnit unit, String name) {

        if (symbol == null || symbol.isBlank())
            throw new IllegalArgumentException("Symbol required");

        if (type == null)
            throw new IllegalArgumentException("Type required");

        if (unit == null)
            throw new IllegalArgumentException("Unit required");

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name required");

        this.id = id;
        this.symbol = symbol;
        this.type = type;
        this.unit = unit;
        this.name = name;
    }

    public Long id() {
        return id;
    }

    public String symbol() {
        return symbol;
    }

    public AssetType type() {
        return type;
    }

    public AssetUnit unit() {
        return unit;
    }
    
    public String name() {
        return name;
    }
}