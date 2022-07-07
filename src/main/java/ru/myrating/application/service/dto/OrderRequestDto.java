package ru.myrating.application.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.domain.enumeration.OrderStatusEnum;

import java.time.Instant;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class OrderRequestDto {
    private Long id;
    @JsonIgnore
    private Instant createdDate;
    private String firstName;
    private String linkReport;
    private String refLink;
    @JsonIgnore
    private OrderStatusEnum status;
    private String email;
    private String login;

    public OrderRequestDto(OrderRequest orderRequest) {
        this.id = orderRequest.getId();
        this.createdDate = orderRequest.getCreatedDate();
        this.firstName = orderRequest.getFirstName();
        this.linkReport = orderRequest.getLinkReport().getId().toString();
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

    public String getLinkReport() {
        return linkReport;
    }

    public void setLinkReport(String linkReport) {
        this.linkReport = linkReport;
    }

    public String getRefLink() {
        return refLink;
    }

    public void setRefLink(String refLink) {
        this.refLink = refLink;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
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
