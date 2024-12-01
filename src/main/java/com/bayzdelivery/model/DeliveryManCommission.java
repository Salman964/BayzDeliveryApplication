package com.bayzdelivery.dto;

import com.bayzdelivery.model.Person;
import java.math.BigDecimal;

public class DeliveryManCommission {
    private Long id;
    private String name;
    private BigDecimal totalCommission;

    public DeliveryManCommission(Person deliveryMan, BigDecimal totalCommission) {
        this.id = deliveryMan.getId();
        this.name = deliveryMan.getName();
        this.totalCommission = totalCommission;
    }

    // Getter and Setter for 'id'
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for 'name'
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for 'totalCommission'
    public BigDecimal getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(BigDecimal totalCommission) {
        this.totalCommission = totalCommission;
    }
}
