package ru.myrating.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_fault_queue")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderFaultQueue implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_fault_queue_generator")
    @SequenceGenerator(name = "order_fault_queue_generator", sequenceName = "order_fault_queue_generator", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "order_request_id")
    @OneToOne
    private OrderRequest orderRequest;

    @Column(name = "deadline_date", updatable = false)
    private LocalDateTime deadLineDate;

    @Column(name = "message_error")
    private String messageError;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    public LocalDateTime getDeadLineDate() {
        return deadLineDate;
    }

    public void setDeadLineDate(LocalDateTime deadLineDate) {
        this.deadLineDate = deadLineDate;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderFaultQueue)) {
            return false;
        }
        return id != null && id.equals(((OrderFaultQueue) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    public OrderFaultQueue() {
    }

    public OrderFaultQueue(OrderRequest orderRequest, LocalDateTime deadLineDate, String messageError) {
        this.orderRequest = orderRequest;
        this.deadLineDate = deadLineDate;
        this.messageError = messageError;
    }

    @Override
    public String toString() {
        return "OrderFaultQueue{" +
                "id=" + id +
                ", orderRequest=" + orderRequest +
                ", deadLineDate=" + deadLineDate +
                ", messageError='" + messageError + '\'' +
                '}';
    }
}
