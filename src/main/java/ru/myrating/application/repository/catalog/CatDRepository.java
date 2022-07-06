package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.myrating.application.domain.catalog.CatD;

import java.util.Optional;

public interface CatDRepository extends JpaRepository<CatD, Long> {
    String CAT_D_BETWEEN = "catDBetween";

    @Cacheable(cacheNames = CAT_D_BETWEEN)
    @Query("from CatD c where c.from <= :value and c.to > :value")
    Optional<CatD> findByBetweenValue(@Param("value") Long value);
}
