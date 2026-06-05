package com.lockerdelivery.pickup.service.impl;

import com.lockerdelivery.parcel.domain.Parcel;
import com.lockerdelivery.parcel.repository.ParcelRepository;
import com.lockerdelivery.pickup.dto.PickupResponse;
import com.lockerdelivery.pickup.service.PickupService;
import com.lockerdelivery.slot.domain.LockerSlot;
import com.lockerdelivery.slot.repository.LockerSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PickupServiceImpl implements PickupService {

    private final LockerSlotRepository lockerSlotRepository;
    private final ParcelRepository parcelRepository;

    public PickupServiceImpl(
        LockerSlotRepository lockerSlotRepository,
        ParcelRepository parcelRepository
    ) {
        this.lockerSlotRepository = lockerSlotRepository;
        this.parcelRepository = parcelRepository;
    }

    @Override
    @Transactional
    public PickupResponse pickup(String accessCode) {
        LockerSlot slot = lockerSlotRepository.findByAccessCode(accessCode)
            .orElseThrow(() -> new RuntimeException("Invalid access code"));

        Parcel parcel = parcelRepository.findBySlotId(slot.getId())
            .orElseThrow(() -> new RuntimeException("Parcel not found"));

        parcel.markAsPicked();
        slot.release();

        parcelRepository.save(parcel);
        lockerSlotRepository.save(slot);

        return new PickupResponse(parcel.getId(), parcel.getStatus().name());
    }
}
