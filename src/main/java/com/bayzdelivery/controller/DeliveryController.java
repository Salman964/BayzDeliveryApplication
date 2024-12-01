package com.bayzdelivery.controller;

import com.bayzdelivery.dto.TopDeliveryMenResponse;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

  @Autowired
  DeliveryService deliveryService;

  @PostMapping
  public ResponseEntity<Object> createNewDelivery(@RequestBody Delivery delivery) {
    System.out.println(delivery);

    try {
      Delivery savedDelivery = deliveryService.save(delivery);
      return ResponseEntity.ok(savedDelivery);

    } 
    catch (IllegalArgumentException | IllegalStateException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{deliveryId}")
  public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long deliveryId) {
    Delivery delivery = deliveryService.findById(deliveryId);
    if (delivery !=null)
      return ResponseEntity.ok(delivery);
    return ResponseEntity.notFound().build();
	}

  @GetMapping("/top-delivery-men")
  public ResponseEntity<TopDeliveryMenResponse> getTopDeliveryMen(
          @RequestParam("startTime") String startTimeStr,
          @RequestParam("endTime") String endTimeStr) {

      try {
          Instant startTime = Instant.parse(startTimeStr);
          Instant endTime = Instant.parse(endTimeStr);

          TopDeliveryMenResponse response = deliveryService.getTopDeliveryMen(startTime, endTime);
          return ResponseEntity.ok(response);
      } catch (DateTimeParseException e) {
          return ResponseEntity.badRequest().body(null);
      }
  }

}
