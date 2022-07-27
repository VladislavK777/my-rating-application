package ru.myrating.application.domain.jsonb;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class RequisitesData implements Serializable {

    private String customerName;
    private Long inn;
    private String bankName;
    private Long bik;
    private Long kpp;
    private Long paymentAccount;
    private Long correcpondentAccount;
    private String legalAddress;
    private String postAddress;
    private String cardNumber;
    private String wallet;
    private String walletName;

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }
}
