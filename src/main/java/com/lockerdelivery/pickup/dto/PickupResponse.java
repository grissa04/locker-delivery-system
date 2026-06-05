package com.lockerdelivery.pickup.dto;

public record PickupResponse(
    Long parcelId,
    String status
) {
}
