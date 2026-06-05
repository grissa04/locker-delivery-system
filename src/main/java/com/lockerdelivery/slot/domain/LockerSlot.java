package com.lockerdelivery.slot.domain;

import com.lockerdelivery.locker.domain.Locker;
import com.lockerdelivery.parcel.domain.Parcel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "locker_slots")
public class LockerSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locker_id", nullable = false)
    private Locker locker;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus status = SlotStatus.FREE;

    @Column(name = "access_code")
    private String accessCode;

    @OneToOne(mappedBy = "lockerSlot")
    private Parcel parcel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Locker getLocker() {
        return locker;
    }

    public SlotStatus getStatus() {
        return status;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void fillWithParcel(Parcel parcel) {
        this.status = SlotStatus.OCCUPIED;
        this.parcel = parcel;
    }

    public void fillWithParcel(Parcel parcel, String accessCode) {
        this.accessCode = accessCode;
        fillWithParcel(parcel);
    }

    public void release() {
        this.status = SlotStatus.FREE;
        this.accessCode = null;
        this.parcel = null;
    }

}
