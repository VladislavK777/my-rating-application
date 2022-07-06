package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.catalog.CatSumExistingCredit;

import java.util.Optional;

public interface CatSumExistingCreditRepository extends JpaRepository<CatSumExistingCredit, Long> {
    String CAT_SUM_EXISTING_CODE = "catSumExistingCredit";

    @Cacheable(cacheNames = CAT_SUM_EXISTING_CODE)
    Optional<CatSumExistingCredit> findByCode(String code);
}
