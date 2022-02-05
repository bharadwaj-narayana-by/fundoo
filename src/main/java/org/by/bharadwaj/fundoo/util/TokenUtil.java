package org.by.bharadwaj.fundoo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.by.bharadwaj.fundoo.exception.FundooException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Component
@Service
public class TokenUtil {

    @Value("${secret.token}")
    public String TOKEN_SECRET;

    public String createToken(Long id) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        JwtBuilder builder = Jwts.builder().setId(String.valueOf(id))
                //.setExpiration(new Date(System.currentTimeMillis()+(180*1000)))
                .signWith(signatureAlgorithm, DatatypeConverter.parseString(TOKEN_SECRET));
        return  builder.compact();
    }


    public String createToken(Long id, Date expirationDate) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        JwtBuilder builder = Jwts.builder().setId(String.valueOf(id))
                .setExpiration(expirationDate)
                .signWith(signatureAlgorithm, DatatypeConverter.parseString(TOKEN_SECRET));
        return  builder.compact();
    }

    public Long decodeToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseString(TOKEN_SECRET)).parseClaimsJws(token)
                    .getBody();
            return Long.parseLong(claims.getId());
        }catch (Exception e) {
            throw new FundooException(HttpStatus.BAD_REQUEST.value(),e.getMessage());
        }
    }
}
