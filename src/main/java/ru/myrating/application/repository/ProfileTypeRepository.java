package ru.myrating.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.catalog.ProfileType;

public interface ProfileTypeRepository extends JpaRepository<ProfileType, Long> {
}
