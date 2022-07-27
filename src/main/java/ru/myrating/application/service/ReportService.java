package ru.myrating.application.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import ru.myrating.application.domain.User;
import ru.myrating.application.domain.enumeration.OrderStatusEnum;
import ru.myrating.application.service.dto.criteria.UserCriteria;
import ru.myrating.application.service.dto.order.OrderRequestDTO;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class ReportService {
    private final Logger log = LoggerFactory.getLogger(ReportService.class);

    private final UserQueryService userQueryService;
    private final OrderService orderService;

    public ReportService(UserQueryService userQueryService, OrderService orderService) {
        this.userQueryService = userQueryService;
        this.orderService = orderService;
    }

    public ByteArrayResource getAllUsers(UserCriteria criteria) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("users");
        List<String> listTitle = of("Название партнера", "Логин", "URL", "Дата регистрации",
                "Имя", "Телефон", "Email", "Реферальная ссылка", "Ставка").collect(toList());
        int num = 0;
        Row row = sheet.createRow(num);
        for (int countCell = 0; countCell < listTitle.size(); countCell++) {
            createTitle(row, countCell, listTitle.get(countCell));
        }
        for (User user : userQueryService.getAllManagedUsers(criteria)) {
            int countCell = 0;
            num = num + 1;
            row = sheet.createRow(num);
            createTitle(row, countCell++, checkIsNull(user.getProfile().getPartnerName()));
            createTitle(row, countCell++, checkIsNull(user.getLogin()));
            createTitle(row, countCell++, checkIsNull(user.getProfile().getUrl()));
            createTitle(row, countCell++, user.getCreatedDate().format(ofPattern("dd.MM.yyyy")));
            createTitle(row, countCell++, checkIsNull(user.getFirstName()));
            createTitle(row, countCell++, checkIsNull(user.getProfile().getPhoneNumber()));
            createTitle(row, countCell++, checkIsNull(user.getEmail()));
            createTitle(row, countCell++, checkIsNull(user.getProfile().getReferenceLink()));
            createTitle(row, countCell, user.getProfile().getFee());
        }
        workbook.write(byteArrayOutputStream);
        workbook.close();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }

    public ByteArrayResource getAllOrders() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("orders");
        List<String> listTitle = of("Номер запроса", "Дата запроса", "Фамилия", "Ссылка на отчет",
                "Статус", "Email", "Название партнера", "Логин").collect(toList());
        int num = 0;
        Row row = sheet.createRow(num);
        for (int countCell = 0; countCell < listTitle.size(); countCell++) {
            createTitle(row, countCell, listTitle.get(countCell));
        }
        for (OrderRequestDTO orderRequest : orderService.getAllOrders()) {
            int countCell = 0;
            num = num + 1;
            row = sheet.createRow(num);
            createTitle(row, countCell++, orderRequest.getId());
            createTitle(row, countCell++, orderRequest.getCreatedDate().format(ofPattern("dd.MM.yyyy")));
            createTitle(row, countCell++, checkIsNull(orderRequest.getLastName()));
            createTitle(row, countCell++, checkIsNull(orderRequest.getUrlReport()));
            createTitle(row, countCell++, checkIsNull(orderRequest.getStatus()));
            createTitle(row, countCell++, checkIsNull(orderRequest.getEmail()));
            createTitle(row, countCell++, checkIsNull(orderRequest.getPartnerName()));
            createTitle(row, countCell, checkIsNull(orderRequest.getLogin()));
        }
        workbook.write(byteArrayOutputStream);
        workbook.close();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }

    public ByteArrayResource getOrdersByPartner(@Nullable String dateFrom, @Nullable String dateTo, @Nullable OrderStatusEnum status) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("orders");
        List<String> listTitle = of("Номер запроса", "Дата запроса", "Статус").collect(toList());
        int num = 0;
        Row row = sheet.createRow(num);
        for (int countCell = 0; countCell < listTitle.size(); countCell++) {
            createTitle(row, countCell, listTitle.get(countCell));
        }
        for (OrderRequestDTO orderRequest : orderService.getOrdersByPartner(dateFrom, dateTo, status).getOrders()) {
            int countCell = 0;
            num = num + 1;
            row = sheet.createRow(num);
            createTitle(row, countCell++, orderRequest.getId());
            createTitle(row, countCell++, orderRequest.getCreatedDate().format(ofPattern("dd.MM.yyyy")));
            createTitle(row, countCell, checkIsNull(orderRequest.getStatus()));
        }
        workbook.write(byteArrayOutputStream);
        workbook.close();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }

    private void createTitle(Row row, int countCell, String title) {
        Cell cell = row.createCell(countCell);
        cell.setCellValue(title.trim());
    }

    private void createTitle(Row row, int countCell, Integer title) {
        Cell cell = row.createCell(countCell);
        cell.setCellValue(title);
    }

    private void createTitle(Row row, int countCell, Long title) {
        Cell cell = row.createCell(countCell);
        cell.setCellValue(title);
    }

    private String checkIsNull(String value) {
        return isEmpty(value) ? "" : value;
    }
}
