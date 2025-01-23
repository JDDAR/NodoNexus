package org.api.java.Backend_NodoNexus.services;

import org.api.java.Backend_NodoNexus.entities.Role;
import org.api.java.Backend_NodoNexus.enums.RoleList;

import org.api.java.Backend_NodoNexus.dto.NewUserDto;
import org.api.java.Backend_NodoNexus.entities.User;
import org.api.java.Backend_NodoNexus.jwt.JwtUtil;
import org.api.java.Backend_NodoNexus.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserService userService;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  @Autowired
  public AuthService(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
      JwtUtil jwtUtil, AuthenticationManagerBuilder authenticationManagerBuilder) {
    this.userService = userService;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
    this.authenticationManagerBuilder = authenticationManagerBuilder;
  }

  public String authenticate(String username, String password) {
    // Creo el token con las creedenciales proporcionadas
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
        password);
    // Autentica el usuario utilizando autenticationManager
    Authentication authResult = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    // Establece el contexto de seguridad
    SecurityContextHolder.getContext().setAuthentication(authResult);
    // Genera y devuelve el token
    return jwtUtil.generateToken(authResult);
  }

  // Metodo para registrar nuevo usuario
  public void registerUser(NewUserDto newUserDto) {

    // Verifica si el nombre ya existe
    if (userService.existsByUserName(newUserDto.getUserName())) {
      throw new IllegalArgumentException("El nombre del usuario ya existe");
    }

    // Obtiene el rol del usuario
    Role roleUser = roleRepository.findByName(RoleList.ROLE_USER)
        .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    // Creando Nuevo usuario
    User user = new User(newUserDto.getUserName(), passwordEncoder.encode(newUserDto.getPassword()), roleUser);
    // Guardando al usuario
    userService.save(user);
  }

}
