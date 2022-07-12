package ru.myrating.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "order_report_content")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class OrderReportContent extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_report_content_generator")
    @SequenceGenerator(name = "order_report_content_generator", sequenceName = "order_report_content_generator", allocationSize = 1)
    private Long id;

    @Column(name = "order_result_link")
    private UUID orderResultLink;

    @Type(type = "jsonb")
    @Column(name = "order_result_content", columnDefinition = "jsonb")
    @Basic(fetch = LAZY)
    private Map<String, Object> orderResult;

    @Column(name = "activated")
    private boolean activated;

    @Column(name = "deactivate_date", updatable = false)
    private LocalDateTime deactivateDate;

    @OneToOne(mappedBy = "orderReportContent")
    private OrderRequest orderRequest;

    public OrderReportContent() {
    }

    public OrderReportContent(UUID orderResultLink, Map<String, Object> orderResult, boolean activated, LocalDateTime deactivateDate) {
        this.orderResultLink = orderResultLink;
        this.orderResult = orderResult;
        this.activated = activated;
        this.deactivateDate = deactivateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getOrderResultLink() {
        return orderResultLink;
    }

    public void setOrderResultLink(UUID orderResultLink) {
        this.orderResultLink = orderResultLink;
    }

    public Map<String, Object> getOrderResult() {
        return orderResult;
    }

    public void setOrderResult(Map<String, Object> orderResult) {
        this.orderResult = orderResult;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public LocalDateTime getDeactivateDate() {
        return deactivateDate;
    }

    public void setDeactivateDate(LocalDateTime deactivateDate) {
        this.deactivateDate = deactivateDate;
    }

    public OrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderReportContent)) {
            return false;
        }
        return id != null && id.equals(((OrderReportContent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderReportContent{" +
                "id=" + id +
                ", orderResult='" + orderResult + '\'' +
                ", activated=" + activated +
                ", deactivateDate=" + deactivateDate +
                '}';
    }
}
