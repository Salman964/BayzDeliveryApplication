package com.bayzdelivery.controller;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

  @Autowired
  DeliveryService deliveryService;

  @PostMapping
  public ResponseEntity<Delivery> createNewDelivery(@RequestBody Delivery delivery) {
    return ResponseEntity.ok(deliveryService.save(delivery));
  }

  @GetMapping("/{deliveryId}")
  public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long deliveryId) {
    Delivery delivery = deliveryService.findById(deliveryId);
    if (delivery !=null)
      return ResponseEntity.ok(delivery);
    return ResponseEntity.notFound().build();
	}


	// @GetMapping("/top")
	// public ResponseEntity<TopDeliveryMenResponse> getTopDeliveryMen(
	// 				@RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime startTime,
	// 				@RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endTime) {

	// 		// Convert OffsetDateTime to Instant
	// 		Instant startInstant = startTime.toInstant();
	// 		Instant endInstant = endTime.toInstant();

	// 		TopDeliveryMenResponse response = deliveryService.getTopDeliveryMen(startInstant, endInstant);
	// 		return ResponseEntity.ok(response);
	// }
}
