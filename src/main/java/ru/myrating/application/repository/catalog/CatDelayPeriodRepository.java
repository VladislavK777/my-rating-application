package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.catalog.CatDelayPeriod;

import java.util.Optional;

public interface CatDelayPeriodRepository extends JpaRepository<CatDelayPeriod, Long> {
    String CAT_DELAY_PERIOD_CODE = "catDelayPeriodCode";

    @Cacheable(cacheNames = CAT_DELAY_PERIOD_CODE)
    Optional<CatDelayPeriod> findByCode(String code);
}
