package com.lockerdelivery.delivery.service.impl;

import com.lockerdelivery.delivery.service.DeliveryService;
import com.lockerdelivery.locker.domain.Locker;
import com.lockerdelivery.locker.repository.LockerRepository;
import com.lockerdelivery.parcel.domain.Parcel;
import com.lockerdelivery.parcel.domain.ParcelStatus;
import com.lockerdelivery.parcel.repository.ParcelRepository;
import com.lockerdelivery.slot.domain.LockerSlot;
import com.lockerdelivery.slot.domain.SlotStatus;
import com.lockerdelivery.slot.repository.SlotRepository;
import java.security.SecureRandom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final ParcelRepository parcelRepository;
    private final LockerRepository lockerRepository;
    private final SlotRepository slotRepository;

    public DeliveryServiceImpl(
        ParcelRepository parcelRepository,
        LockerRepository lockerRepository,
        SlotRepository slotRepository
    ) {
        this.parcelRepository = parcelRepository;
        this.lockerRepository = lockerRepository;
        this.slotRepository = slotRepository;
    }

    @Override
    @Transactional
    public String placeParcelInLocker(Long parcelId, Long lockerId) {
        // 1) Load the parcel to be delivered.
        Parcel parcel = parcelRepository.findById(parcelId)
            .orElseThrow(() -> new IllegalArgumentException("Parcel not found: " + parcelId));

        // 2) Load and validate the locker.
        Locker locker = lockerRepository.findById(lockerId)
            .orElseThrow(() -> new IllegalArgumentException("Locker not found: " + lockerId));

        // 3) Find the first FREE slot in the target locker.
        LockerSlot freeSlot = slotRepository.findFirstByLockerIdAndStatus(locker.getId(), SlotStatus.FREE)
            .orElseThrow(() -> new IllegalStateException("No free slot available in locker: " + lockerId));

        // 4) Mark slot as occupied and assign an access code.
        String accessCode = generateAccessCode();
        freeSlot.setStatus(SlotStatus.OCCUPIED);
        freeSlot.setAccessCode(accessCode);
        freeSlot.setParcel(parcel);

        // 5) Update parcel status to IN_LOCKER and link to the occupied slot.
        parcel.setStatus(ParcelStatus.IN_LOCKER);
        parcel.setLockerSlot(freeSlot);

        // 6) Persist updates in a single transaction.
        slotRepository.save(freeSlot);
        parcelRepository.save(parcel);

        return accessCode;
    }

    private String generateAccessCode() {
        int code = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(code);
    }
}
