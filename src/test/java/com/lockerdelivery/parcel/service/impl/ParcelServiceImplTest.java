package com.lockerdelivery.parcel.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lockerdelivery.locker.domain.Locker;
import com.lockerdelivery.locker.repository.LockerRepository;
import com.lockerdelivery.parcel.domain.Parcel;
import com.lockerdelivery.parcel.domain.ParcelStatus;
import com.lockerdelivery.parcel.dto.CreateParcelRequest;
import com.lockerdelivery.parcel.dto.ParcelResponse;
import com.lockerdelivery.parcel.repository.ParcelRepository;
import com.lockerdelivery.slot.domain.LockerSlot;
import com.lockerdelivery.slot.domain.SlotStatus;
import com.lockerdelivery.slot.repository.LockerSlotRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParcelServiceImplTest {

    @Mock
    private ParcelRepository parcelRepository;

    @Mock
    private LockerRepository lockerRepository;

    @Mock
    private LockerSlotRepository lockerSlotRepository;

    @InjectMocks
    private ParcelServiceImpl parcelService;

    @Test
    void createParcelAssignsUniqueAccessCodeAndReturnsUpdatedResponse() {
        CreateParcelRequest request = new CreateParcelRequest("Alice", 1L);

        Locker locker = new Locker();
        locker.setId(1L);

        LockerSlot freeSlot = new LockerSlot();
        freeSlot.setId(10L);

        when(lockerRepository.findById(1L)).thenReturn(Optional.of(locker));
        when(lockerSlotRepository.findFirstByLockerIdAndStatus(1L, SlotStatus.FREE)).thenReturn(Optional.of(freeSlot));
        when(lockerSlotRepository.existsByAccessCode(any())).thenReturn(false);
        when(parcelRepository.save(any(Parcel.class))).thenAnswer(invocation -> {
            Parcel savedParcel = invocation.getArgument(0);
            savedParcel.setId(99L);
            return savedParcel;
        });

        ParcelResponse response = parcelService.createParcel(request);

        assertThat(response.parcelId()).isEqualTo(99L);
        assertThat(response.lockerId()).isEqualTo(1L);
        assertThat(response.slotId()).isEqualTo(10L);
        assertThat(response.accessCode()).matches("\\d{6}");
        assertThat(response.status()).isEqualTo(ParcelStatus.IN_LOCKER);
        assertThat(freeSlot.getAccessCode()).isEqualTo(response.accessCode());
        assertThat(freeSlot.getStatus()).isEqualTo(SlotStatus.OCCUPIED);
        assertThat(freeSlot.getParcel()).isNotNull();

        verify(lockerSlotRepository).save(freeSlot);
    }

    @Test
    void createParcelRetriesWhenGeneratedAccessCodeAlreadyExists() {
        CreateParcelRequest request = new CreateParcelRequest("Bob", 2L);

        Locker locker = new Locker();
        locker.setId(2L);

        LockerSlot freeSlot = new LockerSlot();
        freeSlot.setId(20L);

        when(lockerRepository.findById(2L)).thenReturn(Optional.of(locker));
        when(lockerSlotRepository.findFirstByLockerIdAndStatus(2L, SlotStatus.FREE)).thenReturn(Optional.of(freeSlot));
        when(lockerSlotRepository.existsByAccessCode(any())).thenReturn(true, false);
        when(parcelRepository.save(any(Parcel.class))).thenAnswer(invocation -> {
            Parcel savedParcel = invocation.getArgument(0);
            savedParcel.setId(100L);
            return savedParcel;
        });

        ParcelResponse response = parcelService.createParcel(request);

        assertThat(response.accessCode()).matches("\\d{6}");
        verify(lockerSlotRepository, times(2)).existsByAccessCode(any());
    }
}
