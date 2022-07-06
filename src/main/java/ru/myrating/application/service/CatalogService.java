package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.catalog.*;
import ru.myrating.application.repository.catalog.*;
import ru.myrating.application.web.rest.errors.BadRequestAlertException;

import java.util.List;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
@Transactional(readOnly = true)
public class CatalogService {
    private final Logger log = LoggerFactory.getLogger(CatalogService.class);

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


    public CatalogService(CatOldRepository catOldRepository, CatCurrentDebtLoadRepository catCurrentDebtLoadRepository, CatSumExistingCreditRepository catSumExistingCreditRepository, CatSumOverdueCreditRepository catSumOverdueCreditRepository, CatYesNoRepository catYesNoRepository, CatSettingRepository catSettingRepository, CatActiveAccountRepository catActiveAccountRepository, CatDelayPeriodRepository catDelayPeriodRepository, CatRequestCreditHistory7DaysRepository catRequestCreditHistory7DaysRepository, CatRequestCreditHistory14DaysRepository catRequestCreditHistory14DaysRepository, CatAdditionalRepository catAdditionalRepository, CatCRepository catCRepository, CatDRepository catDRepository, CatRecommendationByRatingRepository catRecommendationByRatingRepository, CatRecommendationByEmptyHistoryRepository catRecommendationByEmptyHistoryRepository, CatRecommendationBySystemRepository catRecommendationBySystemRepository) {
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
        return catOldRepository.findByBetweenValue(value).orElseThrow(() -> new BadRequestAlertException("Value in CatOld not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatRequestCreditHistory14Days getCatRequestCreditHistory14Days(Long value) {
        return catRequestCreditHistory14DaysRepository.findByBetweenValue(value).orElseThrow(() -> new BadRequestAlertException("Value in CatRequestCreditHistory14Days not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatRequestCreditHistory7Days getCatRequestCreditHistory7Days(Long value) {
        return catRequestCreditHistory7DaysRepository.findByBetweenValue(value).orElseThrow(() -> new BadRequestAlertException("Value in CatRequestCreditHistory7Days not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatC getCatC(Long value) {
        return catCRepository.findByBetweenValue(value).orElseThrow(() -> new BadRequestAlertException("Value in CatC not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatD getCatD(Long value) {
        return catDRepository.findByBetweenValue(value).orElseThrow(() -> new BadRequestAlertException("Value in CatD not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatRecommendationByRating getCatRecommendationByRating(Long value) {
        return catRecommendationByRatingRepository.findByBetweenValue(value).orElseThrow(() -> new BadRequestAlertException("Value in CatRecommendationByRating not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatCurrentDebtLoad getCatCurrentDebtLoad(String value) {
        return catCurrentDebtLoadRepository.findByCode(value).orElseThrow(() -> new BadRequestAlertException("Value in CatCurrentDebtLoad not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatSumExistingCredit getCatSumExistingCredit(String value) {
        return catSumExistingCreditRepository.findByCode(value).orElseThrow(() -> new BadRequestAlertException("Value in CatSumExistingCredit not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatSumOverdueCredit getCatSumOverdueCreditRepository(String value) {
        return catSumOverdueCreditRepository.findByCode(value).orElseThrow(() -> new BadRequestAlertException("Value in CatSumOverdueCreditRepository not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatYesNo getCatYesNo(String value) {
        return catYesNoRepository.findByCode(value).orElseThrow(() -> new BadRequestAlertException("Value in CatYesNo not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatSetting getCatSetting(String value) {
        return catSettingRepository.findByCode(value).orElseThrow(() -> new BadRequestAlertException("Value in CatSetting not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatActiveAccount getCatActiveAccount(String value) {
        return catActiveAccountRepository.findByCode(value).orElseThrow(() -> new BadRequestAlertException("Value in CatActiveAccount not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatDelayPeriod getDelayPeriod(String value) {
        return catDelayPeriodRepository.findByCode(value).orElseThrow(() -> new BadRequestAlertException("Value in CatDelayPeriod not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatAdditional getCatAdditional(String value) {
        return catAdditionalRepository.findByCode(value).orElseThrow(() -> new BadRequestAlertException("Value in CatAdditional not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatRecommendationBySystem getCatRecommendationBySystem(String value) {
        return catRecommendationBySystemRepository.findByCode(value).orElseThrow(() -> new BadRequestAlertException("Value in CatRecommendationBySystem not found", ENTITY_NAME, "valuenotfoundincatalog"));
    }

    public CatRecommendationByEmptyHistory getRecommendationByEmptyHistory() {
        List<CatRecommendationByEmptyHistory> list = catRecommendationByEmptyHistoryRepository.findAll();
        if (list.isEmpty())
            throw new BadRequestAlertException("Value in CatDelayPeriod not found", ENTITY_NAME, "valuenotfoundincatalog");
        return list.get(0);
    }

}
