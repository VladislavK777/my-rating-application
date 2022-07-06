package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.Authority;
import ru.myrating.application.domain.User;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static ru.myrating.application.security.AuthoritiesConstants.ANONYMOUS;

@Service
@Transactional(readOnly = true)
public class ValidationService {
    private final Logger log = LoggerFactory.getLogger(ValidationService.class);

    private final UserService userService;

    public ValidationService(UserService userService) {
        this.userService = userService;
    }

    public boolean validationApiKey(String key) {
        if (isBlank(key))
            return false;
        User user = userService.getUserByApiKey(key);
        if (user == null)
            return false;
        return user.getAuthorities().stream().map(Authority::getName).collect(toSet()).contains(ANONYMOUS);
    }
}
