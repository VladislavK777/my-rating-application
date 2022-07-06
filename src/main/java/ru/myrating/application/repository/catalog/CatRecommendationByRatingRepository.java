package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.myrating.application.domain.catalog.CatC;
import ru.myrating.application.domain.catalog.CatRecommendationByRating;

import java.util.Optional;

public interface CatRecommendationByRatingRepository extends JpaRepository<CatRecommendationByRating, Long> {
    String CAT_RECOMMENTDATION_RATING_BETWEEN = "catRecommendationByRating";

    @Cacheable(cacheNames = CAT_RECOMMENTDATION_RATING_BETWEEN)
    @Query("from CatRecommendationByRating c where c.from <= :value and c.to > :value")
    Optional<CatRecommendationByRating> findByBetweenValue(@Param("value") Long value);
}
