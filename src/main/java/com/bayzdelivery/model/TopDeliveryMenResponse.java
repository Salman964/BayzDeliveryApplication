package com.bayzdelivery.dto;

import java.math.BigDecimal;
import java.util.List;

public class TopDeliveryMenResponse {
    private List<DeliveryManCommission> topDeliveryMen;
    private BigDecimal averageCommission;

    public TopDeliveryMenResponse(List<DeliveryManCommission> topDeliveryMen, BigDecimal averageCommission) {
        this.topDeliveryMen = topDeliveryMen;
        this.averageCommission = averageCommission;
    }

    // Getter and Setter for 'topDeliveryMen'
    public List<DeliveryManCommission> getTopDeliveryMen() {
        return topDeliveryMen;
    }

    public void setTopDeliveryMen(List<DeliveryManCommission> topDeliveryMen) {
        this.topDeliveryMen = topDeliveryMen;
    }

    // Getter and Setter for 'averageCommission'
    public BigDecimal getAverageCommission() {
        return averageCommission;
    }

    public void setAverageCommission(BigDecimal averageCommission) {
        this.averageCommission = averageCommission;
    }
}
