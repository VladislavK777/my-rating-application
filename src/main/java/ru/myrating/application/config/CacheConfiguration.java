package ru.myrating.application.config;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.myrating.application.repository.catalog.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(
                        CacheConfigurationBuilder
                                .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                                .build()
                );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, ru.myrating.application.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, ru.myrating.application.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, ru.myrating.application.domain.User.class.getName());
            createCache(cm, ru.myrating.application.domain.Authority.class.getName());
            createCache(cm, ru.myrating.application.domain.User.class.getName() + ".authorities");

            createCache(cm, ru.myrating.application.domain.catalog.CatActiveAccount.class.getName());
            createCache(cm, CatActiveAccountRepository.CAT_ACTIVE_ACCOUNT_CODE);

            createCache(cm, ru.myrating.application.domain.catalog.CatAdditional.class.getName());
            createCache(cm, CatAdditionalRepository.CAT_ADDITIONAL_CODE);

            createCache(cm, ru.myrating.application.domain.catalog.CatC.class.getName());
            createCache(cm, CatCRepository.CAT_С_BETWEEN);
            createCache(cm, CatCRepository.CAT_С_EQUALS);

            createCache(cm, ru.myrating.application.domain.catalog.CatD.class.getName());
            createCache(cm, CatDRepository.CAT_D_BETWEEN);
            createCache(cm, CatDRepository.CAT_D_EQUALS);

            createCache(cm, ru.myrating.application.domain.catalog.CatCurrentDebtLoad.class.getName());
            createCache(cm, CatCurrentDebtLoadRepository.CAT_CURRENT_DEBT_CODE);

            createCache(cm, ru.myrating.application.domain.catalog.CatDelayPeriod.class.getName());
            createCache(cm, CatDelayPeriodRepository.CAT_DELAY_PERIOD_CODE);

            createCache(cm, ru.myrating.application.domain.catalog.CatOld.class.getName());
            createCache(cm, CatOldRepository.CAT_OLD_BETWEEN);

            createCache(cm, ru.myrating.application.domain.catalog.CatRecommendationByRating.class.getName());
            createCache(cm, CatRecommendationByRatingRepository.CAT_RECOMMENDATION_RATING_BETWEEN);

            createCache(cm, ru.myrating.application.domain.catalog.CatRecommendationBySystem.class.getName());
            createCache(cm, CatRecommendationBySystemRepository.CAT_RECOMMENDATION_SYSTEM_CODE);

            createCache(cm, ru.myrating.application.domain.catalog.CatRecommendationByEmptyHistory.class.getName());
            createCache(cm, CatRecommendationByEmptyHistoryRepository.CAT_RECOMMENDATION_EMPTY_HISTORY);

            createCache(cm, ru.myrating.application.domain.catalog.CatRequestCreditHistory7Days.class.getName());
            createCache(cm, CatRequestCreditHistory7DaysRepository.CAT_HISTORY_7_DAYS_BETWEEN);
            createCache(cm, CatRequestCreditHistory7DaysRepository.CAT_HISTORY_7_DAYS_EQUALS);

            createCache(cm, ru.myrating.application.domain.catalog.CatRequestCreditHistory14Days.class.getName());
            createCache(cm, CatRequestCreditHistory14DaysRepository.CAT_HISTORY_14_DAYS_BETWEEN);
            createCache(cm, CatRequestCreditHistory14DaysRepository.CAT_HISTORY_14_DAYS_EQUALS);

            createCache(cm, ru.myrating.application.domain.catalog.CatSetting.class.getName());
            createCache(cm, CatSettingRepository.CAT_SETTING_CODE);

            createCache(cm, ru.myrating.application.domain.catalog.CatSumExistingCredit.class.getName());
            createCache(cm, CatSumExistingCreditRepository.CAT_SUM_EXISTING_CODE);

            createCache(cm, ru.myrating.application.domain.catalog.CatSumOverdueCredit.class.getName());
            createCache(cm, CatSumOverdueCreditRepository.CAT_SUM_OVERDUE_CODE);

            createCache(cm, ru.myrating.application.domain.catalog.CatYesNo.class.getName());
            createCache(cm, CatYesNoRepository.CAT_YES_NO_CODE);
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
