package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.catalog.CatYesNo;

import java.util.Optional;

public interface CatYesNoRepository extends JpaRepository<CatYesNo, Long> {
    String CAT_YES_NO_CODE = "catYesNo";

    @Cacheable(cacheNames = CAT_YES_NO_CODE)
    Optional<CatYesNo> findByCode(String code);
}
