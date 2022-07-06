package ru.myrating.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.OrderResponse;
import ru.myrating.application.domain.catalog.ProfileType;

public interface OrderResponseRepository extends JpaRepository<OrderResponse, Long> {
}
