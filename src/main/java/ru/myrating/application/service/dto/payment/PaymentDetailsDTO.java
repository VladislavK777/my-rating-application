package ru.myrating.application.service.dto.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class PaymentDetailsDTO implements Serializable {
    private String period;
    private Integer orderCount;
    private Integer payment;
    @JsonIgnore
    private Integer orderBy;

    public PaymentDetailsDTO() {
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}