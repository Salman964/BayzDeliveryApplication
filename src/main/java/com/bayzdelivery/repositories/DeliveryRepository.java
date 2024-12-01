package com.bayzdelivery.repositories;

import com.bayzdelivery.model.Delivery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import java.time.Instant;
import java.util.List;

@RestResource(exported = false)
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {

    List<Delivery> findByStartTimeBetween(Instant startTime, Instant endTime);

    List<Delivery> findByEndTimeIsNull();

    boolean existsByDeliveryManIdAndEndTimeIsNull(Long deliveryManId);
}
