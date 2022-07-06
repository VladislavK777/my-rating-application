package ru.myrating.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.myrating.application.domain.OrderRequest;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderRequest, Long> {
    List<OrderRequest> findAllByStatus(String status);
}
