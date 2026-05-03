package com.lockerdelivery.parcel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateParcelRequest(
    @NotBlank String recipientName,
    @NotNull Long lockerId
) {
}
