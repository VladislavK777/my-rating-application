package ru.myrating.application.service.dto.payment;

import ru.myrating.application.domain.jsonb.RequisitesData;

import java.io.Serializable;
import java.util.LinkedList;

public class PaymentUserDTO implements Serializable {
    private Long partnerId;
    private String partnerName;
    private String partnerLogin;
    private String partnerUrl;
    private Integer partnerFee;
    private RequisitesData partnerRequisitesData;
    private LinkedList<PaymentDetailsDTO> paymentDetails = new LinkedList<>();

    public PaymentUserDTO() {
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerLogin() {
        return partnerLogin;
    }

    public void setPartnerLogin(String partnerLogin) {
        this.partnerLogin = partnerLogin;
    }

    public String getPartnerUrl() {
        return partnerUrl;
    }

    public void setPartnerUrl(String partnerUrl) {
        this.partnerUrl = partnerUrl;
    }

    public Integer getPartnerFee() {
        return partnerFee;
    }

    public void setPartnerFee(Integer partnerFee) {
        this.partnerFee = partnerFee;
    }

    public RequisitesData getPartnerRequisitesData() {
        return partnerRequisitesData;
    }

    public void setPartnerRequisitesData(RequisitesData partnerRequisitesData) {
        this.partnerRequisitesData = partnerRequisitesData;
    }

    public LinkedList<PaymentDetailsDTO> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(LinkedList<PaymentDetailsDTO> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}
