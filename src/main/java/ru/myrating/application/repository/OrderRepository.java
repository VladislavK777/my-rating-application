package ru.myrating.application.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderRequest, Long> {
    List<OrderRequest> findAllByCreatedDateBeforeAndPersonDataIsDeletedFalse(LocalDateTime dateNow);

    @Query("from OrderRequest o where o.createdDate > :dateFrom and o.createdDate <= :dateTo and o.partnerUser.login = :login")
    List<OrderRequest> findAllByCreatedDateAndPartnerUser(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo, @Param("login") String login);

    @Query("from OrderRequest o where o.partnerUser.login = :login")
    List<OrderRequest> findAllByPartnerUser(@Param("login") String login);
}
