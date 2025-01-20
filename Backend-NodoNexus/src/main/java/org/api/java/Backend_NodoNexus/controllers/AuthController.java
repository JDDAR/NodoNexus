package org.api.java.Backend_NodoNexus.controllers;

import org.api.java.Backend_NodoNexus.dto.LoginRequest;
import org.api.java.Backend_NodoNexus.dto.LoginResponse;
import org.api.java.Backend_NodoNexus.services.AuthService;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    try {
      LoginResponse response = authService.authenticate(loginRequest);
      return ResponseEntity.ok(response);
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
          Map.of("Error", "Usuario o contraseña incorrecta"));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
          Map.of("error", "Error interno del servidor: " + e.getMessage()));
    }
  }
}
