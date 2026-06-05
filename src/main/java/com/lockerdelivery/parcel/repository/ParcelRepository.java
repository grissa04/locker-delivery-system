package com.lockerdelivery.parcel.repository;

import com.lockerdelivery.parcel.domain.Parcel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {

    @Query("select p from Parcel p where p.lockerSlot.id = :slotId")
    Optional<Parcel> findBySlotId(Long slotId);
}
