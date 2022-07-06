package ru.myrating.application.integration;

import io.jsonwebtoken.io.IOException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.OrderRequest;
import ru.myrating.application.domain.OrderResponse;
import ru.myrating.application.out.ProductType;
import ru.myrating.application.service.OrderResponseService;
import ru.myrating.application.service.mapper.OrderResponseMapper;
import ru.myrating.application.web.rest.errors.BadRequestAlertException;

import javax.net.ssl.SSLContext;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static jakarta.xml.bind.JAXBContext.newInstance;
import static jakarta.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;
import static org.springframework.http.MediaType.TEXT_XML_VALUE;

@Service
@Transactional
public class IntegrationProviderService {
    private final Logger log = LoggerFactory.getLogger(IntegrationProviderService.class);
    private final RequestPrepareComponent requestPrepareComponent;
    private final OrderResponseService orderResponseService;
    private final OrderResponseMapper orderResponseMapper;

    @Value("${server.ssl.key-store-type: #{null}}")
    private String keyStoreType;
    @Value("${server.ssl.key-store-password: #{null}}")
    private String keyStorePassword;
    @Value("${server.ssl.key-store: #{null}}")
    private String keyStorePath;
    @Value("${application.url}")
    private String url;

    public IntegrationProviderService(RequestPrepareComponent requestPrepareComponent, OrderResponseService orderResponseService, OrderResponseMapper orderResponseMapper) {
        this.requestPrepareComponent = requestPrepareComponent;
        this.orderResponseService = orderResponseService;
        this.orderResponseMapper = orderResponseMapper;
    }

    public OrderResponse callOutService(OrderRequest orderRequest) {
        try {
            JAXBElement<ProductType> requestBody = requestPrepareComponent.createRequestModel(orderRequest);
            StringWriter sw = new StringWriter();
            JAXBContext jaxbContext = newInstance(ProductType.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(requestBody, sw);

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream("config/tls/keystore.p12"), "password".toCharArray());
            SSLContext sslContext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, "password".toCharArray())
                    .build();

            try (CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build()) {
                HttpPost postRequest = new HttpPost(url);
                StringEntity input = new StringEntity(sw.toString(), UTF_8);
                input.setContentType(TEXT_XML_VALUE);
                postRequest.setEntity(input);
                try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                    String xml = EntityUtils.toString(response.getEntity());

                    // Проверяем подпись
                /*CertificateFactory cf = CertificateFactory.getInstance("X.509");
                for (Certificate certificate : cf.generateCertificates(is)) {
                    X509Certificate cert509 = (X509Certificate) certificate;
                    p12.setCertificateEntry("crypto", cert509);
                    isTrustedCertificate(cert509, p12);
                }*/

                    // Распарсим XML, для этого необходимо удалить лишние символы из подписи
                    String result = xml.substring(
                            xml.indexOf("<?xml version=\"1.0\" encoding=\"windows-1251\"?>"),
                            xml.indexOf("</product>") + "</product>".length()
                    );
                    ProductType productType = jaxbContext.createUnmarshaller().unmarshal(new StreamSource(new ByteArrayInputStream(result.getBytes(UTF_8))), ProductType.class).getValue();
                /*if (!productType.getPreply().getErr() != null) {
                    // Ошибку писать может в бд
                    return;
                }*/
                    OrderResponse orderResponse = orderResponseMapper.dtoToDao(productType.getPreply().getPcr().getReasons());
                    orderResponse.setOrderRequest(orderRequest);
                    orderResponseService.save(orderResponse);
                    return orderResponse;
                }
            }
        } catch (Exception e) {
            log.error("Process call integration service failed: {}", e);
            throw new BadRequestAlertException("Process call integration service failed", ENTITY_NAME, "outcallfailed");
        }
    }

    private boolean isTrustedCertificate(X509Certificate cert, KeyStore keyStore) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        if (keyStore == null) {
            return true;
        }
        boolean trusted = false;
        try {
            if (cert != null) {
                String alias = keyStore.getCertificateAlias(cert);
                if (alias != null) {
                    trusted = true;
                }
            }
        } catch (KeyStoreException e) {

        }
        return trusted;
    }
}
