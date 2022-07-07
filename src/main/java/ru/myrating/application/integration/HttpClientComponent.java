package ru.myrating.application.integration;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static java.security.KeyStore.getInstance;
import static org.apache.http.impl.client.HttpClients.custom;

@Component
public class HttpClientComponent {
    private final Logger log = LoggerFactory.getLogger(HttpClientComponent.class);
    @Value("${server.ssl.key-store-type: #{null}}")
    private String keyStoreType;
    @Value("${server.ssl.key-store-password: #{null}}")
    private String keyStorePassword;
    @Value("${server.ssl.key-store: #{null}}")
    private String keyStorePath;
    private KeyStore keyStore;

    public void init() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        keyStore = getInstance(keyStoreType);
        keyStore.load(new FileInputStream(keyStorePath), keyStorePassword.toCharArray());
    }

    public SSLContext getSslContext() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, CertificateException, IOException {
        init();
        return SSLContexts.custom()
                .loadKeyMaterial(keyStore, keyStorePassword.toCharArray())
                .build();
    }

    public CloseableHttpClient getHttpClientWithSsl() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, CertificateException, IOException {
        return custom().setSSLContext(getSslContext()).build();
    }

    public CloseableHttpClient getHttpClientWithOutSsl() {
        return custom().build();
    }

    public boolean isTrustedCertificate(byte[] stream) throws CertificateException, KeyStoreException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        for (Certificate certificate : cf.generateCertificates(new ByteArrayInputStream(stream))) {
            X509Certificate cert509 = (X509Certificate) certificate;
            return isTrustedCertificate(cert509, keyStore);
        }
        return false;
    }

    private boolean isTrustedCertificate(X509Certificate cert, KeyStore keyStore) throws io.jsonwebtoken.io.IOException {
        if (keyStore == null) {
            log.error("KeyStore is empty");
            return false;
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
            log.error("KeyStoreException: " + e);
        }
        return trusted;
    }
}
