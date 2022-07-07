package ru.myrating.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.myrating.application.domain.OrderFaultQueue;
import ru.myrating.application.domain.OrderRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderFaultQueueRepository extends JpaRepository<OrderFaultQueue, Long> {
    @Query("from OrderFaultQueue orderFault where orderFault.deadLineDate > :dateNow and orderFault.orderRequest.status = 'FAULT'")
    List<OrderFaultQueue> findAllByDeadLineDateAfter(@Param("dateNow") LocalDateTime dateNow);

    Optional<OrderFaultQueue> findByOrderRequest(OrderRequest orderRequest);
}
