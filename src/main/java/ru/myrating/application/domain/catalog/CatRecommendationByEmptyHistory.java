package ru.myrating.application.domain.catalog;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cat_recommendation_by_empty_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CatRecommendationByEmptyHistory implements CatalogForSoftDelete {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "rec_box_1")
    private String recBox1;

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

    public String getRecBox1() {
        return recBox1;
    }

    public void setRecBox1(String recBox1) {
        this.recBox1 = recBox1;
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
        if (!(o instanceof CatRecommendationByEmptyHistory)) {
            return false;
        }
        return id != null && id.equals(((CatRecommendationByEmptyHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CatRecommendationByEmptyHistory{" +
                "id=" + id +
                ", recBox1='" + recBox1 + '\'' +
                ", recBox2='" + recBox2 + '\'' +
                ", recBox4='" + recBox4 + '\'' +
                '}';
    }
}
