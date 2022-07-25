package ru.myrating.application.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.myrating.application.domain.jsonb.RequisitesData;
import ru.myrating.application.service.dto.payment.PaymentDetailsDTO;
import ru.myrating.application.service.dto.payment.PaymentResultDbDTO;
import ru.myrating.application.service.dto.payment.PaymentUserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentResultMapper implements RowMapper<PaymentResultDbDTO> {
    private final ObjectMapper objectMapper;
    private final Map<Integer, String> mapMonths = new HashMap();

    public PaymentResultMapper(ObjectMapper objectMapper) {
        init();
        this.objectMapper = objectMapper;
    }

    public void init() {
        mapMonths.put(1, "Январь");
        mapMonths.put(2, "Февраль");
        mapMonths.put(3, "Март");
        mapMonths.put(4, "Апрель");
        mapMonths.put(5, "Май");
        mapMonths.put(6, "Июнь");
        mapMonths.put(7, "Июль");
        mapMonths.put(8, "Август");
        mapMonths.put(9, "Сентябрь");
        mapMonths.put(10, "Октябрь");
        mapMonths.put(11, "Ноябрь");
        mapMonths.put(12, "Декабрь");
    }

    @Override
    public PaymentResultDbDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        PaymentUserDTO paymentUserDTO = new PaymentUserDTO();
        paymentUserDTO.setPartnerId(rs.getLong("id"));
        paymentUserDTO.setPartnerLogin(rs.getString("login"));
        paymentUserDTO.setPartnerName(rs.getString("partner_name"));
        paymentUserDTO.setPartnerFee(rs.getInt("fee"));
        paymentUserDTO.setPartnerUrl(rs.getString("url"));
        try {
            paymentUserDTO.setPartnerRequisitesData(objectMapper.readValue(rs.getString("requisites_data"), RequisitesData.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        PaymentDetailsDTO paymentDetailsDTO = new PaymentDetailsDTO();
        paymentDetailsDTO.setPeriod(mapMonths.get(rs.getInt("month")) + " " + rs.getInt("year"));
        paymentDetailsDTO.setOrderCount(rs.getInt("count"));
        paymentDetailsDTO.setPayment(rs.getInt("payment"));
        paymentDetailsDTO.setOrderBy(rs.getInt("order_by"));
        return new PaymentResultDbDTO(paymentUserDTO, paymentDetailsDTO);
    }
}
