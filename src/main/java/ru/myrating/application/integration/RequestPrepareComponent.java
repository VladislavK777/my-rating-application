package ru.myrating.application.integration;

import jakarta.xml.bind.JAXBElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.myrating.application.config.IntegrationConfigurationParams;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.out.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class RequestPrepareComponent {
    private final Logger log = LoggerFactory.getLogger(RequestPrepareComponent.class);
    private final IntegrationConfigurationParams integrationConfigurationParams;

    public RequestPrepareComponent(IntegrationConfigurationParams integrationConfigurationParams) {
        this.integrationConfigurationParams = integrationConfigurationParams;
    }

    protected JAXBElement<ProductType> createRequestModel(OrderRequest orderRequest) throws DatatypeConfigurationException {
        ObjectFactory objectFactory = new ObjectFactory();
        ProductRequest productRequest = objectFactory.createProductRequest();
        CreditReportRequest creditReportRequest = objectFactory.createCreditReportRequest();
        List<Address> addressList = new ArrayList<>();

        Address addressReg = objectFactory.createAddress();
        addressReg.setAddressType("1");
        addressReg.setCity("Неизвестно");
        addressReg.setStreet("Неизвестно");
        addressReg.setPostal("000000");
        addressList.add(addressReg);

        Address addressLife = objectFactory.createAddress();
        addressLife.setAddressType("2");
        addressLife.setCity("Неизвестно");
        addressLife.setStreet("Неизвестно");
        addressLife.setPostal("000000");
        addressList.add(addressLife);
        creditReportRequest.getAddressReq().addAll(addressList);

        Id id = objectFactory.createId();
        id.setIdType(integrationConfigurationParams.getIdType());
        id.setIdNum(orderRequest.getOrderData().getPassportNumber().toString());
        id.setSeriesNumber(orderRequest.getOrderData().getPassportSerial().toString());
        id.setIssueCountry("Неизвестно");
        id.setIssueAuthority("Неизвестно");
        id.setIssueDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(orderRequest.getOrderData().getBirthDate()
                .plusYears(16)
                .plusMonths(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        creditReportRequest.getIdReq().add(id);

        Person person = objectFactory.createPerson();
        person.setName1(orderRequest.getOrderData().getLastName());
        person.setFirst(orderRequest.getOrderData().getFirstName());
        person.setGender("1");
        person.setBirthDt(DatatypeFactory.newInstance().newXMLGregorianCalendar(orderRequest.getOrderData().getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        person.setPlaceOfBirth("Неизвестно");
        creditReportRequest.getPersonReq().add(person);

        Inquiry inquiry = objectFactory.createInquiry();
        inquiry.setInqPurpose(integrationConfigurationParams.getInqPurpose());
        inquiry.setInqAmount(BigInteger.ZERO);
        inquiry.setCurrencyCode(integrationConfigurationParams.getCurrencyCode());

        Consent consent = objectFactory.createConsent();
        consent.setConsentFlag(integrationConfigurationParams.getConsentFlag());
        consent.setConsentPurpose(BigInteger.ONE);
        consent.setConsentDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDateTime.now().minusHours(6).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        consent.setConsentExpireDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDateTime.now().plusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        consent.setReportUser(integrationConfigurationParams.getReportUser());
        consent.setLiability(integrationConfigurationParams.getLiability());
        inquiry.setConsentReq(consent);
        creditReportRequest.setInquiryReq(inquiry);

        Requestor requestor = objectFactory.createRequestor();
        requestor.setUserID(integrationConfigurationParams.getUserId());
        requestor.setMemberCode(integrationConfigurationParams.getMemberCode());
        requestor.setPassword(integrationConfigurationParams.getPassword());
        creditReportRequest.setRequestorReq(requestor);

        Reference reference = objectFactory.createReference();
        reference.setProduct(integrationConfigurationParams.getProduct());
        creditReportRequest.setRefReq(reference);
        creditReportRequest.setIOType(integrationConfigurationParams.getIOType());
        creditReportRequest.setOutputFormat(integrationConfigurationParams.getOutputFormat());
        creditReportRequest.setLang(integrationConfigurationParams.getLang());
        productRequest.setReq(creditReportRequest);

        ProductType productType = objectFactory.createProductType();
        productType.setPrequest(productRequest);

        return objectFactory.createProduct(productType);
    }
}
