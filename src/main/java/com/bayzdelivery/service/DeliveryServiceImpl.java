package com.bayzdelivery.service;

import com.bayzdelivery.dto.DeliveryManCommission;
import com.bayzdelivery.dto.TopDeliveryMenResponse;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.model.Role;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Override
    public Delivery save(Delivery delivery) {
        if (delivery.getDeliveryMan() == null) {
            throw new IllegalArgumentException("Delivery man must be assigned.");
        }

        if (!delivery.getDeliveryMan().getRole().equals(Role.DELIVERY_MAN)) {
            throw new IllegalArgumentException("Assigned person is not a delivery man.");
        }

        boolean hasOngoingDelivery = deliveryRepository.existsByDeliveryManIdAndEndTimeIsNull(delivery.getDeliveryMan().getId());
        if (hasOngoingDelivery) {
            throw new IllegalStateException("Delivery man is already assigned to an ongoing delivery.");
        }

        // Calculate and set commission
        BigDecimal commission = calculateCommission(delivery);
        delivery.setComission(commission);

        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery findById(Long deliveryId) {
        Optional<Delivery> optionalDelivery = deliveryRepository.findById(deliveryId);
        return optionalDelivery.orElse(null);
    }

    @Override
    public TopDeliveryMenResponse getTopDeliveryMen(Instant startTime, Instant endTime) {
        List<Delivery> deliveries = deliveryRepository.findByStartTimeBetween(startTime, endTime);

        Map<Person, BigDecimal> commissionMap = new HashMap<>();

        for (Delivery delivery : deliveries) {
            if (delivery.getDeliveryMan() != null) {
                BigDecimal commission = calculateCommission(delivery);
                commissionMap.merge(delivery.getDeliveryMan(), commission, BigDecimal::add);
            }
        }

        List<DeliveryManCommission> deliveryManCommissions = new ArrayList<>();
        for (Map.Entry<Person, BigDecimal> entry : commissionMap.entrySet()) {
            deliveryManCommissions.add(new DeliveryManCommission(entry.getKey(), entry.getValue()));
        }

        deliveryManCommissions.sort((a, b) -> b.getTotalCommission().compareTo(a.getTotalCommission()));

        List<DeliveryManCommission> topDeliveryMen = deliveryManCommissions.stream().limit(3).toList();

        BigDecimal averageCommission = BigDecimal.ZERO;
        if (!topDeliveryMen.isEmpty()) {
            BigDecimal totalCommission = topDeliveryMen.stream()
                    .map(DeliveryManCommission::getTotalCommission)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            averageCommission = totalCommission.divide(BigDecimal.valueOf(topDeliveryMen.size()), BigDecimal.ROUND_HALF_UP);
        }

        return new TopDeliveryMenResponse(topDeliveryMen, averageCommission);
    }

    @Override
    public List<Delivery> findOngoingDeliveries() {
        return deliveryRepository.findByEndTimeIsNull();
    }

    private BigDecimal calculateCommission(Delivery delivery) {
        BigDecimal orderPriceCommission = delivery.getPrice().multiply(new BigDecimal("0.05"));
        BigDecimal distanceCommission = BigDecimal.valueOf(delivery.getDistance()).multiply(new BigDecimal("0.5"));
        return orderPriceCommission.add(distanceCommission);
    }
}
