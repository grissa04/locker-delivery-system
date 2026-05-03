package com.lockerdelivery.parcel.service;

import com.lockerdelivery.parcel.dto.CreateParcelRequest;
import com.lockerdelivery.parcel.dto.ParcelResponse;

public interface ParcelService {

    ParcelResponse createParcel(CreateParcelRequest request);
}
