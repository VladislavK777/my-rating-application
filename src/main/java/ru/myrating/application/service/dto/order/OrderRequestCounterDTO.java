package ru.myrating.application.service.dto.order;

import java.util.List;

public class OrderRequestCounterDTO {
    private Integer all;
    private Integer allPaid;
    private Integer allPeriod;
    private Integer allPeriodPaid;
    private List<OrderRequestDTO> orders;

    public OrderRequestCounterDTO() {
    }

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

    public Integer getAllPaid() {
        return allPaid;
    }

    public void setAllPaid(Integer allPaid) {
        this.allPaid = allPaid;
    }

    public Integer getAllPeriod() {
        return allPeriod;
    }

    public void setAllPeriod(Integer allPeriod) {
        this.allPeriod = allPeriod;
    }

    public Integer getAllPeriodPaid() {
        return allPeriodPaid;
    }

    public void setAllPeriodPaid(Integer allPeriodPaid) {
        this.allPeriodPaid = allPeriodPaid;
    }

    public List<OrderRequestDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderRequestDTO> orders) {
        this.orders = orders;
    }
}
