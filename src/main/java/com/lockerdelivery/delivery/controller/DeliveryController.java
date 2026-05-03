package com.lockerdelivery.delivery.controller;

import com.lockerdelivery.delivery.service.DeliveryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/place")
    public ResponseEntity<PlaceDeliveryResponse> placeParcelInLocker(@Valid @RequestBody PlaceDeliveryRequest request) {
        // Delegate the full delivery flow to the service layer.
        String accessCode = deliveryService.placeParcelInLocker(request.parcelId(), request.lockerId());

        // Return the generated code for pickup access.
        return ResponseEntity.ok(new PlaceDeliveryResponse(accessCode));
    }

    public record PlaceDeliveryRequest(
        @NotNull Long parcelId,
        @NotNull Long lockerId
    ) {
    }

    public record PlaceDeliveryResponse(String accessCode) {
    }
}
