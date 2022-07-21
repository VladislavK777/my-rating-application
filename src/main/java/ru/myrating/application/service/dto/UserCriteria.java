package ru.myrating.application.service.dto;

import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

public class UserCriteria implements Serializable, Criteria {

    private StringFilter partnerName;

    public StringFilter getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(StringFilter partnerName) {
        this.partnerName = partnerName;
    }

    public UserCriteria() {
    }

    public UserCriteria(UserCriteria other) {
        this.partnerName = other.partnerName == null ? null : other.partnerName.copy();
    }

    @Override
    public Criteria copy() {
        return new UserCriteria(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserCriteria that = (UserCriteria) o;
        return
                Objects.equals(partnerName, that.partnerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                partnerName
        );
    }

    @Override
    public String toString() {
        return "UserCriteria{" +
                (partnerName != null ? "partnerName=" + partnerName + ", " : "") +
                "}";
    }
}
