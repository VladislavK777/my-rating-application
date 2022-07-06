package ru.myrating.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
