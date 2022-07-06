package ru.myrating.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.catalog.CatProfileType;

public interface ProfileTypeRepository extends JpaRepository<CatProfileType, Long> {
}
