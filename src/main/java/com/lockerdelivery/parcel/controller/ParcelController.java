package com.lockerdelivery.parcel.controller;

import com.lockerdelivery.parcel.dto.CreateParcelRequest;
import com.lockerdelivery.parcel.dto.ParcelResponse;
import com.lockerdelivery.parcel.service.ParcelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parcels")
public class ParcelController {

    private final ParcelService parcelService;

    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @PostMapping
    public ResponseEntity<ParcelResponse> createParcel(@Valid @RequestBody CreateParcelRequest request) {
        ParcelResponse response = parcelService.createParcel(request);
        return ResponseEntity.ok(response);
    }
}
