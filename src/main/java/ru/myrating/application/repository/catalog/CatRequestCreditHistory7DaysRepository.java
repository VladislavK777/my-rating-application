package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.myrating.application.domain.catalog.CatRequestCreditHistory7Days;

import java.util.Optional;

public interface CatRequestCreditHistory7DaysRepository extends JpaRepository<CatRequestCreditHistory7Days, Long> {
    String CAT_HISTORY_7_DAYS_BETWEEN = "catRequestCreditHistory7Days";

    @Cacheable(cacheNames = CAT_HISTORY_7_DAYS_BETWEEN)
    @Query("from CatRequestCreditHistory7Days c where c.from <= :value and c.to > :value")
    Optional<CatRequestCreditHistory7Days> findByBetweenValue(@Param("value") Long value);
}
