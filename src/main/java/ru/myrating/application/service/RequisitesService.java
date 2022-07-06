package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.Requisites;
import ru.myrating.application.domain.UserProfile;
import ru.myrating.application.repository.RequisitesRepository;
import tech.jhipster.security.RandomUtil;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@Transactional
public class RequisitesService {
    private final Logger log = LoggerFactory.getLogger(RequisitesService.class);

    private final RequisitesRepository requisitesRepository;

    public RequisitesService(RequisitesRepository requisitesRepository) {
        this.requisitesRepository = requisitesRepository;
    }

    public Requisites save(Requisites requisites) {
        return requisitesRepository.saveAndFlush(requisites);
    }
}
