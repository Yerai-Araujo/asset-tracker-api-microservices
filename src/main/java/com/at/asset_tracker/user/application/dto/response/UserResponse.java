package com.at.asset_tracker.user.application.dto.response;


public record UserResponse(
        Long id,
        String name,
        String email,
        Long portfolioId
) {}
