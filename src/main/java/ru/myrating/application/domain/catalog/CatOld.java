package ru.myrating.application.domain.catalog;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cat_old")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CatOld implements CatalogForSoftDelete {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "from")
    private Long from;

    @Column(name = "to")
    private Long to;

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
        if (!(o instanceof CatOld)) {
            return false;
        }
        return id != null && id.equals(((CatOld) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CatOld{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", description='" + description + '\'' +
                ", points=" + points +
                ", weight=" + weight +
                ", calculatedValue=" + calculatedValue +
                '}';
    }
}
