package com.lockerdelivery.locker.repository;

import com.lockerdelivery.locker.domain.Locker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LockerRepository extends JpaRepository<Locker, Long> {
}
