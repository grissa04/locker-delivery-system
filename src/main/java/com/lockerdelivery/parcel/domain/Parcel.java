package com.lockerdelivery.parcel.domain;

import com.lockerdelivery.slot.domain.LockerSlot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "parcels")
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ParcelStatus status = ParcelStatus.CREATED;

    @Column(name = "recipient_name")
    private String recipientName;

    @OneToOne
    @JoinColumn(name = "slot_id")
    private LockerSlot lockerSlot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public void setStatus(ParcelStatus status) {
        this.status = status;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public LockerSlot getLockerSlot() {
        return lockerSlot;
    }

    public void setLockerSlot(LockerSlot lockerSlot) {
        this.lockerSlot = lockerSlot;
    }
}
