package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.myrating.application.security.SecurityUtils;
import ru.myrating.application.service.dto.payment.PaymentDetailsDTO;
import ru.myrating.application.service.dto.payment.PaymentResultDbDTO;
import ru.myrating.application.service.dto.payment.PaymentUserDTO;
import ru.myrating.application.service.mapper.PaymentResultMapper;
import ru.myrating.application.web.rest.errors.BadRequestAlertException;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
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

    private final String query = "select\n" +
            "    ju.id,\n" +
            "    ju.login,\n" +
            "    up.partner_name,\n" +
            "    up.url,\n" +
            "    up.fee,\n" +
            "    up.requisites_data,\n" +
            "    payment_details.\"year\",\n" +
            "    payment_details.\"month\",\n" +
            "    payment_details.\"count\",\n" +
            "    payment_details.\"count\" * up.fee as \"payment\",\n" +
            "    row_number () over (\n" +
            "\t\tpartition by ju.id\n" +
            "\t\torder by payment_details.\"year\", payment_details.\"month\") as \"order_by\"\n" +
            "from\n" +
            "    jhi_user ju,\n" +
            "    user_profile up,\n" +
            "    (\n" +
            "        select\n" +
            "            date_part('month', or2.created_date ::date) as \"month\",\n" +
            "            date_part('year', or2.created_date ::date) as \"year\",\n" +
            "            count(*) as \"count\",\n" +
            "            or2.partner_user_id\n" +
            "        from\n" +
            "            order_request or2\n" +
            "            where or2.status not in ('NEW')\n" +
            "        group by\n" +
            "            1,\n" +
            "            2,\n" +
            "            or2.partner_user_id) payment_details\n" +
            "where\n" +
            "        ju.id = payment_details.partner_user_id\n" +
            "  and ju.user_profile_id = up.id\n";

    public List<PaymentUserDTO> getAllPayments(@Nullable Integer year, Sort sort) throws IOException {
        List<PaymentResultDbDTO> resultDbDTO = jdbcTemplate.query(year == null ? query
                : query + " and payment_details.year = " + year, paymentResultMapper);
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

    public List<PaymentDetailsDTO> getPaymentsByPartner(Integer year) {
        Optional<String> loginOptional = SecurityUtils.getCurrentUserLogin();
        if (loginOptional.isEmpty())
            throw new BadRequestAlertException("Login is empty", "paymentManagement", "notfound");
        List<PaymentResultDbDTO> resultDbDTO = jdbcTemplate.query(
                query + " and payment_details.year = " + year + " and ju.login = '" + loginOptional.get() + "'", paymentResultMapper);
        List<PaymentDetailsDTO> result = resultDbDTO.stream().map(PaymentResultDbDTO::getPaymentDetailsDTO).collect(toList());
        List<Integer> detailsMonthsIds = result.stream().map(PaymentDetailsDTO::getMonthId).collect(toList());
        for (Entry<Integer, String> months : paymentResultMapper.getMapMonths().entrySet()) {
            if (!detailsMonthsIds.contains(months.getKey())) {
                PaymentDetailsDTO paymentDetailsDTO = new PaymentDetailsDTO();
                paymentDetailsDTO.setPayment(0);
                paymentDetailsDTO.setMonthId(months.getKey());
                paymentDetailsDTO.setMonthName(months.getValue());
                paymentDetailsDTO.setOrderCount(0);
                result.add(paymentDetailsDTO);
            }
        }
        result.sort(comparing(PaymentDetailsDTO::getMonthId));
        return result;
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
