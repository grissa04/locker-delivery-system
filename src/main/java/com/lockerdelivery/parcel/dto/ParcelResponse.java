package com.lockerdelivery.parcel.dto;

import com.lockerdelivery.parcel.domain.ParcelStatus;

public record ParcelResponse(
    Long parcelId,
    Long lockerId,
    Long slotId,
    ParcelStatus status
) {
}
