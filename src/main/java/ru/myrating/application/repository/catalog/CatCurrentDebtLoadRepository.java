package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.catalog.CatCurrentDebtLoad;

import java.util.Optional;

public interface CatCurrentDebtLoadRepository extends JpaRepository<CatCurrentDebtLoad, Long> {
    String CAT_CURRENT_DEBT_CODE = "catCurrentDebtLoadCode";

    @Cacheable(cacheNames = CAT_CURRENT_DEBT_CODE)
    Optional<CatCurrentDebtLoad> findByCode(String code);
}
