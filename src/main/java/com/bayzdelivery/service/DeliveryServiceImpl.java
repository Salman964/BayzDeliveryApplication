package com.bayzdelivery.service;

import com.bayzdelivery.dto.DeliveryManCommission;
import com.bayzdelivery.dto.DeliveryManCommissionDTO;
import com.bayzdelivery.dto.TopDeliveryMenResponse;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.model.Role;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.DeliveryRepository;
import com.bayzdelivery.repositories.PersonRepository;
import org.springframework.data.domain.Pageable;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    DeliveryRepository deliveryRepository;
    

    @Autowired
    private PersonRepository personRepository;


    @Override
    public TopDeliveryMenResponse getTopDeliveryMen(Instant startTime, Instant endTime) {
        Pageable topThree = PageRequest.of(0, 3);

        List<DeliveryManCommissionDTO> topDeliveryMen = deliveryRepository.findTopDeliveryMenByCommission(
                startTime, endTime, topThree);
                
                System.out.println(topDeliveryMen.get(0).getTotalCommission());
                // System.out.println(topDeliveryMen.get(1).getTotalCommission());
                // System.out.println(topDeliveryMen.get(2).getTotalCommission());
                System.out.println("Salman   ------- Testing");


        BigDecimal averageCommission = deliveryRepository.findAverageCommission(startTime, endTime);

        TopDeliveryMenResponse response = new TopDeliveryMenResponse();
        response.setTopDeliveryMen(topDeliveryMen);
        response.setAverageCommission(averageCommission);

        return response;
    }


    @Override
    public Delivery save(Delivery delivery) {

        // Fetch deliveryMan and customer and set them in delivery
        Long deliveryManId = delivery.getDeliveryMan().getId();
        Person deliveryMan = personRepository.findById(deliveryManId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid deliveryMan ID"));
        delivery.setDeliveryMan(deliveryMan);

        Person customer = personRepository.findById(delivery.getCustomer().getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
        delivery.setCustomer(customer);

        BigDecimal commission = calculateCommission(delivery);
        System.out.println(commission);
        delivery.setComission(commission);

        boolean hasOngoingDelivery = deliveryRepository.existsByDeliveryMan_IdAndEndTimeIsNull(deliveryManId);
        if (hasOngoingDelivery) {
            throw new IllegalStateException("Delivery man is already assigned to an ongoing delivery.");
        }

        // Save the delivery
        Delivery savedDelivery = deliveryRepository.save(delivery);
        


        // Initialize deliveryMan and customer to ensure they are loaded
        savedDelivery.getDeliveryMan().getName(); // Access a property to initialize
        savedDelivery.getCustomer().getName(); // Access a property to initialize


        return savedDelivery;
    }


    @Override
    public Delivery findById(Long deliveryId) {
        Optional<Delivery> optionalDelivery = deliveryRepository.findById(deliveryId);
        return optionalDelivery.orElse(null);
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
