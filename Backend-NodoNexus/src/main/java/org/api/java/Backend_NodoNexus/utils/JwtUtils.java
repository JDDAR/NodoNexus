package org.api.java.Backend_NodoNexus.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.api.java.Backend_NodoNexus.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

  @Value("${jwt.secret}")
  private String SECRET;

  @Value("${jwt.expiration}")
  private int EXPIRATION_MS;

  private SecretKey secretKey;

  // Este metodo se va a encargar de ejecutar la inyeccion de dependencia
  // ya que sin esta la inicializacion de la constante secretKey se hace antes de
  // que el spring haya
  // completado la inyeccion de valires. Esto proboca que SECRET sea null en el
  // momento en
  // el qie se crea el secretKey

  @PostConstruct
  public void init() {
    this.secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
  }

  public String generateToken(User user) {
    return Jwts.builder()
        .setSubject(user.getUsername())
        .claim("role", user.getIdRolUser().getNameRol())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

}
