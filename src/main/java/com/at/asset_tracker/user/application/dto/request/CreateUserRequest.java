package com.at.asset_tracker.user.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(

        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "Email cannot be empty")
        String email
) {}
