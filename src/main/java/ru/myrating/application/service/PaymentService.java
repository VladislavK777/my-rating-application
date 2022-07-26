package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.myrating.application.service.dto.payment.PaymentDetailsDTO;
import ru.myrating.application.service.dto.payment.PaymentResultDbDTO;
import ru.myrating.application.service.dto.payment.PaymentUserDTO;
import ru.myrating.application.service.mapper.PaymentResultMapper;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Comparator.comparing;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class PaymentService {
    private final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final JdbcTemplate jdbcTemplate;
    private final PaymentResultMapper paymentResultMapper;

    public PaymentService(JdbcTemplate jdbcTemplate, PaymentResultMapper paymentResultMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.paymentResultMapper = paymentResultMapper;
    }

    public List<PaymentUserDTO> getAllPayments(@Nullable Integer year, Sort sort) throws IOException {
        List<PaymentResultDbDTO> resultDbDTO = jdbcTemplate.query(year == null ? readFileAsString()
                : readFileAsString() + " and payment_details.year = " + year, paymentResultMapper);
        Map<Long, PaymentUserDTO> mapUsers = new HashMap<>();
        for (PaymentResultDbDTO paymentResultDbDTO : resultDbDTO) {
            LinkedList<PaymentDetailsDTO> detailsList;
            PaymentUserDTO paymentUserDTO;
            if (mapUsers.containsKey(paymentResultDbDTO.getPaymentUserDTO().getPartnerId())) {
                paymentUserDTO = mapUsers.get(paymentResultDbDTO.getPaymentUserDTO().getPartnerId());
                detailsList = paymentUserDTO.getPaymentDetails();
                detailsList.add(paymentResultDbDTO.getPaymentDetailsDTO());
                detailsList.sort(comparing(PaymentDetailsDTO::getOrderBy).reversed());
            } else {
                paymentUserDTO = paymentResultDbDTO.getPaymentUserDTO();
                detailsList = paymentUserDTO.getPaymentDetails();
                detailsList.add(paymentResultDbDTO.getPaymentDetailsDTO());
            }
            paymentUserDTO.setPaymentDetails(detailsList);
            mapUsers.put(paymentResultDbDTO.getPaymentUserDTO().getPartnerId(), paymentUserDTO);
            if (!mapUsers.containsKey(paymentResultDbDTO.getPaymentUserDTO().getPartnerId())) {
                mapUsers.put(paymentResultDbDTO.getPaymentUserDTO().getPartnerId(), paymentResultDbDTO.getPaymentUserDTO());
            }
        }
        return sortResult(new ArrayList<>(mapUsers.values()), sort);
    }

    private String readFileAsString() throws IOException {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = new BufferedReader(
                new FileReader("sql/payment.sql", UTF_8));
        char[] buf = new char[1024];
        int numRead;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

    private List<PaymentUserDTO> sortResult(List<PaymentUserDTO> listResult, Sort sort) {
        if (sort.isSorted()) {
            sort.forEach(s -> {
                if (DESC.equals(s.getDirection())) {
                    listResult.sort(getComparator(s.getProperty()).reversed());
                } else {
                    listResult.sort(getComparator(s.getProperty()));
                }
            });
        }
        return listResult;
    }

    private Comparator<PaymentUserDTO> getComparator(String sortProperty) {
        if ("partnerFee".equals(sortProperty))
            return comparing(PaymentUserDTO::getPartnerFee);
        if ("partnerName".equals(sortProperty))
            return comparing(PaymentUserDTO::getPartnerName);
        if ("partnerLogin".equals(sortProperty))
            return comparing(PaymentUserDTO::getPartnerLogin);
        return comparing(PaymentUserDTO::getPartnerId);
    }
}
