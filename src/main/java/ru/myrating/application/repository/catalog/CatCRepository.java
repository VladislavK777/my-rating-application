package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.myrating.application.domain.catalog.CatActiveAccount;
import ru.myrating.application.domain.catalog.CatC;

import java.util.Optional;

public interface CatCRepository extends JpaRepository<CatC, Long> {
    String CAT_С_BETWEEN = "catCBetween";

    @Cacheable(cacheNames = CAT_С_BETWEEN)
    @Query("from CatC c where c.from <= :value and c.to > :value")
    Optional<CatC> findByBetweenValue(@Param("value") Long value);
}
