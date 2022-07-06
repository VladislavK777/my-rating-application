package ru.myrating.application.domain.catalog;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cat_delay_period")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CatDelayPeriod implements CatalogForSoftDelete {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "points")
    private Long points;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "calculated_value")
    private Long calculatedValue;

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

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getCalculatedValue() {
        return calculatedValue;
    }

    public void setCalculatedValue(Long calculatedValue) {
        this.calculatedValue = calculatedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatDelayPeriod)) {
            return false;
        }
        return id != null && id.equals(((CatDelayPeriod) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CatDelayPeriod{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", points=" + points +
                ", weight=" + weight +
                ", calculatedValue=" + calculatedValue +
                '}';
    }
}
