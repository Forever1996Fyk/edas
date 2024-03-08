package com.ahcloud.edas.pulsar.core.infrastructure.util;

import com.google.common.io.ByteStreams;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.codec.binary.Base64;
import org.apache.pulsar.client.api.url.URL;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Optional;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/20 15:50
 **/
public class AuthTokenUtils {
    public static SecretKey createSecretKey(SignatureAlgorithm signatureAlgorithm) {
        return Keys.secretKeyFor(signatureAlgorithm);
    }

    public static SecretKey decodeSecretKey(byte[] secretKey) {
        return Keys.hmacShaKeyFor(secretKey);
    }

    public static PrivateKey decodePrivateKey(byte[] key, SignatureAlgorithm algType) throws IOException {
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(key);
            KeyFactory kf = KeyFactory.getInstance(keyTypeForSignatureAlgorithm(algType));
            return kf.generatePrivate(spec);
        } catch (Exception e) {
            throw new IOException("Failed to decode private key", e);
        }
    }


    public static PublicKey decodePublicKey(byte[] key, SignatureAlgorithm algType) throws IOException {
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(key);
            KeyFactory kf = KeyFactory.getInstance(keyTypeForSignatureAlgorithm(algType));
            return kf.generatePublic(spec);
        } catch (Exception e) {
            throw new IOException("Failed to decode public key", e);
        }
    }

    private static String keyTypeForSignatureAlgorithm(SignatureAlgorithm alg) {
        if (alg.getFamilyName().equals("RSA")) {
            return "RSA";
        } else if (alg.getFamilyName().equals("ECDSA")) {
            return "EC";
        } else {
            String msg = "The " + alg.name() + " algorithm does not support Key Pairs.";
            throw new IllegalArgumentException(msg);
        }
    }

    public static String encodeKeyBase64(Key key) {
        return Encoders.BASE64.encode(key.getEncoded());
    }

    public static String createToken(Key signingKey, String subject, Optional<Date> expiryTime) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .signWith(signingKey);

        expiryTime.ifPresent(builder::setExpiration);

        return builder.compact();
    }

    public static byte[] readKeyFromUrl(String keyConfUrl) throws IOException {
        if (keyConfUrl.startsWith("data:") || keyConfUrl.startsWith("file:")) {
            try {
                return ByteStreams.toByteArray((InputStream) new URL(keyConfUrl).getContent());
            } catch (Exception e) {
                throw new IOException(e);
            }
        } else if (Files.exists(Paths.get(keyConfUrl))) {
            // Assume the key content was passed in a valid file path
            try {
                return Files.readAllBytes(Paths.get(keyConfUrl));
            } catch (IOException e) {
                throw new IOException(e);
            }
        } else if (Base64.isBase64(keyConfUrl.getBytes())) {
            // Assume the key content was passed in base64
            try {
                return Decoders.BASE64.decode(keyConfUrl);
            } catch (DecodingException e) {
                String msg = "Illegal base64 character or Key file " + keyConfUrl + " doesn't exist";
                throw new IOException(msg, e);
            }
        } else {
            String msg = "Secret/Public Key file " + keyConfUrl + " doesn't exist";
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * 读取key
     * @param keyFile
     * @return
     * @throws IOException
     */
    public static byte[] readKey(String keyFile) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(keyFile);
        InputStream inputStream = classPathResource.getInputStream();
        return ByteStreams.toByteArray(inputStream);
    }
}
