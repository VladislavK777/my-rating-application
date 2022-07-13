package ru.myrating.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to My rating app.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private IntegrationParams integrationParams;
    private DurationFaultAttempts durationFaultAttempts;
    private String deactivateResultCron;

    private int personDataDeleteDays;

    private String personDataDeleteCron;

    private int lifeTimeResultDays;

    public IntegrationParams getIntegrationParams() {
        return integrationParams;
    }

    public void setIntegrationParams(IntegrationParams integrationParams) {
        this.integrationParams = integrationParams;
    }

    public DurationFaultAttempts getDurationFaultAttempts() {
        return durationFaultAttempts;
    }

    public void setDurationFaultAttempts(DurationFaultAttempts durationFaultAttempts) {
        this.durationFaultAttempts = durationFaultAttempts;
    }

    public String getDeactivateResultCron() {
        return deactivateResultCron;
    }

    public void setDeactivateResultCron(String deactivateResultCron) {
        this.deactivateResultCron = deactivateResultCron;
    }

    public int getPersonDataDeleteDays() {
        return personDataDeleteDays;
    }

    public void setPersonDataDeleteDays(int personDataDeleteDays) {
        this.personDataDeleteDays = personDataDeleteDays;
    }

    public String getPersonDataDeleteCron() {
        return personDataDeleteCron;
    }

    public void setPersonDataDeleteCron(String personDataDeleteCron) {
        this.personDataDeleteCron = personDataDeleteCron;
    }

    public int getLifeTimeResultDays() {
        return lifeTimeResultDays;
    }

    public void setLifeTimeResultDays(int lifeTimeResultDays) {
        this.lifeTimeResultDays = lifeTimeResultDays;
    }

    public static class IntegrationParams {
        private String url;
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
        private boolean needTrustCert;
        private boolean needSslCert;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

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

        public boolean isNeedTrustCert() {
            return needTrustCert;
        }

        public void setNeedTrustCert(boolean needTrustCert) {
            this.needTrustCert = needTrustCert;
        }

        public boolean isNeedSslCert() {
            return needSslCert;
        }

        public void setNeedSslCert(boolean needSslCert) {
            this.needSslCert = needSslCert;
        }
    }

    public static class DurationFaultAttempts {
        private int days;
        private String cron;

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }
    }
}
