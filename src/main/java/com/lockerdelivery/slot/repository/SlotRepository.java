package com.lockerdelivery.slot.repository;

import com.lockerdelivery.slot.domain.LockerSlot;
import com.lockerdelivery.slot.domain.SlotStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<LockerSlot, Long> {

    Optional<LockerSlot> findFirstByLockerIdAndStatus(Long lockerId, SlotStatus status);
}
