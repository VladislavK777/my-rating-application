package ru.myrating.application.service.mapper;

import org.springframework.stereotype.Component;
import ru.myrating.application.config.ApplicationProperties;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.service.dto.order.OrderRequestDTO;

@Component
public class OrderRequestMapper {
    private final ApplicationProperties applicationProperties;

    public OrderRequestMapper(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public OrderRequestDTO toDto(OrderRequest orderRequest) {
        OrderRequestDTO orderRequestDto = new OrderRequestDTO();
        orderRequestDto.setId(orderRequest.getId());
        orderRequestDto.setCreatedDate(orderRequest.getCreatedDate());
        orderRequestDto.setStatus(orderRequest.getStatus().getValue());
        orderRequestDto.setLogin(orderRequest.getPartnerUser() != null ? orderRequest.getPartnerUser().getLogin() : "Без партнера");
        orderRequestDto.setPartnerName(orderRequest.getPartnerUser() != null ? orderRequest.getPartnerUser().getProfile().getPartnerName() : "Без партнера");
        orderRequestDto.setLastName(orderRequest.getOrderData().getLastName());
        orderRequestDto.setEmail(orderRequest.getOrderData().getEmail());
        orderRequestDto.setUrlReport(orderRequest.getOrderReportContent() != null ? applicationProperties.getLinkReport() + orderRequest.getOrderReportContent().getOrderResultLink() : null);
        return orderRequestDto;
    }

    public OrderRequestDTO toDtoByPartner(OrderRequest orderRequest) {
        OrderRequestDTO orderRequestDto = new OrderRequestDTO();
        orderRequestDto.setId(orderRequest.getId());
        orderRequestDto.setCreatedDate(orderRequest.getCreatedDate());
        orderRequestDto.setStatus(orderRequest.getStatus().getValue());
        return orderRequestDto;
    }
}
