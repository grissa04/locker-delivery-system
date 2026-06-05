package com.lockerdelivery.parcel.service.impl;

import static com.lockerdelivery.delivery.utils.AccessCodeGenerator.generateAccessCode;

import com.lockerdelivery.locker.domain.Locker;
import com.lockerdelivery.locker.repository.LockerRepository;
import com.lockerdelivery.parcel.domain.Parcel;
import com.lockerdelivery.parcel.dto.CreateParcelRequest;
import com.lockerdelivery.parcel.dto.ParcelResponse;
import com.lockerdelivery.parcel.repository.ParcelRepository;
import com.lockerdelivery.parcel.service.ParcelService;
import com.lockerdelivery.slot.domain.LockerSlot;
import com.lockerdelivery.slot.domain.SlotStatus;
import com.lockerdelivery.slot.repository.LockerSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;
    private final LockerRepository lockerRepository;
    private final LockerSlotRepository lockerSlotRepository;

    public ParcelServiceImpl(
        ParcelRepository parcelRepository,
        LockerRepository lockerRepository,
        LockerSlotRepository lockerSlotRepository
    ) {
        this.parcelRepository = parcelRepository;
        this.lockerRepository = lockerRepository;
        this.lockerSlotRepository = lockerSlotRepository;
    }

    @Override
    @Transactional
    public ParcelResponse createParcel(CreateParcelRequest request) {
        Locker locker = lockerRepository.findById(request.lockerId())
            .orElseThrow(() -> new IllegalArgumentException("Locker not found: " + request.lockerId()));

        LockerSlot freeSlot = lockerSlotRepository.findFirstByLockerIdAndStatus(locker.getId(), SlotStatus.FREE)
            .orElseThrow(() -> new IllegalStateException("No free slot available in locker: " + request.lockerId()));

        String accessCode = generateUniqueAccessCode();

        Parcel parcel = new Parcel();
        parcel.assignSlot(freeSlot, request.recipientName());
        freeSlot.fillWithParcel(parcel, accessCode);

        Parcel savedParcel = parcelRepository.save(parcel);
        lockerSlotRepository.save(freeSlot);

        return new ParcelResponse(
            savedParcel.getId(),
            locker.getId(),
            freeSlot.getId(),
            accessCode,
            savedParcel.getStatus()
        );
    }

    private String generateUniqueAccessCode() {
        String accessCode = generateAccessCode();

        while (lockerSlotRepository.existsByAccessCode(accessCode)) {
            accessCode = generateAccessCode();
        }

        return accessCode;
    }
}
