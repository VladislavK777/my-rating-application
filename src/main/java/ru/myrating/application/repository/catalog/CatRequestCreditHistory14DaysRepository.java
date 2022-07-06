package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.myrating.application.domain.catalog.CatRequestCreditHistory14Days;

import java.util.Optional;

public interface CatRequestCreditHistory14DaysRepository extends JpaRepository<CatRequestCreditHistory14Days, Long> {
    String CAT_HISTORY_14_DAYS_BETWEEN = "catRequestCreditHistory14Days";

    @Cacheable(cacheNames = CAT_HISTORY_14_DAYS_BETWEEN)
    @Query("from CatRequestCreditHistory14Days c where c.from <= :value and c.to > :value")
    Optional<CatRequestCreditHistory14Days> findByBetweenValue(@Param("value") Long value);
}
