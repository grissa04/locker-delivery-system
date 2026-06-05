package com.lockerdelivery.pickup.controller;

import com.lockerdelivery.pickup.dto.PickupResponse;
import com.lockerdelivery.pickup.dto.PickupRequest;
import com.lockerdelivery.pickup.service.PickupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pickup")
public class PickupController {

    private final PickupService pickupService;

    public PickupController(PickupService pickupService) {
        this.pickupService = pickupService;
    }

    @PostMapping
    public ResponseEntity<PickupResponse> pickup(@Valid @RequestBody PickupRequest request) {
        PickupResponse response = pickupService.pickup(request.accessCode());
        return ResponseEntity.ok(response);
    }
}
