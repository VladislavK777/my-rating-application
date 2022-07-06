package ru.myrating.application.domain.catalog;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cat_recommendation_by_system")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CatRecommendationBySystem implements CatalogForSoftDelete {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "rec_box_2")
    private String recBox2;

    @Column(name = "rec_box_4")
    private String recBox4;

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

    public String getRecBox2() {
        return recBox2;
    }

    public void setRecBox2(String recBox2) {
        this.recBox2 = recBox2;
    }

    public String getRecBox4() {
        return recBox4;
    }

    public void setRecBox4(String recBox4) {
        this.recBox4 = recBox4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatRecommendationBySystem)) {
            return false;
        }
        return id != null && id.equals(((CatRecommendationBySystem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CatRecommendationBySystem{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", recBox2='" + recBox2 + '\'' +
                ", recBox4='" + recBox4 + '\'' +
                '}';
    }
}
