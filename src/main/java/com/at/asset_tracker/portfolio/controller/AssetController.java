package com.at.asset_tracker.portfolio.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.at.asset_tracker.portfolio.application.dto.request.CreateAssetRequest;
import com.at.asset_tracker.portfolio.application.dto.response.AssetResponse;
import com.at.asset_tracker.portfolio.application.service.AssetApplicationService;
import com.at.asset_tracker.portfolio.domain.model.Asset;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetApplicationService assetService;

    public AssetController(AssetApplicationService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public ResponseEntity<AssetResponse> create(@RequestBody CreateAssetRequest request) {

        Asset asset = assetService.create(
                request.symbol(),
                request.type(),
                request.unit(),
                request.name()
        );

        return ResponseEntity
        .created(URI.create("/api/assets/" + asset.id()))
        .body(toResponse(asset));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponse> findById(@PathVariable Long id) {

        Asset asset = assetService.findById(id);

        return ResponseEntity.ok(toResponse(asset));
    }

    private AssetResponse toResponse(Asset asset) {
        return new AssetResponse(
                asset.id(),
                asset.symbol(),
                asset.type(),
                asset.unit(),
                asset.name()
        );
    }
}
