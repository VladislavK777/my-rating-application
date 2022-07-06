package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.catalog.CatSetting;

import java.util.Optional;

public interface CatSettingRepository extends JpaRepository<CatSetting, Long> {
    String CAT_SETTING_CODE = "catSettingCode";

    @Cacheable(cacheNames = CAT_SETTING_CODE)
    Optional<CatSetting> findByCode(String code);
}
