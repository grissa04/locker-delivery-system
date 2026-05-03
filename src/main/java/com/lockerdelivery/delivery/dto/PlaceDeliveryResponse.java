package com.lockerdelivery.delivery.dto;

public record PlaceDeliveryResponse(String accessCode) {

  public static PlaceDeliveryResponse from(String accessCode) {
    return new PlaceDeliveryResponse(accessCode);
  }

}