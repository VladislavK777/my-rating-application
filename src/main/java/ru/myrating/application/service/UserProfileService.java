package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.UserProfile;
import ru.myrating.application.repository.UserProfileRepository;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static tech.jhipster.security.RandomUtil.generateRandomAlphanumericString;

@Service
@Transactional
public class UserProfileService {
    private final Logger log = LoggerFactory.getLogger(UserProfileService.class);
    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile save(UserProfile userProfile) {
        if (isEmpty(userProfile.getReferenceLink()))
            userProfile.setReferenceLink(generateRandomAlphanumericString());
        return userProfileRepository.saveAndFlush(userProfile);
    }

    public UserProfile getOne(Long userProfileId) {
        return userProfileRepository.getById(userProfileId);
    }

    public void deleteProfile(UserProfile userProfile) {
        userProfileRepository.delete(userProfile);
    }
}
