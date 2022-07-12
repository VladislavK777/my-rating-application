package ru.myrating.application.web.websocket.dto;

import java.io.Serializable;

public class ReportDTO implements Serializable {

    private Long orderId;

    private String reportLink;

    public ReportDTO() {
    }

    public ReportDTO(Long orderId, String reportLink) {
        this.orderId = orderId;
        this.reportLink = reportLink;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getReportLink() {
        return reportLink;
    }

    public void setReportLink(String reportLink) {
        this.reportLink = reportLink;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "orderId=" + orderId +
                ", reportLink='" + reportLink + '\'' +
                '}';
    }
}
