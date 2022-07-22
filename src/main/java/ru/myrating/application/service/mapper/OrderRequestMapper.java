package ru.myrating.application.service.mapper;

import org.springframework.stereotype.Component;
import ru.myrating.application.config.ApplicationProperties;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.service.dto.OrderRequestDto;

@Component
public class OrderRequestMapper {
    private final ApplicationProperties applicationProperties;

    public OrderRequestMapper(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public OrderRequestDto toDto(OrderRequest orderRequest) {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setId(orderRequestDto.getId());
        orderRequestDto.setCreatedDate(orderRequest.getCreatedDate());
        orderRequestDto.setStatus(orderRequest.getStatus().getValue());
        orderRequestDto.setLogin(orderRequest.getPartnerUser() != null ? orderRequest.getPartnerUser().getLogin() : "Без партнера");
        orderRequestDto.setPartnerName(orderRequest.getPartnerUser() != null ? orderRequest.getPartnerUser().getProfile().getPartnerName() : "Без партнера");
        orderRequestDto.setLastName(orderRequest.getOrderData().getLastName());
        orderRequestDto.setEmail(orderRequest.getOrderData().getEmail());
        orderRequestDto.setUrlReport(orderRequest.getOrderReportContent() != null ? applicationProperties.getLinkReport() + orderRequest.getOrderReportContent().getOrderResultLink() : null);
        return orderRequestDto;
    }
}
