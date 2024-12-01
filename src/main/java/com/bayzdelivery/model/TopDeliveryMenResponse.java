package com.bayzdelivery.dto;

import java.math.BigDecimal;
import java.util.List;

public class TopDeliveryMenResponse {
    private List<DeliveryManCommissionDTO> topDeliveryMen;
    private BigDecimal averageCommission;

    // Getters and Setters
    public List<DeliveryManCommissionDTO> getTopDeliveryMen() {
        return topDeliveryMen;
    }

    public void setTopDeliveryMen(List<DeliveryManCommissionDTO> topDeliveryMen) {
        this.topDeliveryMen = topDeliveryMen;
    }

    public BigDecimal getAverageCommission() {
        return averageCommission;
    }

    public void setAverageCommission(BigDecimal averageCommission) {
        this.averageCommission = averageCommission;
    }
}
