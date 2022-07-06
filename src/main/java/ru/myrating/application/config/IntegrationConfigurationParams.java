package ru.myrating.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "application.request-custom-params", ignoreUnknownFields = false)
public class IntegrationConfigurationParams {
    private String idType;
    private String inqPurpose;
    private String currencyCode;
    private String liability;
    private String product;
    private String IOType;
    private String outputFormat;
    private String lang;
    private String reportUser;
    private String userId;
    private String memberCode;
    private String password;

    private String consentFlag;

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getInqPurpose() {
        return inqPurpose;
    }

    public void setInqPurpose(String inqPurpose) {
        this.inqPurpose = inqPurpose;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getLiability() {
        return liability;
    }

    public void setLiability(String liability) {
        this.liability = liability;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getIOType() {
        return IOType;
    }

    public void setIOType(String IOType) {
        this.IOType = IOType;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getReportUser() {
        return reportUser;
    }

    public void setReportUser(String reportUser) {
        this.reportUser = reportUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConsentFlag() {
        return consentFlag;
    }

    public void setConsentFlag(String consentFlag) {
        this.consentFlag = consentFlag;
    }

    @Override
    public String toString() {
        return "IntegrationConfigurationParams{" +
                "idType='" + idType + '\'' +
                ", inqPurpose='" + inqPurpose + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", liability='" + liability + '\'' +
                ", product='" + product + '\'' +
                ", IOType='" + IOType + '\'' +
                ", outputFormat='" + outputFormat + '\'' +
                ", lang='" + lang + '\'' +
                ", consentFlag='" + consentFlag + '\'' +
                '}';
    }
}
