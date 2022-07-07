package ru.myrating.application.integration;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.out.ProductType;
import ru.myrating.application.web.rest.errors.BadRequestAlertException;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;

import static jakarta.xml.bind.JAXBContext.newInstance;
import static jakarta.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.List.of;
import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;
import static org.springframework.http.MediaType.TEXT_XML_VALUE;
import static tech.jhipster.config.JHipsterConstants.SPRING_PROFILE_PRODUCTION;

@Service
@Transactional
public class IntegrationProviderService {
    private final Logger log = LoggerFactory.getLogger(IntegrationProviderService.class);
    private final RequestPrepareComponent requestPrepareComponent;
    private final HttpClientComponent httpsClientComponent;
    private final Environment environment;
    @Value("${application.url}")
    private String url;

    public IntegrationProviderService(RequestPrepareComponent requestPrepareComponent, HttpClientComponent httpsClientComponent, Environment environment) {
        this.requestPrepareComponent = requestPrepareComponent;
        this.httpsClientComponent = httpsClientComponent;
        this.environment = environment;
    }

    public ProductType callOutService(OrderRequest orderRequest) {
        try {
            JAXBElement<ProductType> requestBody = requestPrepareComponent.createRequestModel(orderRequest);
            StringWriter sw = new StringWriter();
            JAXBContext jaxbContext = newInstance(ProductType.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(requestBody, sw);

            try (CloseableHttpClient httpClient = isProdProfile()
                    ? httpsClientComponent.getHttpClientWithSsl()
                    : httpsClientComponent.getHttpClientWithOutSsl()) {
                HttpPost postRequest = new HttpPost(url);
                StringEntity input = new StringEntity(sw.toString(), UTF_8);
                input.setContentType(TEXT_XML_VALUE);
                postRequest.setEntity(input);
                try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                    byte[] is = response.getEntity().getContent().readAllBytes();

                    // Проверяем сетрификат, если все ок, парсим
                    if (!isProdProfile() || httpsClientComponent.isTrustedCertificate(is)) {
                        String xml = new String(is, UTF_8);
                        // Распарсим XML, для этого необходимо удалить лишние символы из подписи
                        String result = xml.substring(
                                xml.indexOf("<?xml version=\"1.0\" encoding=\"windows-1251\"?>"),
                                xml.indexOf("</product>") + "</product>".length()
                        );

                        ProductType productType = jaxbContext.createUnmarshaller().unmarshal(new StreamSource(new ByteArrayInputStream(result.getBytes(UTF_8))), ProductType.class).getValue();
                        /*if (productType.getPreply().getErr() != null) {
                            Error error = productType.getPreply().getErr();
                            StringBuilder errorText = new StringBuilder();
                            for (CodeText codeText : error.getCtErr()) {
                                errorText
                                        .append("Code: ")
                                        .append(codeText.getCode())
                                        .append("; ")
                                        .append("Text: ")
                                        .append(codeText.getText())
                                        .append("; ");
                            }
                            throw new BadRequestAlertException("Response error: " + errorText, ENTITY_NAME, "responseerror");
                        }*/
                        return productType;
                    }
                }
            }
        } catch (Exception e) {
            log.error("Process call integration service failed: " + e);
            throw new BadRequestAlertException("Process call integration service failed; cause: " + e.getMessage(), ENTITY_NAME, "outcallfailed");
        }
        throw new BadRequestAlertException("Certificate validation fault", ENTITY_NAME, "certfalidfault");
    }

    private boolean isProdProfile() {
        if (environment.getActiveProfiles().length > 0) {
            List<String> list = of(environment.getActiveProfiles());
            return list.contains(SPRING_PROFILE_PRODUCTION);
        }
        return false;
    }
}
