package ru.myrating.application.service.mapper;

import org.springframework.stereotype.Component;
import ru.myrating.application.domain.OrderResponse;
import ru.myrating.application.out.PcrReasons;

@Component
public class OrderResponseMapper {

    public OrderResponse dtoToDao(PcrReasons pcrReasons) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setI1(pcrReasons.getI1().longValue());
        orderResponse.setI2(pcrReasons.getI2().longValue());
        orderResponse.setI3(pcrReasons.getI3().longValue());
        orderResponse.setI4(pcrReasons.getI4().longValue());
        orderResponse.setA1(pcrReasons.getA1().longValue());
        orderResponse.setA2(pcrReasons.getA2().longValue());
        orderResponse.setA3(pcrReasons.getA3().longValue());
        orderResponse.setA4(pcrReasons.getA4().longValue());
        orderResponse.setA5(pcrReasons.getA5().longValue());
        orderResponse.setA6(pcrReasons.getA6().longValue());
        orderResponse.setA7(pcrReasons.getA7().longValue());
        orderResponse.setA8(pcrReasons.getA8().longValue());
        orderResponse.setA9(pcrReasons.getA9().longValue());
        orderResponse.setA10(pcrReasons.getA10().longValue());
        orderResponse.setA11(pcrReasons.getA11().longValue());
        orderResponse.setA12(pcrReasons.getA12().longValue());
        orderResponse.setA13(pcrReasons.getA13().longValue());
        orderResponse.setA14(pcrReasons.getA14().longValue());
        orderResponse.setA15(pcrReasons.getA15().longValue());
        orderResponse.setA16(pcrReasons.getA16().longValue());
        orderResponse.setA17(pcrReasons.getA17().longValue());
        orderResponse.setD1(pcrReasons.getD1().toString());
        orderResponse.setD2(pcrReasons.getD2().longValue());
        orderResponse.setD3(pcrReasons.getD3().toString());
        orderResponse.setD4(pcrReasons.getD4().longValue());
        return orderResponse;
    }
}
