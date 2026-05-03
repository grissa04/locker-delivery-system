package com.lockerdelivery.delivery.dto;

import jakarta.validation.constraints.NotNull;

public record PlaceDeliveryRequest(
    @NotNull Long parcelId,
    @NotNull Long lockerId
) {

}