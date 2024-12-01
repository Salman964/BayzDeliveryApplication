package com.bayzdelivery.repositories;

import com.bayzdelivery.dto.DeliveryManCommissionDTO;
import com.bayzdelivery.model.Delivery;
import org.springframework.data.domain.Pageable;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RestResource(exported = false)
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {

    List<Delivery> findByStartTimeBetween(Instant startTime, Instant endTime);

    List<Delivery> findByEndTimeIsNull();

    boolean existsByDeliveryManIdAndEndTimeIsNull(Long deliveryManId);

    @Query("SELECT COUNT(d) > 0 FROM Delivery d WHERE d.deliveryMan.id = :deliveryManId AND ((:startTime BETWEEN d.startTime AND d.endTime) OR (:endTime BETWEEN d.startTime AND d.endTime) OR (d.startTime BETWEEN :startTime AND :endTime))")
    boolean existsOverlappingDelivery(@Param("deliveryManId") Long deliveryManId, @Param("startTime") Instant startTime, @Param("endTime") Instant endTime);


    @Query("SELECT new com.bayzdelivery.dto.DeliveryManCommissionDTO(d.deliveryMan.id, d.deliveryMan.name, SUM(d.comission)) " +
        "FROM Delivery d " +
        "WHERE d.startTime BETWEEN :startTime AND :endTime " +
        "GROUP BY d.deliveryMan.id, d.deliveryMan.name " +
        "ORDER BY SUM(d.comission) DESC")
    List<DeliveryManCommissionDTO> findTopDeliveryMenByCommission(
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime,
            Pageable pageable);

    @Query("SELECT AVG(d.comission) FROM Delivery d WHERE d.startTime BETWEEN :startTime AND :endTime")
    BigDecimal findAverageCommission(
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime);

    @Query("SELECT d FROM Delivery d WHERE d.endTime IS NULL AND d.startTime < :cutoffTime")
    List<Delivery> findDelayedDeliveries(Instant cutoffTime);
    boolean existsByDeliveryMan_IdAndEndTimeIsNull(Long deliveryManId);
}
