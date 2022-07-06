package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.UserProfile;
import ru.myrating.application.repository.UserProfileRepository;
import tech.jhipster.security.RandomUtil;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@Transactional
public class UserProfileService {
    private final Logger log = LoggerFactory.getLogger(UserProfileService.class);

    private final RequisitesService requisitesService;
    private final UserProfileRepository userProfileRepository;

    public UserProfileService(RequisitesService requisitesService, UserProfileRepository userProfileRepository) {
        this.requisitesService = requisitesService;
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile save(UserProfile userProfile) {
        if (isEmpty(userProfile.getRefLink())) {
            userProfile.setRefLink(RandomUtil.generateRandomAlphanumericString());
        }
        userProfile.setRequisites(requisitesService.save(userProfile.getRequisites()));
        return userProfileRepository.saveAndFlush(userProfile);
    }
}
