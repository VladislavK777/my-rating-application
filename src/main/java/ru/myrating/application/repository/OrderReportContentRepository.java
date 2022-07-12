package ru.myrating.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.myrating.application.domain.OrderReportContent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderReportContentRepository extends JpaRepository<OrderReportContent, Long> {
    @Query("from OrderReportContent orderReport where orderReport.deactivateDate < :dateNow and orderReport.activated = true")
    List<OrderReportContent> findAllByDeactivateDateAfter(@Param("dateNow") LocalDateTime dateNow);

    Optional<OrderReportContent> findByOrderResultLink(UUID link);
}
