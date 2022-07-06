package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.catalog.CatAdditional;

import java.util.Optional;

public interface CatAdditionalRepository extends JpaRepository<CatAdditional, Long> {
    String CAT_ADDITIONAL_CODE = "catAdditionalCode";

    @Cacheable(cacheNames = CAT_ADDITIONAL_CODE)
    Optional<CatAdditional> findByCode(String code);
}
