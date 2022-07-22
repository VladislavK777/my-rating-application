package ru.myrating.application.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import ru.myrating.application.domain.enumeration.OrderStatusEnum;
import ru.myrating.application.domain.jsonb.OrderRequestData;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "order_request")
@Cache(usage = CacheConcurrencyStrategy.NONE)
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class OrderRequest extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_request_generator")
    @SequenceGenerator(name = "order_request_generator", sequenceName = "order_request_generator", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "partner_user_id")
    @ManyToOne(fetch = LAZY)
    private User partnerUser;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @Type(type = "jsonb")
    @Column(name = "order_data", columnDefinition = "jsonb")
    @Basic(fetch = LAZY)
    private OrderRequestData orderData;

    @Column(name = "payment_transaction_id")
    private String paymentTransactionId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_report_content", unique = true)
    private OrderReportContent orderReportContent;

    @Column(name = "person_data_deleted")
    private boolean personDataIsDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPartnerUser() {
        return partnerUser;
    }

    public void setPartnerUser(User partnerUser) {
        this.partnerUser = partnerUser;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public OrderRequestData getOrderData() {
        return orderData;
    }

    public void setOrderData(OrderRequestData orderData) {
        this.orderData = orderData;
    }

    public String getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(String paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    public OrderReportContent getOrderReportContent() {
        return orderReportContent;
    }

    public void setOrderReportContent(OrderReportContent orderReportContent) {
        this.orderReportContent = orderReportContent;
    }

    public boolean isPersonDataIsDeleted() {
        return personDataIsDeleted;
    }

    public void setPersonDataIsDeleted(boolean personDataIsDeleted) {
        this.personDataIsDeleted = personDataIsDeleted;
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
                ", status=" + status +
                ", transactionId='" + paymentTransactionId + '\'' +
                '}';
    }
}
