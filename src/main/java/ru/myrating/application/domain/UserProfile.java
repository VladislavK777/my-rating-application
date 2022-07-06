package ru.myrating.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ru.myrating.application.domain.enumeration.ProfileTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity
@Table(name = "user_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserProfile extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_generator")
    @SequenceGenerator(name = "user_profile_generator", sequenceName = "user_profile_generator")
    private Long id;

    @Column(name = "profile_type")
    @Enumerated(EnumType.STRING)
    private ProfileTypeEnum profileType;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "fee")
    private int fee;

    @Column(name = "ref_link")
    private String refLink;

    @OneToOne
    @JoinColumn(name = "requisites_id")
    private Requisites requisites;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Requisites getRequisites() {
        return requisites;
    }

    public void setRequisites(Requisites requisites) {
        this.requisites = requisites;
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
                ", profileType=" + profileType +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", fee=" + fee +
                ", refLink='" + refLink + '\'' +
                '}';
    }
}
