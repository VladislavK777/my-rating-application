package ru.myrating.application.domain.catalog;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cat_setting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CatSetting implements CatalogForSoftDelete {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private Long value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatSetting)) {
            return false;
        }
        return id != null && id.equals(((CatSetting) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CatSetting{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                '}';
    }
}
