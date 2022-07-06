package ru.myrating.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.Requisites;

public interface RequisitesRepository extends JpaRepository<Requisites, Long> {
}
