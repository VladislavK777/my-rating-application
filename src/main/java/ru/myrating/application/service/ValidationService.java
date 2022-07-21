package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.Authority;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.domain.User;
import ru.myrating.application.web.rest.errors.AccessDeniedAlertException;
import ru.myrating.application.web.rest.errors.BadRequestAlertException;
import ru.myrating.application.web.rest.vm.ManagedUserVM;

import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.*;
import static ru.myrating.application.config.Constants.EMAIL_REGEX;
import static ru.myrating.application.config.Constants.UPPER_CYRILLIC_LITTERS;
import static ru.myrating.application.domain.enumeration.ProfileTypeEnum.INDIVIDUAL_ENTITY;
import static ru.myrating.application.domain.enumeration.ProfileTypeEnum.LEGAL_ENTITY;
import static ru.myrating.application.security.AuthoritiesConstants.ANONYMOUS;
import static ru.myrating.application.web.rest.errors.ErrorConstants.ERR_VALIDATION;

@Service
@Transactional(readOnly = true)
public class ValidationService {
    private final Logger log = LoggerFactory.getLogger(ValidationService.class);

    private final UserService userService;

    public ValidationService(UserService userService) {
        this.userService = userService;
    }

    public void validationApiKey(String key) {
        if (isBlank(key))
            throw new AccessDeniedAlertException("Access denied!", "validation", ERR_VALIDATION);
        User user = userService.getUserByApiKey(key);
        if (user == null)
            throw new AccessDeniedAlertException("Access denied!", "validation", ERR_VALIDATION);
        if (!user.getAuthorities().stream().map(Authority::getName).collect(toSet()).contains(ANONYMOUS))
            throw new AccessDeniedAlertException("Access denied!", "validation", ERR_VALIDATION);
    }

    public void validationRequest(OrderRequest orderRequest) {
        if (isNotEmpty(orderRequest.getOrderData().getFirstName())) {
            if (!orderRequest.getOrderData().getFirstName().matches(UPPER_CYRILLIC_LITTERS)) {
                throw new BadRequestAlertException("FirstName must contains only Cyrillic upper letters", "validationService", ERR_VALIDATION);
            }
        } else {
            throw new BadRequestAlertException("FirstName must not be empty", "orderManagement", ERR_VALIDATION);
        }
        if (isNotEmpty(orderRequest.getOrderData().getLastName())) {
            if (!orderRequest.getOrderData().getLastName().matches(UPPER_CYRILLIC_LITTERS)) {
                throw new BadRequestAlertException("LastName must contains only Cyrillic upper letters", "validationService", ERR_VALIDATION);
            }
        } else {
            throw new BadRequestAlertException("LastName must not be empty", "orderManager", ERR_VALIDATION);
        }
        if (orderRequest.getOrderData().getPassportSerial() != null) {
            if (!String.valueOf(orderRequest.getOrderData().getPassportSerial()).matches("\\d{4}")) {
                throw new BadRequestAlertException("PassportSerial must contains only digital and length 4", "validationService", ERR_VALIDATION);
            }
        } else {
            throw new BadRequestAlertException("PassportSerial must not be empty", "validationService", ERR_VALIDATION);
        }
        if (orderRequest.getOrderData().getPassportNumber() != null) {
            if (!String.valueOf(orderRequest.getOrderData().getPassportNumber()).matches("\\d{6}"))
                throw new BadRequestAlertException("PassportNumber must contains only digital and length 6", "validationService", ERR_VALIDATION);
        } else {
            throw new BadRequestAlertException("PassportNumber must not be empty", "validationService", ERR_VALIDATION);
        }
        if (isNotEmpty(orderRequest.getOrderData().getEmail())) {
            if (!orderRequest.getOrderData().getEmail().toUpperCase().matches(EMAIL_REGEX)) {
                throw new BadRequestAlertException("Email invalid", "validationService", ERR_VALIDATION);
            }
        } else {
            throw new BadRequestAlertException("Email must not be empty", "validationService", ERR_VALIDATION);
        }
        if (orderRequest.getOrderData().getBirthDate() == null) {
            throw new BadRequestAlertException("BirthDate must not be empty", "validationService", ERR_VALIDATION);
        }
    }

    public void validationUserProfile(ManagedUserVM userDTO) {
        if (userDTO.getProfile().getRequisitesData() == null)
            throw new BadRequestAlertException("RequisitesData must not be empty", "validationService", ERR_VALIDATION);
        if (LEGAL_ENTITY.equals(userDTO.getProfile().getProfileType())) {
            if (isEmpty(userDTO.getProfile().getRequisitesData().getBankName()))
                throw new BadRequestAlertException("BankName must not be empty", "validationService", ERR_VALIDATION);
            if (isEmpty(userDTO.getProfile().getRequisitesData().getCustomerName()))
                throw new BadRequestAlertException("CustomerName must not be empty", "validationService", ERR_VALIDATION);
            if (isEmpty(userDTO.getProfile().getRequisitesData().getLegalAddress()))
                throw new BadRequestAlertException("LegalAddress must not be empty", "validationService", ERR_VALIDATION);
            if (isEmpty(userDTO.getProfile().getRequisitesData().getPostAddress()))
                throw new BadRequestAlertException("PostAddress must not be empty", "validationService", ERR_VALIDATION);
            if (userDTO.getProfile().getRequisitesData().getBik() == null)
                throw new BadRequestAlertException("Bik must not be empty", "validationService", ERR_VALIDATION);
            if (userDTO.getProfile().getRequisitesData().getCorrecpondentAccount() == null)
                throw new BadRequestAlertException("CorrecpondentAccount must not be empty", "validationService", ERR_VALIDATION);
            if (userDTO.getProfile().getRequisitesData().getInn() == null)
                throw new BadRequestAlertException("Inn must not be empty", "validationService", ERR_VALIDATION);
            if (userDTO.getProfile().getRequisitesData().getKpp() == null)
                throw new BadRequestAlertException("Kpp must not be empty", "validationService", ERR_VALIDATION);
            if (userDTO.getProfile().getRequisitesData().getPaymentAccount() == null)
                throw new BadRequestAlertException("PaymentAccount must not be empty", "validationService", ERR_VALIDATION);
        }
        if (INDIVIDUAL_ENTITY.equals(userDTO.getProfile().getProfileType())) {
            if (isEmpty(userDTO.getProfile().getRequisitesData().getBankName())
                    && isEmpty(userDTO.getProfile().getRequisitesData().getWalletName()))
                throw new BadRequestAlertException("BankName or WalletName must not be empty", "validationService", ERR_VALIDATION);
            if (userDTO.getProfile().getRequisitesData().getCardNumber() == null
                    && userDTO.getProfile().getRequisitesData().getWallet() == null)
                throw new BadRequestAlertException("CardNumber or Wallet must not be empty", "validationService", ERR_VALIDATION);
        }
    }
}
