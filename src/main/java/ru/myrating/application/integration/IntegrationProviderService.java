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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.config.ApplicationProperties;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.out.CodeText;
import ru.myrating.application.out.Error;
import ru.myrating.application.out.ProductType;
import ru.myrating.application.web.rest.errors.InternalServerErrorAlertException;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import static jakarta.xml.bind.JAXBContext.newInstance;
import static jakarta.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.TEXT_XML_VALUE;

@Service
@Transactional
public class IntegrationProviderService {
    private final Logger log = LoggerFactory.getLogger(IntegrationProviderService.class);
    private final ApplicationProperties applicationProperties;
    private final RequestPrepareComponent requestPrepareComponent;
    private final HttpClientComponent httpsClientComponent;

    public IntegrationProviderService(ApplicationProperties applicationProperties, RequestPrepareComponent requestPrepareComponent, HttpClientComponent httpsClientComponent) {
        this.applicationProperties = applicationProperties;
        this.requestPrepareComponent = requestPrepareComponent;
        this.httpsClientComponent = httpsClientComponent;
    }

    public ProductType callOutService(OrderRequest orderRequest) {
        try {
            JAXBElement<ProductType> requestBody = requestPrepareComponent.createRequestModel(orderRequest);
            StringWriter sw = new StringWriter();
            JAXBContext jaxbContext = newInstance(ProductType.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(requestBody, sw);

            try (CloseableHttpClient httpClient = httpsClientComponent.getHttpClient()) {
                HttpPost postRequest = new HttpPost(applicationProperties.getIntegrationParams().getUrl());
                StringEntity input = new StringEntity(sw.toString(), UTF_8);
                input.setContentType(TEXT_XML_VALUE);
                postRequest.setEntity(input);
                try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                    byte[] is = response.getEntity().getContent().readAllBytes();

                    // Проверяем сетрификат, если все ок, парсим
                    boolean isNeedTrustCert = !applicationProperties.getIntegrationParams().isNeedTrustCert() || httpsClientComponent.isTrustedCertificate(is);
                    if (isNeedTrustCert) {
                        String xml = new String(is, UTF_8);
                        // Распарсим XML, для этого необходимо удалить лишние символы из подписи
                        String result = xml.substring(
                                xml.indexOf("<?xml version=\"1.0\" encoding=\"windows-1251\"?>"),
                                xml.indexOf("</preply>\n</product>") + "</preply>\n</product>".length()
                        );

                        ProductType productType = jaxbContext.createUnmarshaller().unmarshal(new StreamSource(new ByteArrayInputStream(result.getBytes())), ProductType.class).getValue();
                        if (productType.getPreply().getErr() != null) {
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
                            throw new InternalServerErrorAlertException("Response error: " + errorText, "integrationProvider", "responseerror");
                        }
                        return productType;
                    }
                }
            }
        } catch (Exception e) {
            log.error("Process call integration service failed: " + e);
            throw new InternalServerErrorAlertException("Process call integration service failed; cause: " + e.getMessage(), "integrationProvider", "outcallfailed");
        }
        throw new InternalServerErrorAlertException("Certificate validation fault", "integrationProvider", "certfalidfault");
    }
}
