package ru.myrating.application.service.dto.criteria;

import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

public class UserCriteria implements Serializable, Criteria {

    private StringFilter partnerName;
    private StringFilter referenceLink;
    private StringFilter authorities;
    private StringFilter login;

    public StringFilter getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(StringFilter partnerName) {
        this.partnerName = partnerName;
    }

    public StringFilter getReferenceLink() {
        return referenceLink;
    }

    public void setReferenceLink(StringFilter referenceLink) {
        this.referenceLink = referenceLink;
    }

    public StringFilter getAuthorities() {
        return authorities;
    }

    public void setAuthorities(StringFilter authorities) {
        this.authorities = authorities;
    }

    public StringFilter getLogin() {
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public UserCriteria() {
    }

    public UserCriteria(UserCriteria other) {
        this.partnerName = other.partnerName == null ? null : other.partnerName.copy();
        this.referenceLink = other.referenceLink == null ? null : other.referenceLink.copy();
        this.authorities = other.authorities == null ? null : other.authorities.copy();
        this.login = other.login == null ? null : other.login.copy();
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
                Objects.equals(partnerName, that.partnerName) &&
                        Objects.equals(referenceLink, that.referenceLink) &&
                        Objects.equals(authorities, that.authorities) &&
                        Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                partnerName,
                referenceLink,
                authorities,
                login
        );
    }

    @Override
    public String toString() {
        return "UserCriteria{" +
                (partnerName != null ? "partnerName=" + partnerName + ", " : "") +
                (referenceLink != null ? "referenceLink=" + referenceLink + ", " : "") +
                (authorities != null ? "authorities=" + authorities + ", " : "") +
                (login != null ? "login=" + login + ", " : "") +
                "}";
    }
}