package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.catalog.CatRecommendationBySystem;

import java.util.Optional;

public interface CatRecommendationBySystemRepository extends JpaRepository<CatRecommendationBySystem, Long> {
    String CAT_RECOMMENDATION_SYSTEM_CODE = "catRecommendationBySystemCode";

    @Cacheable(cacheNames = CAT_RECOMMENDATION_SYSTEM_CODE)
    Optional<CatRecommendationBySystem> findByCode(String code);
}
