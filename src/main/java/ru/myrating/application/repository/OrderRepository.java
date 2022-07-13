package ru.myrating.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.OrderRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderRequest, Long> {
    List<OrderRequest> findAllByCreatedDateBeforeAndPersonDataIsDeletedFalse(LocalDateTime dateNow);
}
