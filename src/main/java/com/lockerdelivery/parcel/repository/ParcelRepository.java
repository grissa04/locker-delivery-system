package com.lockerdelivery.parcel.repository;

import com.lockerdelivery.parcel.domain.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
}
