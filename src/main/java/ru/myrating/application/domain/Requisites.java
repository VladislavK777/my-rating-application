package ru.myrating.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "requisites")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Requisites extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requisites_generator")
    @SequenceGenerator(name = "requisites_generator", sequenceName = "requisites_generator")
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @NotNull
    @Column(name = "inn")
    private Long inn;

    @NotNull
    @Column(name = "bank_name")
    private String bankName;

    @NotNull
    @Column(name = "bik")
    private Long bik;

    @NotNull
    @Column(name = "kpp")
    private Long kpp;

    @NotNull
    @Column(name = "payment_account")
    private Long paymentAccount;

    @NotNull
    @Column(name = "correcpondent_account")
    private Long correcpondentAccount;

    @NotNull
    @Column(name = "legal_address")
    private String legalAddress;

    @Column(name = "post_address")
    private String postAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getInn() {
        return inn;
    }

    public void setInn(Long inn) {
        this.inn = inn;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Long getBik() {
        return bik;
    }

    public void setBik(Long bik) {
        this.bik = bik;
    }

    public Long getKpp() {
        return kpp;
    }

    public void setKpp(Long kpp) {
        this.kpp = kpp;
    }

    public Long getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(Long paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public Long getCorrecpondentAccount() {
        return correcpondentAccount;
    }

    public void setCorrecpondentAccount(Long correcpondentAccount) {
        this.correcpondentAccount = correcpondentAccount;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Requisites)) {
            return false;
        }
        return id != null && id.equals(((Requisites) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Requisites{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", inn=" + inn +
                ", bankName='" + bankName + '\'' +
                ", bik=" + bik +
                ", kpp=" + kpp +
                ", paymentAccount=" + paymentAccount +
                ", correcpondentAccount=" + correcpondentAccount +
                ", legalAddress='" + legalAddress + '\'' +
                ", postAddress='" + postAddress + '\'' +
                '}';
    }
}
