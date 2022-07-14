package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.myrating.application.domain.OrderResponse;
import ru.myrating.application.domain.catalog.*;
import ru.myrating.application.service.dto.OpenCloseDto;
import ru.myrating.application.service.dto.TitleValueDto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.List.of;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

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
        String name = orderResponse.getOrderRequest().getOrderData().getLastName() + " " + orderResponse.getOrderRequest().getOrderData().getFirstName().charAt(0) + ".";

        // Если кредитная история пустая, нет необходимости высчитывать рейтинг
        if (e1 == 1L && e2 == 1L) {
            log.info("credit history is empty");
            CatRecommendationByEmptyHistory catRecommendationByEmptyHistory = catalogService.getRecommendationByEmptyHistory();
            mapResultRating.put("box1", of(
                    new TitleValueDto("clientName", name),
                    new TitleValueDto("ratingVale", null),
                    new TitleValueDto("ratingText", null),
                    new TitleValueDto("ratingComment", catRecommendationByEmptyHistory.getRecBox1())));
            mapResultRating.put("box2", new Box2Object(
                    new TitleValueDto("Количество активных кредитов", "Нет"),
                    new TitleValueDto("Сумма всех активных кредитов", "Нет"),
                    new TitleValueDto("Остаток долга по активным кредитам", "Нет"),
                    of(new TitleValueDto("emptyHistory", catRecommendationByEmptyHistory.getRecBox2()))));
            mapResultRating.put("box3", new Box3Object(
                    new OpenCloseDto(false, false),
                    new OpenCloseDto(false, false),
                    new OpenCloseDto(false, false),
                    new OpenCloseDto(false, false),
                    new OpenCloseDto(false, false),
                    new OpenCloseDto(false, false)));
            mapResultRating.put("box4", of(
                    new TitleValueDto("emptyHistory", catRecommendationByEmptyHistory.getRecBox4())));
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
        mapCalculatePoints.put("d3", (long) (((float) calculateValue(catDelayPeriodD3.getPoints(), catDelayPeriodD3.getWeight())) * 0.8));
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

        // Ищем рекомендации по рейтингу
        CatRecommendationByRating catRecommendationByRating = catalogService.getCatRecommendationByRating(resultRating <= 0 ? 0L : resultRating);
        mapResultRating.put("box1", of(
                new TitleValueDto("clientName", name),
                new TitleValueDto("ratingVale", resultRating),
                new TitleValueDto("ratingText", calculateRatingText(resultRating)),
                new TitleValueDto("ratingComment", catRecommendationByRating.getRecBox1())));
        mapResultRating.put("box2", new Box2Object());
        mapResultRating.put("box4", new ArrayList<TitleValueDto>());
        mapResultRating.put("box4", setCommentToBox4(mapResultRating, new TitleValueDto("ratingComment", catRecommendationByRating.getRecBox4())));

        // Ищем рекомендации на основе данных из бюро
        calculateRecommendationActiveDebt(mapResultRating, orderResponse.getD1(), e1);
        calculateRecommendationCloseDebt(mapResultRating, d3, e2);
        calculateRecommendationBadDebt(mapResultRating, orderResponse.getD4());
        calculateRecommendationMfoCredit(mapResultRating, orderResponse.getA10());
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
        if (a3CalculateValue == 0L) return 0L;
        return (long) (((float) a4CalculateValue / a3CalculateValue) * 100);
    }

    private Long calculateD(Long d2CalculateValue, Long a4CalculateValue) {
        if (a4CalculateValue == 0L) return 0L;
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
            mapResultRating.put("box2", setCommentToBox2(mapResultRating, new TitleValueDto("activeDebt", catRecommendationBySystem.getRecBox2())));
            if (isNotEmpty(catRecommendationBySystem.getRecBox4())) {
                mapResultRating.put("box4", setCommentToBox4(mapResultRating, new TitleValueDto("activeDebt", catRecommendationBySystem.getRecBox4())));
            }
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
            mapResultRating.put("box2", setCommentToBox2(mapResultRating, new TitleValueDto("closeDebt", catRecommendationBySystem.getRecBox2())));
        }
    }

    // Рекомендации безнадежного долга
    private void calculateRecommendationBadDebt(Map<String, Object> mapResultRating, Long d4) {
        CatRecommendationBySystem catRecommendationBySystem;
        if (d4.equals(1L)) {
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("8");
            mapResultRating.put("box2", setCommentToBox2(mapResultRating, new TitleValueDto("badDebt", catRecommendationBySystem.getRecBox2())));
        }
    }

    // Рекомендации займов МФО
    private void calculateRecommendationMfoCredit(Map<String, Object> mapResultRating, Long a10) {
        CatRecommendationBySystem catRecommendationBySystem;
        if (a10.equals(1L)) {
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("9");
            mapResultRating.put("box2", setCommentToBox2(mapResultRating, new TitleValueDto("mfoCredit", catRecommendationBySystem.getRecBox2())));
            mapResultRating.put("box4", setCommentToBox4(mapResultRating, new TitleValueDto("mfoCredit", catRecommendationBySystem.getRecBox4())));
        }
    }

    // Рекомендации на частые запросы на кредит
    private void calculateRecommendationRequestCredit(Map<String, Object> mapResultRating, Long i1, Long i2) {
        CatRecommendationBySystem catRecommendationBySystem;
        if (i1 > 10L || i2 > 10L) {
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("10");
            mapResultRating.put("box2", setCommentToBox2(mapResultRating, new TitleValueDto("requestCredit", catRecommendationBySystem.getRecBox2())));
            mapResultRating.put("box4", setCommentToBox4(mapResultRating, new TitleValueDto("requestCredit", catRecommendationBySystem.getRecBox4())));
        }
    }

    // Рекомендации по кредитной нагрузки
    private void calculateRecommendationCreditLoad(Map<String, Object> mapResultRating, Long a1, Long a2) {
        CatRecommendationBySystem catRecommendationBySystem;
        if (a1 >= 4L || (a1 >= 3L && a2 >= 3L)) {
            catRecommendationBySystem = catalogService.getCatRecommendationBySystem("11");
            mapResultRating.put("box2", setCommentToBox2(mapResultRating, new TitleValueDto("creditLoad", catRecommendationBySystem.getRecBox2())));
            mapResultRating.put("box4", setCommentToBox4(mapResultRating, new TitleValueDto("creditLoad", catRecommendationBySystem.getRecBox4())));
        }
    }

    // Расчет блока 3
    private void calculateBox3(Map<String, Object> mapResultRating, OrderResponse orderResponse) {
        Box3Object box3Object = new Box3Object();
        box3Object.setIpotecaCredit(new OpenCloseDto(orderResponse.getA6().equals(1L), orderResponse.getA7().equals(1L)));
        box3Object.setPotrebCredit(new OpenCloseDto(orderResponse.getA8().equals(1L), orderResponse.getA9().equals(1L)));
        box3Object.setMfoCredit(new OpenCloseDto(orderResponse.getA10().equals(1L), orderResponse.getA11().equals(1L)));
        box3Object.setAutoCredit(new OpenCloseDto(orderResponse.getA12().equals(1L), orderResponse.getA13().equals(1L)));
        box3Object.setCreditCard(new OpenCloseDto(orderResponse.getA14().equals(1L), orderResponse.getA15().equals(1L)));
        box3Object.setOtherCredit(new OpenCloseDto(orderResponse.getA16().equals(1L), orderResponse.getA17().equals(1L)));
        mapResultRating.put("box3", box3Object);
    }

    // Расчет заголовков и значений в Блоке 2
    private void calculateHeaders1Box2(Map<String, Object> mapResultRating, CatActiveAccount catActiveAccountA1) {
        Box2Object box2Object = (Box2Object) mapResultRating.get("box2");
        box2Object.setLoad1(new TitleValueDto("Количество активных кредитов", catActiveAccountA1.getDescription()));
        mapResultRating.put("box2", box2Object);
    }

    private void calculateHeaders2Box2(Map<String, Object> mapResultRating, String d1, CatSumExistingCredit catSumExistingCreditA3, CatSumOverdueCredit catSumOverdueCreditD2) {
        TitleValueDto titleValueDto = new TitleValueDto();
        if (d1.equals("0") || d1.equals("1")) {
            titleValueDto.setTitle("Сумма всех активных кредитов");
            titleValueDto.setText(catSumExistingCreditA3.getDescription());
        } else {
            titleValueDto.setTitle("Сумма просроченной задолженности");
            titleValueDto.setText(catSumOverdueCreditD2.getDescription());
        }
        Box2Object box2Object = (Box2Object) mapResultRating.get("box2");
        box2Object.setLoad2(titleValueDto);
        mapResultRating.put("box2", box2Object);
    }

    private void calculateHeaders3Box2(Map<String, Object> mapResultRating, String d1, CatCurrentDebtLoad catCurrentDebtLoadA4, CatDelayPeriod catDelayPeriodD1) {
        TitleValueDto titleValueDto = new TitleValueDto();
        if (d1.equals("0") || d1.equals("1")) {
            titleValueDto.setTitle("Остаток долга по активным кредитам");
            titleValueDto.setText(catCurrentDebtLoadA4.getDescription());
        } else {
            titleValueDto.setTitle("Срок активной просрочки");
            titleValueDto.setText(catDelayPeriodD1.getDescription());
        }
        Box2Object box2Object = (Box2Object) mapResultRating.get("box2");
        box2Object.setLoad3(titleValueDto);
        mapResultRating.put("box2", box2Object);
    }

    private Box2Object setCommentToBox2(Map<String, Object> mapResultRating, TitleValueDto value) {
        Box2Object box2Object = (Box2Object) mapResultRating.get("box2");
        List<TitleValueDto> titleValueDtoList = box2Object.getComments();
        titleValueDtoList.add(value);
        box2Object.setComments(titleValueDtoList);
        return box2Object;
    }

    private List<TitleValueDto> setCommentToBox4(Map<String, Object> mapResultRating, TitleValueDto value) {
        List<TitleValueDto> titleValueDtoList = (List<TitleValueDto>) mapResultRating.get("box4");
        titleValueDtoList.add(value);
        return titleValueDtoList;
    }

    public static class Box2Object implements Serializable {
        private TitleValueDto load1;
        private TitleValueDto load2;
        private TitleValueDto load3;
        private List<TitleValueDto> comments = new ArrayList<>();

        public Box2Object() {
        }

        public Box2Object(TitleValueDto load1, TitleValueDto load2, TitleValueDto load3, List<TitleValueDto> comments) {
            this.load1 = load1;
            this.load2 = load2;
            this.load3 = load3;
            this.comments = comments;
        }

        public TitleValueDto getLoad1() {
            return load1;
        }

        public void setLoad1(TitleValueDto load1) {
            this.load1 = load1;
        }

        public TitleValueDto getLoad2() {
            return load2;
        }

        public void setLoad2(TitleValueDto load2) {
            this.load2 = load2;
        }

        public TitleValueDto getLoad3() {
            return load3;
        }

        public void setLoad3(TitleValueDto load3) {
            this.load3 = load3;
        }

        public List<TitleValueDto> getComments() {
            return comments;
        }

        public void setComments(List<TitleValueDto> comments) {
            this.comments = comments;
        }
    }

    public static class Box3Object implements Serializable {
        private OpenCloseDto ipotecaCredit;
        private OpenCloseDto potrebCredit;
        private OpenCloseDto mfoCredit;
        private OpenCloseDto autoCredit;
        private OpenCloseDto creditCard;
        private OpenCloseDto otherCredit;

        public Box3Object() {
        }

        public Box3Object(OpenCloseDto ipotecaCredit, OpenCloseDto potrebCredit, OpenCloseDto mfoCredit, OpenCloseDto autoCredit, OpenCloseDto creditCard, OpenCloseDto otherCredit) {
            this.ipotecaCredit = ipotecaCredit;
            this.potrebCredit = potrebCredit;
            this.mfoCredit = mfoCredit;
            this.autoCredit = autoCredit;
            this.creditCard = creditCard;
            this.otherCredit = otherCredit;
        }

        public OpenCloseDto getIpotecaCredit() {
            return ipotecaCredit;
        }

        public void setIpotecaCredit(OpenCloseDto ipotecaCredit) {
            this.ipotecaCredit = ipotecaCredit;
        }

        public OpenCloseDto getPotrebCredit() {
            return potrebCredit;
        }

        public void setPotrebCredit(OpenCloseDto potrebCredit) {
            this.potrebCredit = potrebCredit;
        }

        public OpenCloseDto getMfoCredit() {
            return mfoCredit;
        }

        public void setMfoCredit(OpenCloseDto mfoCredit) {
            this.mfoCredit = mfoCredit;
        }

        public OpenCloseDto getAutoCredit() {
            return autoCredit;
        }

        public void setAutoCredit(OpenCloseDto autoCredit) {
            this.autoCredit = autoCredit;
        }

        public OpenCloseDto getCreditCard() {
            return creditCard;
        }

        public void setCreditCard(OpenCloseDto creditCard) {
            this.creditCard = creditCard;
        }

        public OpenCloseDto getOtherCredit() {
            return otherCredit;
        }

        public void setOtherCredit(OpenCloseDto otherCredit) {
            this.otherCredit = otherCredit;
        }
    }
}
