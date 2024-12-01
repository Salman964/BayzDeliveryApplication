package com.bayzdelivery.jobs;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.repositories.DeliveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class DelayedDeliveryNotifier {

    private static final Logger LOG = LoggerFactory.getLogger(DelayedDeliveryNotifier.class);

    @Autowired
    private DeliveryRepository deliveryRepository;


    @Scheduled(fixedDelay = 30000)
    public void checkDelayedDeliveries() {
        LOG.info("Checking for delayed deliveries...");
        Instant cutoffTime = Instant.now().minusSeconds(45 * 60); // 45 minutes ago
        List<Delivery> delayedDeliveries = deliveryRepository.findDelayedDeliveries(cutoffTime);

        if (!delayedDeliveries.isEmpty()) {
            for (Delivery delivery : delayedDeliveries) {
                LOG.warn("got delayed delivery: {}", delivery.getId());
                notifyCustomerSupport(delivery);
            }
        } else {
            LOG.info("No delayed deliveries found.");
        }
    }


    @Async
    public void notifyCustomerSupport(Delivery delivery) {
        System.out.println("Notifying customer support for delayed delivery ID: " + delivery.getId());
        LOG.info("Customer support notified for delayed delivery ID: {}", delivery.getId());
    }
}