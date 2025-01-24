package org.api.java.Backend_NodoNexus.controllers;

import javax.validation.Valid;

import org.api.java.Backend_NodoNexus.dto.LoginUserDto;
import org.api.java.Backend_NodoNexus.dto.NewUserDto;
import org.api.java.Backend_NodoNexus.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body("Revise sus credenciales");
    }
    try {
      String jwt = authService.authenticate(loginUserDto.getUserName(), loginUserDto.getPassword());
      return ResponseEntity.ok(jwt);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // Si hay errores, devolvemos un mensaje claro
      return ResponseEntity.badRequest().body("Revise los campos: " + bindingResult.getFieldErrors());
    }
    try {
      String response = authService.registerUser(newUserDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/check-auth")
  public ResponseEntity<String> checkAuth() {
    return ResponseEntity.ok().body("Autenticado");
  }

}
