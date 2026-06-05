package com.lockerdelivery.pickup.service;

import com.lockerdelivery.pickup.dto.PickupResponse;

public interface PickupService {

    PickupResponse pickup(String accessCode);
}
