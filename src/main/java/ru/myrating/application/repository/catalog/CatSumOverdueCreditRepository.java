package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.catalog.CatSumOverdueCredit;

import java.util.Optional;

public interface CatSumOverdueCreditRepository extends JpaRepository<CatSumOverdueCredit, Long> {
    String CAT_SUM_OVERDUE_CODE = "catSumOverdueCredit";

    @Cacheable(cacheNames = CAT_SUM_OVERDUE_CODE)
    Optional<CatSumOverdueCredit> findByCode(String code);
}
