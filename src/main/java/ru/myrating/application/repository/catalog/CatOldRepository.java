package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.myrating.application.domain.catalog.CatOld;

import java.util.Optional;

public interface CatOldRepository extends JpaRepository<CatOld, Long> {
    String CAT_OLD_BETWEEN = "catOldBetween";
    String CAT_OLD_EQUALS = "catOldEquals";

    @Cacheable(cacheNames = CAT_OLD_BETWEEN)
    @Query("from CatOld c where c.from < :value and c.to >= :value")
    Optional<CatOld> findByBetweenValue(@Param("value") Long value);

    @Cacheable(cacheNames = CAT_OLD_EQUALS)
    @Query("from CatOld c where c.from = :value and c.to = :value")
    Optional<CatOld> findByEqualsValue(@Param("value") Long value);
}
