package ru.myrating.application.repository.catalog;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.myrating.application.domain.catalog.CatActiveAccount;

import java.util.Optional;

public interface CatActiveAccountRepository extends JpaRepository<CatActiveAccount, Long> {
    String CAT_ACTIVE_ACCOUNT_CODE = "catActiveAccountCode";

    @Cacheable(cacheNames = CAT_ACTIVE_ACCOUNT_CODE)
    Optional<CatActiveAccount> findByCode(String code);
}
