package com.bayzdelivery.service;

import com.bayzdelivery.dto.TopDeliveryMenResponse;
import com.bayzdelivery.model.Delivery;

import java.time.Instant;
import java.util.List;

public interface DeliveryService {

    Delivery save(Delivery delivery);

    Delivery findById(Long deliveryId);

    TopDeliveryMenResponse getTopDeliveryMen(Instant startTime, Instant endTime);

    List<Delivery> findOngoingDeliveries();
}
