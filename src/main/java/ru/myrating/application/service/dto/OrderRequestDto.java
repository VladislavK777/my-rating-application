package ru.myrating.application.service.dto;

import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.domain.enumeration.StatusOrderEnum;

import java.time.Instant;

public class OrderRequestDto {
    private Long id;
    private Instant createdDate;
    private String firstName;
    private String linkOnReport;
    private String refLink;
    private StatusOrderEnum status;
    private String email;
    private String login;

    public OrderRequestDto(OrderRequest orderRequest) {
        this.id = orderRequest.getId();
        this.createdDate = orderRequest.getCreatedDate();
        this.firstName = orderRequest.getFirstName();
        this.linkOnReport = orderRequest.getLinkOnReport();
        this.refLink = orderRequest.getRefLink();
        this.status = orderRequest.getStatus();
        this.email = orderRequest.getEmail();
        this.login = orderRequest.getLogin();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLinkOnReport() {
        return linkOnReport;
    }

    public void setLinkOnReport(String linkOnReport) {
        this.linkOnReport = linkOnReport;
    }

    public String getRefLink() {
        return refLink;
    }

    public void setRefLink(String refLink) {
        this.refLink = refLink;
    }

    public StatusOrderEnum getStatus() {
        return status;
    }

    public void setStatus(StatusOrderEnum status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
