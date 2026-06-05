package com.lockerdelivery.pickup.dto;

import jakarta.validation.constraints.NotBlank;

public record PickupRequest(
    @NotBlank String accessCode
) {
}
