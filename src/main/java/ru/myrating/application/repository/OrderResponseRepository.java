package ru.myrating.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.OrderResponse;

public interface OrderResponseRepository extends JpaRepository<OrderResponse, Long> {
}
