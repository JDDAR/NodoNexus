package org.api.java.Backend_NodoNexus.services;

import org.api.java.Backend_NodoNexus.dto.LoginRequest;
import org.api.java.Backend_NodoNexus.dto.LoginResponse;
import org.api.java.Backend_NodoNexus.entities.User;
import org.api.java.Backend_NodoNexus.repositories.UserRepository;
import org.api.java.Backend_NodoNexus.utils.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtils jwtUtils;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtils = jwtUtils;
  }

  public LoginResponse authenticate(LoginRequest request) {
    User user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new RuntimeException("Contraseña incorrecta");
    }

    String token = jwtUtils.generateToken(user);

    return new LoginResponse(token, user.getUsername(), user.getIdRolUser().getNameRol());

  }

}
