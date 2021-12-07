package com.api.helper;

import java.time.Instant;
import java.util.Date;

import com.api.model.user.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenJwt {
  static final String SECRET = "PhamMinhPhat";
  static final String ISSUER = "WebDEV";
  static final String SUBJECT = "JWT Auth";
  static final String TOKEN_PREFIX = "Bearer";
  static final String HEADER_STRING = "Authorization";

  public static String generateJwt(UserModel user) {
    String tokenServer = null;

    try {
      Date date = Common.time(60 * 60 * 4);

      String id = user.get_id();
      String email = user.getEmail();
      String fname = user.getFname();
      String lname = user.getLname();

      tokenServer = Jwts.builder().setIssuer(ISSUER).setSubject(SUBJECT).setId(id).claim("email", email)
          .claim("fname", fname).claim("lname", lname).setIssuedAt(Date.from(Instant.now())).setExpiration(date)
          .signWith(SignatureAlgorithm.HS256, SECRET.getBytes("UTF-8")).compact();
    } catch (Exception e) {
      System.out.println(e);
    }

    return tokenServer;
  }

  public static Claims checkJwt(String token) throws Exception {
    Claims claims = null;

    try {
      claims = Jwts.parser().setSigningKey(SECRET.getBytes("UTF-8")).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
          .getBody();
    } catch (Exception e) {
      throw e;
    }

    return claims;
  }
}
