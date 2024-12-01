package com.bayzdelivery.dto;

import java.math.BigDecimal;

public class DeliveryManCommissionDTO {
    private Long deliveryManId;
    private String deliveryManName;
    private BigDecimal totalCommission;

    // Constructor
    public DeliveryManCommissionDTO(Long deliveryManId, String deliveryManName, BigDecimal totalCommission) {
        this.deliveryManId = deliveryManId;
        this.deliveryManName = deliveryManName;
        this.totalCommission = totalCommission;
    }

    // Getters and Setters
    public Long getDeliveryManId() {
        return deliveryManId;
    }

    public void setDeliveryManId(Long deliveryManId) {
        this.deliveryManId = deliveryManId;
    }

    public String getDeliveryManName() {
        return deliveryManName;
    }

    public void setDeliveryManName(String deliveryManName) {
        this.deliveryManName = deliveryManName;
    }

    public BigDecimal getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(BigDecimal totalCommission) {
        this.totalCommission = totalCommission;
    }
}
