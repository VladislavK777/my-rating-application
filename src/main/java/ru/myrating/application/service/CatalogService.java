package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.catalog.*;
import ru.myrating.application.repository.catalog.*;
import ru.myrating.application.web.rest.errors.NotFoundAlertException;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class CatalogService {
    private final Logger log = LoggerFactory.getLogger(CatalogService.class);

    private final CacheManager cacheManager;
    private final CatOldRepository catOldRepository;
    private final CatCurrentDebtLoadRepository catCurrentDebtLoadRepository;
    private final CatSumExistingCreditRepository catSumExistingCreditRepository;
    private final CatSumOverdueCreditRepository catSumOverdueCreditRepository;
    private final CatYesNoRepository catYesNoRepository;
    private final CatSettingRepository catSettingRepository;
    private final CatActiveAccountRepository catActiveAccountRepository;
    private final CatDelayPeriodRepository catDelayPeriodRepository;
    private final CatRequestCreditHistory7DaysRepository catRequestCreditHistory7DaysRepository;
    private final CatRequestCreditHistory14DaysRepository catRequestCreditHistory14DaysRepository;
    private final CatAdditionalRepository catAdditionalRepository;
    private final CatCRepository catCRepository;
    private final CatDRepository catDRepository;
    private final CatRecommendationByRatingRepository catRecommendationByRatingRepository;
    private final CatRecommendationByEmptyHistoryRepository catRecommendationByEmptyHistoryRepository;
    private final CatRecommendationBySystemRepository catRecommendationBySystemRepository;


    public CatalogService(CacheManager cacheManager, CatOldRepository catOldRepository, CatCurrentDebtLoadRepository catCurrentDebtLoadRepository, CatSumExistingCreditRepository catSumExistingCreditRepository, CatSumOverdueCreditRepository catSumOverdueCreditRepository, CatYesNoRepository catYesNoRepository, CatSettingRepository catSettingRepository, CatActiveAccountRepository catActiveAccountRepository, CatDelayPeriodRepository catDelayPeriodRepository, CatRequestCreditHistory7DaysRepository catRequestCreditHistory7DaysRepository, CatRequestCreditHistory14DaysRepository catRequestCreditHistory14DaysRepository, CatAdditionalRepository catAdditionalRepository, CatCRepository catCRepository, CatDRepository catDRepository, CatRecommendationByRatingRepository catRecommendationByRatingRepository, CatRecommendationByEmptyHistoryRepository catRecommendationByEmptyHistoryRepository, CatRecommendationBySystemRepository catRecommendationBySystemRepository) {
        this.cacheManager = cacheManager;
        this.catOldRepository = catOldRepository;
        this.catCurrentDebtLoadRepository = catCurrentDebtLoadRepository;
        this.catSumExistingCreditRepository = catSumExistingCreditRepository;
        this.catSumOverdueCreditRepository = catSumOverdueCreditRepository;
        this.catYesNoRepository = catYesNoRepository;
        this.catSettingRepository = catSettingRepository;
        this.catActiveAccountRepository = catActiveAccountRepository;
        this.catDelayPeriodRepository = catDelayPeriodRepository;
        this.catRequestCreditHistory7DaysRepository = catRequestCreditHistory7DaysRepository;
        this.catRequestCreditHistory14DaysRepository = catRequestCreditHistory14DaysRepository;
        this.catAdditionalRepository = catAdditionalRepository;
        this.catCRepository = catCRepository;
        this.catDRepository = catDRepository;
        this.catRecommendationByRatingRepository = catRecommendationByRatingRepository;
        this.catRecommendationByEmptyHistoryRepository = catRecommendationByEmptyHistoryRepository;
        this.catRecommendationBySystemRepository = catRecommendationBySystemRepository;
    }

    public CatOld getCatOld(Long value) {
        return catOldRepository.findByBetweenValue(value).orElseThrow(() -> new NotFoundAlertException("Value in CatOld not found", "catOld", "valuenotfoundincatalog"));
    }

    public CatRequestCreditHistory14Days getCatRequestCreditHistory14Days(Long value) {
        if (value == 0L)
            return catRequestCreditHistory14DaysRepository.findByEqualsValue(value).orElseThrow(() -> new NotFoundAlertException("Value in CatRequestCreditHistory14Days not found", "catRequestCreditHistory14Days", "valuenotfoundincatalog"));
        return catRequestCreditHistory14DaysRepository.findByBetweenValue(value).orElseThrow(() -> new NotFoundAlertException("Value in CatRequestCreditHistory14Days not found", "catRequestCreditHistory14Days", "valuenotfoundincatalog"));
    }

    public CatRequestCreditHistory7Days getCatRequestCreditHistory7Days(Long value) {
        if (value == 0L)
            return catRequestCreditHistory7DaysRepository.findByEqualsValue(value).orElseThrow(() -> new NotFoundAlertException("Value in CatRequestCreditHistory7Days not found", "catRequestCreditHistory7Days", "valuenotfoundincatalog"));
        return catRequestCreditHistory7DaysRepository.findByBetweenValue(value).orElseThrow(() -> new NotFoundAlertException("Value in CatRequestCreditHistory7Days not found", "catRequestCreditHistory7Days", "valuenotfoundincatalog"));
    }

    public CatC getCatC(Long value) {
        if (value == 0L || value == 100L)
            return catCRepository.findByEqualsValue(value).orElseThrow(() -> new NotFoundAlertException("Value in CatC not found", "catC", "valuenotfoundincatalog"));
        return catCRepository.findByBetweenValue(value).orElseThrow(() -> new NotFoundAlertException("Value in CatC not found", "catC", "valuenotfoundincatalog"));
    }

    public CatD getCatD(Long value) {
        if (value == 0L || value == 100L)
            return catDRepository.findByEqualsValue(value).orElseThrow(() -> new NotFoundAlertException("Value in CatD not found", "catD", "valuenotfoundincatalog"));
        return catDRepository.findByBetweenValue(value).orElseThrow(() -> new NotFoundAlertException("Value in CatD not found", "catD", "valuenotfoundincatalog"));
    }

    public CatRecommendationByRating getCatRecommendationByRating(Long value) {
        return catRecommendationByRatingRepository.findByBetweenValue(value).orElseThrow(() -> new NotFoundAlertException("Value in CatRecommendationByRating not found", "catRecommendationByRating", "valuenotfoundincatalog"));
    }

    public CatCurrentDebtLoad getCatCurrentDebtLoad(String value) {
        return catCurrentDebtLoadRepository.findByCode(value).orElseThrow(() -> new NotFoundAlertException("Value in CatCurrentDebtLoad not found", "catCurrentDebtLoad", "valuenotfoundincatalog"));
    }

    public CatSumExistingCredit getCatSumExistingCredit(String value) {
        return catSumExistingCreditRepository.findByCode(value).orElseThrow(() -> new NotFoundAlertException("Value in CatSumExistingCredit not found", "catSumExistingCredit", "valuenotfoundincatalog"));
    }

    public CatSumOverdueCredit getCatSumOverdueCreditRepository(String value) {
        return catSumOverdueCreditRepository.findByCode(value).orElseThrow(() -> new NotFoundAlertException("Value in CatSumOverdueCreditRepository not found", "catSumOverdueCreditRepository", "valuenotfoundincatalog"));
    }

    public CatYesNo getCatYesNo(String value) {
        return catYesNoRepository.findByCode(value).orElseThrow(() -> new NotFoundAlertException("Value in CatYesNo not found", "catYesNo", "valuenotfoundincatalog"));
    }

    public CatSetting getCatSetting(String value) {
        return catSettingRepository.findByCode(value).orElseThrow(() -> new NotFoundAlertException("Value in CatSetting not found", "catSetting", "valuenotfoundincatalog"));
    }

    public CatActiveAccount getCatActiveAccount(String value) {
        return catActiveAccountRepository.findByCode(value).orElseThrow(() -> new NotFoundAlertException("Value in CatActiveAccount not found", "catActiveAccount", "valuenotfoundincatalog"));
    }

    public CatDelayPeriod getDelayPeriod(String value) {
        return catDelayPeriodRepository.findByCode(value).orElseThrow(() -> new NotFoundAlertException("Value in CatDelayPeriod not found", "catDelayPeriod", "valuenotfoundincatalog"));
    }

    public CatAdditional getCatAdditional(String value) {
        return catAdditionalRepository.findByCode(value).orElseThrow(() -> new NotFoundAlertException("Value in CatAdditional not found", "catAdditional", "valuenotfoundincatalog"));
    }

    public CatRecommendationBySystem getCatRecommendationBySystem(String value) {
        return catRecommendationBySystemRepository.findByCode(value).orElseThrow(() -> new NotFoundAlertException("Value in CatRecommendationBySystem not found", "catRecommendationBySystem", "valuenotfoundincatalog"));
    }

    public CatRecommendationByEmptyHistory getRecommendationByEmptyHistory() {
        List<CatRecommendationByEmptyHistory> list = catRecommendationByEmptyHistoryRepository.findAll();
        if (list.isEmpty())
            throw new NotFoundAlertException("Value in CatRecommendationByEmptyHistory not found", "catRecommendationByEmptyHistory", "valuenotfoundincatalog");
        return list.get(0);
    }

    public void clearCaches() {
        Objects.requireNonNull(cacheManager.getCache(CatActiveAccountRepository.CAT_ACTIVE_ACCOUNT_CODE)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatAdditionalRepository.CAT_ADDITIONAL_CODE)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatCRepository.CAT_С_BETWEEN)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatCRepository.CAT_С_EQUALS)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatDRepository.CAT_D_BETWEEN)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatDRepository.CAT_D_EQUALS)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatCurrentDebtLoadRepository.CAT_CURRENT_DEBT_CODE)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatDelayPeriodRepository.CAT_DELAY_PERIOD_CODE)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatOldRepository.CAT_OLD_BETWEEN)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatRecommendationByRatingRepository.CAT_RECOMMENDATION_RATING_BETWEEN)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatRecommendationBySystemRepository.CAT_RECOMMENDATION_SYSTEM_CODE)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatRecommendationByEmptyHistoryRepository.CAT_RECOMMENDATION_EMPTY_HISTORY)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatRequestCreditHistory7DaysRepository.CAT_HISTORY_7_DAYS_BETWEEN)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatRequestCreditHistory7DaysRepository.CAT_HISTORY_7_DAYS_EQUALS)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatRequestCreditHistory14DaysRepository.CAT_HISTORY_14_DAYS_BETWEEN)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatRequestCreditHistory14DaysRepository.CAT_HISTORY_14_DAYS_EQUALS)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatSettingRepository.CAT_SETTING_CODE)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatSumExistingCreditRepository.CAT_SUM_EXISTING_CODE)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatSumOverdueCreditRepository.CAT_SUM_OVERDUE_CODE)).clear();
        Objects.requireNonNull(cacheManager.getCache(CatYesNoRepository.CAT_YES_NO_CODE)).clear();
    }

}
