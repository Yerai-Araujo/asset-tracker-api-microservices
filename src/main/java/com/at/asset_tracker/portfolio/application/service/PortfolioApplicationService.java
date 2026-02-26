package com.at.asset_tracker.portfolio.application.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.at.asset_tracker.portfolio.domain.model.Portfolio;
import com.at.asset_tracker.market.domain.model.MarketPriceProvider;
import com.at.asset_tracker.portfolio.domain.repository.AssetRepository;
import com.at.asset_tracker.portfolio.domain.repository.PortfolioRepository;

@Service
@Transactional
public class PortfolioApplicationService {

    private final PortfolioRepository portfolioRepository;
    private final MarketPriceProvider marketPriceProvider;
    private final AssetRepository assetRepository;

    public PortfolioApplicationService(PortfolioRepository portfolioRepository,
            MarketPriceProvider marketPriceProvider, AssetRepository assetRepository) {
        this.portfolioRepository = portfolioRepository;
        this.marketPriceProvider = marketPriceProvider;
        this.assetRepository = assetRepository;
    }

    public Portfolio create(Long userId) {
        Portfolio portfolio = new Portfolio(null, userId);
        return portfolioRepository.save(portfolio);
    }

    public Portfolio addAsset(Long portfolioId, Long assetId, BigDecimal quantity) {

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found"));

        portfolio.addAsset(assetId, quantity);

        return portfolioRepository.save(portfolio);
    }

    @Transactional(readOnly = true)
    public Portfolio findById(Long id) {
        return portfolioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found"));
    }

    @Transactional(readOnly = true)
    public Portfolio findByUserId(Long userId) {
        return portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found for user ID: " + userId));
    }

    @Transactional(readOnly = true)
    public BigDecimal calculatePortfolioValue(Long portfolioId) {

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        return portfolio.items()
                .stream()
                .map(item -> {

                    var asset = assetRepository.findById(item.assetId())
                            .orElseThrow(() -> new RuntimeException("Asset not found"));

                    BigDecimal price = marketPriceProvider
                            .getCurrentPrice(asset.symbol(), asset.type());

                    return price.multiply(item.quantity());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
