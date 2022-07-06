package ru.myrating.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "order_response_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderResponse {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_response_generator")
    @SequenceGenerator(name = "order_response_generator", sequenceName = "order_response_generator", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "order_request_id")
    @OneToOne
    private OrderRequest orderRequest;

    @Column(name = "i1")
    private Long i1;

    @Column(name = "i2")
    private Long i2;

    @Column(name = "i3")
    private Long i3;

    @Column(name = "i4")
    private Long i4;

    @Column(name = "a1")
    private Long a1;

    @Column(name = "a2")
    private Long a2;

    @Column(name = "a3")
    private Long a3;

    @Column(name = "a4")
    private Long a4;

    @Column(name = "a5")
    private Long a5;

    @Column(name = "a6")
    private Long a6;

    @Column(name = "a7")
    private Long a7;

    @Column(name = "a8")
    private Long a8;

    @Column(name = "a9")
    private Long a9;

    @Column(name = "a10")
    private Long a10;

    @Column(name = "a11")
    private Long a11;

    @Column(name = "a12")
    private Long a12;

    @Column(name = "a13")
    private Long a13;

    @Column(name = "a14")
    private Long a14;

    @Column(name = "a15")
    private Long a15;

    @Column(name = "a16")
    private Long a16;

    @Column(name = "a17")
    private Long a17;

    @Column(name = "d1")
    private String d1;

    @Column(name = "d2")
    private Long d2;

    @Column(name = "d3")
    private String d3;

    @Column(name = "d4")
    private Long d4;

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

    public Long getI1() {
        return i1;
    }

    public void setI1(Long i1) {
        this.i1 = i1;
    }

    public Long getI2() {
        return i2;
    }

    public void setI2(Long i2) {
        this.i2 = i2;
    }

    public Long getI3() {
        return i3;
    }

    public void setI3(Long i3) {
        this.i3 = i3;
    }

    public Long getI4() {
        return i4;
    }

    public void setI4(Long i4) {
        this.i4 = i4;
    }

    public Long getA1() {
        return a1;
    }

    public void setA1(Long a1) {
        this.a1 = a1;
    }

    public Long getA2() {
        return a2;
    }

    public void setA2(Long a2) {
        this.a2 = a2;
    }

    public Long getA3() {
        return a3;
    }

    public void setA3(Long a3) {
        this.a3 = a3;
    }

    public Long getA4() {
        return a4;
    }

    public void setA4(Long a4) {
        this.a4 = a4;
    }

    public Long getA5() {
        return a5;
    }

    public void setA5(Long a5) {
        this.a5 = a5;
    }

    public Long getA6() {
        return a6;
    }

    public void setA6(Long a6) {
        this.a6 = a6;
    }

    public Long getA7() {
        return a7;
    }

    public void setA7(Long a7) {
        this.a7 = a7;
    }

    public Long getA8() {
        return a8;
    }

    public void setA8(Long a8) {
        this.a8 = a8;
    }

    public Long getA9() {
        return a9;
    }

    public void setA9(Long a9) {
        this.a9 = a9;
    }

    public Long getA10() {
        return a10;
    }

    public void setA10(Long a10) {
        this.a10 = a10;
    }

    public Long getA11() {
        return a11;
    }

    public void setA11(Long a11) {
        this.a11 = a11;
    }

    public Long getA12() {
        return a12;
    }

    public void setA12(Long a12) {
        this.a12 = a12;
    }

    public Long getA13() {
        return a13;
    }

    public void setA13(Long a13) {
        this.a13 = a13;
    }

    public Long getA14() {
        return a14;
    }

    public void setA14(Long a14) {
        this.a14 = a14;
    }

    public Long getA15() {
        return a15;
    }

    public void setA15(Long a15) {
        this.a15 = a15;
    }

    public Long getA16() {
        return a16;
    }

    public void setA16(Long a16) {
        this.a16 = a16;
    }

    public Long getA17() {
        return a17;
    }

    public void setA17(Long a17) {
        this.a17 = a17;
    }

    public String getD1() {
        return d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public Long getD2() {
        return d2;
    }

    public void setD2(Long d2) {
        this.d2 = d2;
    }

    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public Long getD4() {
        return d4;
    }

    public void setD4(Long d4) {
        this.d4 = d4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderResponse)) {
            return false;
        }
        return id != null && id.equals(((OrderResponse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "id=" + id +
                ", orderRequestId=" + orderRequest.getId() +
                ", i1=" + i1 +
                ", i2=" + i2 +
                ", i3=" + i3 +
                ", i4=" + i4 +
                ", a1=" + a1 +
                ", a2=" + a2 +
                ", a3=" + a3 +
                ", a4=" + a4 +
                ", a5=" + a5 +
                ", a6=" + a6 +
                ", a7=" + a7 +
                ", a8=" + a8 +
                ", a9=" + a9 +
                ", a10=" + a10 +
                ", a11=" + a11 +
                ", a12=" + a12 +
                ", a13=" + a13 +
                ", a14=" + a14 +
                ", a15=" + a15 +
                ", a16=" + a16 +
                ", a17=" + a17 +
                ", d1=" + d1 +
                ", d2=" + d2 +
                ", d3=" + d3 +
                ", d4=" + d4 +
                '}';
    }
}
