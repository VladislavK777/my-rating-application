package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.catalog.CatRecommendationByEmptyHistory;

import java.util.List;


public interface CatRecommendationByEmptyHistoryRepository extends JpaRepository<CatRecommendationByEmptyHistory, Long> {
    String CAT_RECOMMENDATION_EMPTY_HISTORY = "catRecommendationByEmptyHistory";

    @Cacheable(cacheNames = CAT_RECOMMENDATION_EMPTY_HISTORY)
    @Override
    List<CatRecommendationByEmptyHistory> findAll();
}
