package br.com.backend.blog_gateway.filter;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class JwtValidator {

    @Value("${jwt.key}")
    private String jwtKey;

    public boolean isValid(String jwt) throws IOException {
        try {
            jwt = extractToken(jwt);
            SignedJWT signedJWT = SignedJWT.parse(jwt);
            JWSVerifier jwsVerifier = new MACVerifier(jwtKey.getBytes(StandardCharsets.UTF_8));
            if (!signedJWT.verify(jwsVerifier)) {
                log.error("Assinatura inválida");
                return false;
            }
            Jwts.parser().setSigningKey(jwtKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            log.error("Token inválido : {}" + e.getMessage());
            return false;
        }
    }

    private String extractToken(String authToken) {
        if (authToken.toLowerCase().startsWith("bearer")) {
            return authToken.substring("bearer ".length());
        }
        return authToken;
    }

}
