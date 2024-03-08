package com.ahcloud.edas.pulsar.core.infrastructure.pulsar.jwt.impl;

import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.jwt.JwtService;
import com.ahcloud.edas.pulsar.core.infrastructure.util.AuthTokenUtils;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.common.util.RelativeTimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/20 15:10
 **/
@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${edas.pulsar.jwt.broker.token.mode}")
    private String jwtBrokerTokenMode;

    @Value("${edas.pulsar.jwt.broker.secret.key}")
    private String jwtBrokerSecretKey;

    @Value("${edas.pulsar.jwt.broker.private.key}")
    private String jwtBrokerPrivateKey;

    @Value("${edas.pulsar.jwt.broker.public.key}")
    private String jwtBrokerPublicKey;

    private final static String SECRET_MODE = "SECRET";
    private final static String PRIVATE_MODE = "PRIVATE";

    @Override
    public String createBrokerToken(String role, String expiryTime) {
        Key signingKey;
        if (StringUtils.equals(jwtBrokerTokenMode, SECRET_MODE)) {
            signingKey = decodeBySecretKey();
        } else if (StringUtils.equals(jwtBrokerTokenMode, PRIVATE_MODE)) {
            signingKey = decodeByPrivateKey();
        } else {
            log.info("Default disable JWT auth, please set jwt.broker.token.mode.");
            return null;
        }
        if (signingKey == null) {
            log.error("JWT Auth failed, signingKey is not empty");
            return null;
        }
        Optional<Date> optExpiryTime = Optional.empty();
        if (expiryTime != null) {
            long relativeTimeMillis = TimeUnit.SECONDS
                    .toMillis(RelativeTimeUtil.parseRelativeTimeInSeconds(expiryTime));
            optExpiryTime = Optional.of(new Date(System.currentTimeMillis() + relativeTimeMillis));
        }
        return AuthTokenUtils.createToken(signingKey, role, optExpiryTime);
    }


    private Key decodeBySecretKey() {
        try {
            byte[] encodedKey = AuthTokenUtils.readKey(jwtBrokerSecretKey);
            return AuthTokenUtils.decodeSecretKey(encodedKey);
        } catch (IOException e) {
            log.error("Decode failed by secrete key, error: {}", e.getMessage());
            return null;
        }
    }

    private Key decodeByPrivateKey() {
        try {
            byte[] encodedKey = AuthTokenUtils.readKey(jwtBrokerPrivateKey);
            SignatureAlgorithm algorithm = SignatureAlgorithm.RS256;
            return AuthTokenUtils.decodePrivateKey(encodedKey, algorithm);
        } catch (IOException e) {
            log.error("Decode failed by private key, error: {}", e.getMessage());
            return null;
        }
    }
}
