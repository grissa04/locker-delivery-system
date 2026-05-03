package com.lockerdelivery.delivery.controller;

import com.lockerdelivery.delivery.dto.PlaceDeliveryRequest;
import com.lockerdelivery.delivery.dto.PlaceDeliveryResponse;
import com.lockerdelivery.delivery.service.DeliveryService;
import jakarta.validation.Valid;
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
    String accessCode = deliveryService.placeParcelInLocker(request.parcelId(), request.lockerId());
    return ResponseEntity.ok(PlaceDeliveryResponse.from(accessCode));
  }

}
