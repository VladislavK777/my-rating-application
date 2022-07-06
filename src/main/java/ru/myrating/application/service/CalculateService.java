package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.myrating.application.domain.OrderResponse;
import ru.myrating.application.domain.catalog.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalculateService {
    private final Logger log = LoggerFactory.getLogger(CalculateService.class);
    private final CatalogService catalogService;

    public CalculateService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public Map<String, Object> calculateRatingModel(OrderResponse orderResponse) {
        log.info("calculate rating start");
        Map<String, Long> mapCalculatePoints = new HashMap<>();
        Map<String, Object> mapResultRating = new HashMap<>();

        Long e1 = calculateE1(orderResponse);
        Long e2 = calculateE2(orderResponse);

        // Если кредитная история пустая, нет необходимости высчитывать рейтинг
        if (e1 == 1L && e2 == 1L) {
            log.info("credit history is empty");
            CatRecommendationByEmptyHistory catRecommendationByEmptyHistory = catalogService.getRecommendationByEmptyHistory();
            mapResultRating.put("recBox1", catRecommendationByEmptyHistory.getRecBox1());
            mapResultRating.put("recBox2EmptyHistory", catRecommendationByEmptyHistory.getRecBox2());
            mapResultRating.put("recBox4EmptyHistory", catRecommendationByEmptyHistory.getRecBox4());
            mapResultRating.put("activeIpoteca", "Нет");
            mapResultRating.put("closeIpoteca", "Нет");
            mapResultRating.put("activePotrebCredit", "Нет");
            mapResultRating.put("closePotrebCredit", "Нет");
            mapResultRating.put("activeMFOCredit", "Нет");
            mapResultRating.put("closeMFOCredit", "Нет");
            mapResultRating.put("activeAutoCredit", "Нет");
            mapResultRating.put("closeAutoCredit", "Нет");
            mapResultRating.put("activeCreditCard", "Нет");
            mapResultRating.put("closeCreditCard", "Нет");
            mapResultRating.put("activeOtherCredit", "Нет");
            mapResultRating.put("closeOtherCredit", "Нет");
            mapResultRating.put("box2Header1", "Нет");
            mapResultRating.put("box2Header2", "Нет");
            mapResultRating.put("box2Header3", "Нет");
            return mapResultRating;
        }

        // Вычисляемые значения
        Long old = calculateOld(orderResponse.getOrderRequest().getOrderData().getBirthDate());
        CatOld catOld = catalogService.getCatOld(old);
        String d3 = calculateD3(e1, e2, orderResponse.getD1(), orderResponse.getD3());
        CatSumExistingCredit catSumExistingCreditA3 = catalogService.getCatSumExistingCredit(orderResponse.getA3().toString());
        CatCurrentDebtLoad catCurrentDebtLoadA4 = catalogService.getCatCurrentDebtLoad(orderResponse.getA4().toString());
        CatSumOverdueCredit catSumOverdueCreditD2 = catalogService.getCatSumOverdueCreditRepository(orderResponse.getD2().toString());
        Long a4CalculateValue = catCurrentDebtLoadA4.getCalculatedValue();
        Long a3CalculateValue = catSumExistingCreditA3.getCalculatedValue();
        Long d2CalculateValue = catSumOverdueCreditD2.getCalculatedValue();
        Long c = calculateC(a4CalculateValue, a3CalculateValue);
        Long d = calculateD(d2CalculateValue, a4CalculateValue);

        // Ищем остальные значения
        CatActiveAccount catActiveAccountA1 = catalogService.getCatActiveAccount(orderResponse.getA1().toString());
        CatActiveAccount catActiveAccountA2 = catalogService.getCatActiveAccount(orderResponse.getA2().toString());
        CatDelayPeriod catDelayPeriodD1 = catalogService.getDelayPeriod(orderResponse.getD1());
        CatDelayPeriod catDelayPeriodD3 = catalogService.getDelayPeriod(d3);
        CatRequestCreditHistory7Days catRequestCreditHistory7DaysI1 = catalogService.getCatRequestCreditHistory7Days(orderResponse.getI1());
        CatRequestCreditHistory14Days catRequestCreditHistory14DaysI2 = catalogService.getCatRequestCreditHistory14Days(orderResponse.getI2());
        CatAdditional catAdditionalA10 = orderResponse.getA10() == 1 ? catalogService.getCatAdditional("A10") : null;
        CatAdditional catAdditionalA11 = orderResponse.getA11() == 1 ? catalogService.getCatAdditional("A11") : null;
        CatAdditional catAdditionalAD4 = orderResponse.getD4() == 1 ? catalogService.getCatAdditional("D4") : null;
        CatC catC = catalogService.getCatC(c);
        CatD catD = catalogService.getCatD(d);

        mapCalculatePoints.put("old", calculateValue(catOld.getPoints(), catOld.getWeight()));
        mapCalculatePoints.put("i1", calculateValue(catRequestCreditHistory7DaysI1.getPoints(), catRequestCreditHistory7DaysI1.getWeight()));
        mapCalculatePoints.put("i2", calculateValue(catRequestCreditHistory14DaysI2.getPoints(), catRequestCreditHistory14DaysI2.getWeight()));
        mapCalculatePoints.put("a1", calculateValue(catActiveAccountA1.getPoints(), catActiveAccountA1.getWeight()));
        mapCalculatePoints.put("a2", calculateValue(catActiveAccountA2.getPoints(), catActiveAccountA2.getWeight()));
        if (catAdditionalA10 != null)
            mapCalculatePoints.put("a10", calculateValue(catAdditionalA10.getPoints(), catAdditionalA10.getWeight()));
        if (catAdditionalA11 != null)
            mapCalculatePoints.put("a11", calculateValue(catAdditionalA11.getPoints(), catAdditionalA11.getWeight()));
        mapCalculatePoints.put("d1", calculateValue(catDelayPeriodD1.getPoints(), catDelayPeriodD1.getWeight()));
        mapCalculatePoints.put("d2", calculateValue(catSumOverdueCreditD2.getPoints(), catSumOverdueCreditD2.getWeight()));
        mapCalculatePoints.put("d3", calculateValue(catDelayPeriodD3.getPoints(), catDelayPeriodD3.getWeight()));
        if (catAdditionalAD4 != null)
            mapCalculatePoints.put("d4", calculateValue(catAdditionalAD4.getPoints(), catAdditionalAD4.getWeight()));
        mapCalculatePoints.put("c", calculateValue(catC.getPoints(), catC.getWeight()));
        mapCalculatePoints.put("d", calculateValue(catD.getPoints(), catD.getWeight()));
        log.info("mapCalculatePoints: {}", mapCalculatePoints);

        // Считаем итоговый рейтинг
        Long result = 0L;
        for (Map.Entry<String, Long> values : mapCalculatePoints.entrySet()) {
            result = result + values.getValue();
        }
        CatSetting catSetting = catalogService.getCatSetting("basic_rate");
        Long resultRating = catSetting.getValue() + result;
        mapResultRating.put("rating", calculateRatingText(resultRating));

        // Ищем рекомендации по рейтингу
        CatRecommendationByRating catRecommendationByRating = catalogService.getCatRecommendationByRating(resultRating);
        mapResultRating.put("recBox1", catRecommendationByRating.getRecBox1());
        mapResultRating.put("recBox4Rating", catRecommendationByRating.getRecBox4());

        // Ищем рекомендации на основе данных из бюро
        calculateRecommendationActiveDebt(mapResultRating, orderResponse.getD1(), e1);
        calculateRecommendationCloseDebt(mapResultRating, d3, e2);
        calculateRecommendationBadDebt(mapResultRating, orderResponse.getD4());
        calculateRecommendationCategoryLoans(mapResultRating, orderResponse.getA10());
        calculateRecommendationRequestCredit(mapResultRating, orderResponse.getI1(), orderResponse.getI2());
        calculateRecommendationCreditLoad(mapResultRating, orderResponse.getA1(), orderResponse.getA2());

        calculateBox3(mapResultRating, orderResponse);
        calculateHeaders1Box2(mapResultRating, catActiveAccountA1);
        calculateHeaders2Box2(mapResultRating, orderResponse.getD1(), catSumExistingCreditA3, catSumOverdueCreditD2);
        calculateHeaders3Box2(mapResultRating, orderResponse.getD1(), catCurrentDebtLoadA4, catDelayPeriodD1);

        return mapResultRating;
    }

    private Long calculateOld(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return (long) Period.between(birthDate, currentDate).getYears();
    }

    private Long calculateValue(Long points, Long weight) {
        return (points * weight) / 100;
    }

    private Long calculateE1(OrderResponse orderResponse) {
        return (orderResponse.getA6() +
                orderResponse.getA8() +
                orderResponse.getA10() +
                orderResponse.getA12() +
                orderResponse.getA14() +
                orderResponse.getA16()) == 0 ? 1L : 0L;
    }

    private Long calculateE2(OrderResponse orderResponse) {
        return (orderResponse.getA7() +
                orderResponse.getA9() +
                orderResponse.getA11() +
                orderResponse.getA13() +
                orderResponse.getA15() +
                orderResponse.getA17()) == 0 ? 1L : 0L;
    }

    private String calculateD3(Long e1, Long e2, String d1, String d3) {
        return (e1 == 0L && e2 == 1L) || d1.equals(d3) ? "0" : d3;
    }

    private Long calculateC(Long a4CalculateValue, Long a3CalculateValue) {
        return (long) (((float) a4CalculateValue / a3CalculateValue) * 100);
    }

    private Long calculateD(Long d2CalculateValue, Long a4CalculateValue) {
        return (long) (((float) d2CalculateValue / a4CalculateValue) * 100);
    }

    private String calculateRatingText(Long rating) {
        return rating <= 450 ? "менее 450" : rating >= 900 ? "более 900" : rating.toString();
    }

    // Рекомендации активной просрочки
    private void calculateRecommendationActiveDebt(Map<String, Object> mapResultRating, String d1, Long e1) {
        CatRecommendationBySystem catRecommendationBySystem = null;
        if ((d1.equals("0") || d1.equals("1")) && e1 == 0L)
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("1");
        if ((d1.equals("2") || d1.equals("A") || d1.equals("B") || d1.equals("C")) && e1 == 0L)
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("2");
        if ((d1.equals("3") || d1.equals("4")) && e1 == 0L)
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("3");
        if ((d1.equals("5") || d1.equals("9")) && e1 == 0L)
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("4");
        if (catRecommendationBySystem != null) {
            mapResultRating.put("recBox2ActiveDebt", catRecommendationBySystem.getRecBox2());
            mapResultRating.put("recBox4ActiveDebt", catRecommendationBySystem.getRecBox4());
        }
    }

    // Рекомендации закрытой просрочки
    private void calculateRecommendationCloseDebt(Map<String, Object> mapResultRating, String d3, Long e2) {
        CatRecommendationBySystem catRecommendationBySystem = null;
        if ((d3.equals("0") || d3.equals("1")) && e2 == 0L)
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("5");
        if ((d3.equals("2") || d3.equals("3") || d3.equals("A") || d3.equals("B") || d3.equals("C")) && e2 == 0L)
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("6");
        if ((d3.equals("4") || d3.equals("5") || d3.equals("9")) && e2 == 0L)
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("7");
        if (catRecommendationBySystem != null) {
            mapResultRating.put("recBox2CloseDebt", catRecommendationBySystem.getRecBox2());
        }
    }

    // Рекомендации безнадежного долга
    private void calculateRecommendationBadDebt(Map<String, Object> mapResultRating, Long d4) {
        CatRecommendationBySystem catRecommendationBySystem;
        if (d4.equals(1L)) {
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("8");
            mapResultRating.put("recBox2BadDebt", catRecommendationBySystem.getRecBox2());
        }
    }

    // Рекомендации безнадежного долга
    private void calculateRecommendationCategoryLoans(Map<String, Object> mapResultRating, Long a10) {
        CatRecommendationBySystem catRecommendationBySystem;
        if (a10.equals(1L)) {
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("9");
            mapResultRating.put("recBox2CategoryLoans", catRecommendationBySystem.getRecBox2());
            mapResultRating.put("recBox4CategoryLoans", catRecommendationBySystem.getRecBox4());
        }
    }

    // Рекомендации на частые запросы на кредит
    private void calculateRecommendationRequestCredit(Map<String, Object> mapResultRating, Long i1, Long i2) {
        CatRecommendationBySystem catRecommendationBySystem;
        if (i1 > 10L || i2 > 10L) {
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("10");
            mapResultRating.put("recBox2RequestCredit", catRecommendationBySystem.getRecBox2());
            mapResultRating.put("recBox4RequestCredit", catRecommendationBySystem.getRecBox4());
        }
    }

    // Рекомендации по кредитной нагрузки  Если ("А1">= 4) активные кредиты  ИЛИ ("А1">= 3 И "А2">= 3) активные кредиты и поручительство
    private void calculateRecommendationCreditLoad(Map<String, Object> mapResultRating, Long a1, Long a2) {
        CatRecommendationBySystem catRecommendationBySystem;
        if (a1 >= 4L || (a1 >= 3L && a2 >= 3L)) {
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("11");
            mapResultRating.put("recBox2RequestCredit", catRecommendationBySystem.getRecBox2());
            mapResultRating.put("recBox4RequestCredit", catRecommendationBySystem.getRecBox4());
        }
    }

    // Расчет блока 3
    private void calculateBox3(Map<String, Object> mapResultRating, OrderResponse orderResponse) {
        // Ипотека
        if (orderResponse.getA6().equals(1L))
            mapResultRating.put("activeIpoteca", "Да");
        else
            mapResultRating.put("activeIpoteca", "Нет");
        if (orderResponse.getA7().equals(1L))
            mapResultRating.put("closeIpoteca", "Да");
        else
            mapResultRating.put("closeIpoteca", "Нет");

        // Потреб кредит
        if (orderResponse.getA8().equals(1L))
            mapResultRating.put("activePotrebCredit", "Да");
        else
            mapResultRating.put("activePotrebCredit", "Нет");

        if (orderResponse.getA9().equals(1L))
            mapResultRating.put("closePotrebCredit", "Да");
        else
            mapResultRating.put("closePotrebCredit", "Нет");

        // МФО
        if (orderResponse.getA10().equals(1L))
            mapResultRating.put("activeMFOCredit", "Да");
        else
            mapResultRating.put("activeMFOCredit", "Нет");

        if (orderResponse.getA11().equals(1L))
            mapResultRating.put("closeMFOCredit", "Да");
        else
            mapResultRating.put("closeMFOCredit", "Нет");

        // Автокредит
        if (orderResponse.getA12().equals(1L))
            mapResultRating.put("activeAutoCredit", "Да");
        else
            mapResultRating.put("activeAutoCredit", "Нет");

        if (orderResponse.getA13().equals(1L))
            mapResultRating.put("closeAutoCredit", "Да");
        else
            mapResultRating.put("closeAutoCredit", "Нет");

        // Кредит карта
        if (orderResponse.getA14().equals(1L))
            mapResultRating.put("activeCreditCard", "Да");
        else
            mapResultRating.put("activeCreditCard", "Нет");

        if (orderResponse.getA15().equals(1L))
            mapResultRating.put("closeCreditCard", "Да");
        else
            mapResultRating.put("closeCreditCard", "Нет");

        // Другие
        if (orderResponse.getA16().equals(1L))
            mapResultRating.put("activeOtherCredit", "Да");
        else
            mapResultRating.put("activeOtherCredit", "Нет");

        if (orderResponse.getA17().equals(1L))
            mapResultRating.put("closeOtherCredit", "Да");
        else
            mapResultRating.put("closeOtherCredit", "Нет");
    }

    // Расчет заголовков и значений в Блоке 2
    private void calculateHeaders1Box2(Map<String, Object> mapResultRating, CatActiveAccount catActiveAccountA1) {
        List<String> list = new ArrayList<>();
        list.add("Количество активных кредитов");
        list.add(catActiveAccountA1.getDescription());
        mapResultRating.put("box2Header1", list);
    }

    private void calculateHeaders2Box2(Map<String, Object> mapResultRating, String d1, CatSumExistingCredit catSumExistingCreditA3, CatSumOverdueCredit catSumOverdueCreditD2) {
        List<String> list = new ArrayList<>();
        if (d1.equals("0") || d1.equals("1")) {
            list.add("Сумма всех активных кредитов");
            list.add(catSumExistingCreditA3.getDescription());
        } else {
            list.add("Сумма просроченной задолженности");
            list.add(catSumOverdueCreditD2.getDescription());
        }
        mapResultRating.put("box2Header2", list);
    }

    private void calculateHeaders3Box2(Map<String, Object> mapResultRating, String d1, CatCurrentDebtLoad catCurrentDebtLoadA4, CatDelayPeriod catDelayPeriodD1) {
        List<String> list = new ArrayList<>();
        if (d1.equals("0") || d1.equals("1")) {
            list.add("Остаток долга по активным кредитам");
            list.add(catCurrentDebtLoadA4.getDescription());
        } else {
            list.add("Срок активной просрочки");
            list.add(catDelayPeriodD1.getDescription());
        }
        mapResultRating.put("box2Header3", list);
    }
}
