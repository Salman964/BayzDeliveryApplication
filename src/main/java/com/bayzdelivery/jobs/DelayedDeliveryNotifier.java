package com.bayzdelivery.jobs;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.service.DeliveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// Import other necessary classes
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
public class DelayedDeliveryNotifier {

    private static final Logger LOG = LoggerFactory.getLogger(DelayedDeliveryNotifier.class);

    @Autowired
    private DeliveryService deliveryService;

    @Scheduled(fixedDelay = 900000) // Runs every 15 minutes
    public void checkDelayedDeliveries() {
        List<Delivery> ongoingDeliveries = deliveryService.findOngoingDeliveries();

        for (Delivery delivery : ongoingDeliveries) {
            Duration duration = Duration.between(delivery.getStartTime(), Instant.now());
            if (duration.toMinutes() > 45) {
                notifyCustomerSupport(delivery);
            }
        }
    }

    @Async
    public void notifyCustomerSupport(Delivery delivery) {
        LOG.info("Customer support team is notified! Delivery ID {} has exceeded 45 minutes.", delivery.getId());
    }
}
