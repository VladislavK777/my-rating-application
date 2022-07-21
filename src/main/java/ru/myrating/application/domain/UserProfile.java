package ru.myrating.application.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import ru.myrating.application.domain.enumeration.ProfileTypeEnum;
import ru.myrating.application.domain.jsonb.RequisitesData;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "user_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class UserProfile extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_generator")
    @SequenceGenerator(name = "user_profile_generator", sequenceName = "user_profile_generator", allocationSize = 1)
    private Long id;

    @Column(name = "profile_type")
    @Enumerated(EnumType.STRING)
    private ProfileTypeEnum profileType;

    @NotNull
    @Column(name = "partner_name")
    private String partnerName;

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Column(name = "fee")
    private int fee;

    @Column(name = "ref_link")
    private String refLink;

    @NotNull
    @Column(name = "url")
    private String url;

    @NotNull
    @Type(type = "jsonb")
    @Column(name = "requisites_data", columnDefinition = "jsonb")
    @Basic(fetch = LAZY)
    private RequisitesData requisitesData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfileTypeEnum getProfileType() {
        return profileType;
    }

    public void setProfileType(ProfileTypeEnum profileType) {
        this.profileType = profileType;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getRefLink() {
        return refLink;
    }

    public void setRefLink(String refLink) {
        this.refLink = refLink;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequisitesData getRequisitesData() {
        return requisitesData;
    }

    public void setRequisitesData(RequisitesData requisitesData) {
        this.requisitesData = requisitesData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProfile)) {
            return false;
        }
        return id != null && id.equals(((UserProfile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", profileType=" + profileType.getValue() +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", fee=" + fee +
                ", refLink='" + refLink + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
