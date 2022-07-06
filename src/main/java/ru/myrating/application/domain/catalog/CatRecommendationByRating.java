package ru.myrating.application.domain.catalog;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cat_recommendation_by_rating")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CatRecommendationByRating implements CatalogForSoftDelete {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "from")
    private Long from;

    @Column(name = "to")
    private Long to;

    @Column(name = "rec_box_1")
    private String recBox1;

    @Column(name = "rec_box_4")
    private String recBox4;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getRecBox1() {
        return recBox1;
    }

    public void setRecBox1(String recBox1) {
        this.recBox1 = recBox1;
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
        if (!(o instanceof CatRecommendationByRating)) {
            return false;
        }
        return id != null && id.equals(((CatRecommendationByRating) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CatRecommendationByRating{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", recBox1='" + recBox1 + '\'' +
                ", recBox4='" + recBox4 + '\'' +
                '}';
    }
}
