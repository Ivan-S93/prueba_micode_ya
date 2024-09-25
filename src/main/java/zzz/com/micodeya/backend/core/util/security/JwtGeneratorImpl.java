
package zzz.com.micodeya.backend.core.util.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtGeneratorImpl implements JwtGeneratorInterface {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String generateToken(UsuarioJwt userJwt) {
        String jwtToken = "";

        // Date expirationDate = new Date(System.currentTimeMillis() + 3600000); // 1
        // hora desde ahora
        // Date expirationDate = new Date(System.currentTimeMillis() + 60*1000); // 1
        // minuto desde ahora

        jwtToken = Jwts.builder()
                // .setSubject(userJwt.getUsr())
                // .setIssuedAt(new Date())
                .setPayload(userJwt.getPayload())
                // .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        //Actualizar ultima transaccion
        SesionJwt.ULTIMA_TRANSACCION_MAP.put(jwtToken, new Date().getTime());

        return jwtToken;
    }
}