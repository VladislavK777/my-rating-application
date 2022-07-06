package ru.myrating.application.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import ru.myrating.application.domain.enumeration.OrderStatusEnum;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "order_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class OrderRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_request_generator")
    @SequenceGenerator(name = "order_request_generator", sequenceName = "order_request_generator", allocationSize = 1)
    private Long id;

    @Column(name = "created_date")
    private Instant createdDate = Instant.now();

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "link_on_report")
    private String linkOnReport;

    @Column(name = "ref_link")
    private String refLink;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String login;

    @Type(type = "jsonb")
    @Column(name = "order_data", columnDefinition = "jsonb")
    @Basic(fetch = LAZY)
    private OrderRequestData orderData;

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

    public OrderRequestData getOrderData() {
        return orderData;
    }

    public void setOrderData(OrderRequestData orderData) {
        this.orderData = orderData;
    }

    public String getRefLink() {
        return refLink;
    }

    public void setRefLink(String refLink) {
        this.refLink = refLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderRequest)) {
            return false;
        }
        return id != null && id.equals(((OrderRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", firstName='" + firstName + '\'' +
                ", linkOnReport='" + linkOnReport + '\'' +
                ", refLink='" + refLink + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
