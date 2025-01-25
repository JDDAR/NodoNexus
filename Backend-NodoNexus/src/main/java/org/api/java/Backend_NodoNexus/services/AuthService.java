package org.api.java.Backend_NodoNexus.services;

import org.api.java.Backend_NodoNexus.dto.request.NewUserDto;
import org.api.java.Backend_NodoNexus.dto.response.AuthResponseDto;
import org.api.java.Backend_NodoNexus.dto.response.UserDto;
import org.api.java.Backend_NodoNexus.entities.Role;

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

  public AuthResponseDto authenticate(String username, String password) {

    // Creo el token con las creedenciales proporcionadas
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
        password);

    // Autentica el usuario utilizando autenticationManager
    Authentication authResult = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    // Establece el contexto de seguridad
    SecurityContextHolder.getContext().setAuthentication(authResult);

    // Genera y devuelve el token
    String token = jwtUtil.generateToken(authResult);

    // Obtine la informacion del usuario:
    User user = userService.findByUserName(username)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    String role = user.getRole().getName().name();

    // Contruyendo el DTO con el token
    UserDto userDto = new UserDto(user.getId(), user.getUserName(), role);

    return new AuthResponseDto(token, userDto);

  }

  // Metodo para registrar nuevo usu
  public String registerUser(NewUserDto newUserDto) {
    try {
      // Verifica si el nombre ya existe
      if (userService.existsByUserName(newUserDto.getUserName())) {
        throw new IllegalArgumentException("El nombre del usuario ya existe");
      }
      // Obtiene el rol del usuario
      Role roleUser = roleRepository.findById(newUserDto.getIdRol())
          .orElseThrow(() -> new RuntimeException("Rol con ID " + newUserDto.getIdRol() + " no encontrado."));

      // Creando nuevo usuario
      User user = new User(newUserDto.getUserName(), passwordEncoder.encode(newUserDto.getPassword()), roleUser);
      // Guardando al usuario
      userService.save(user);

      return String.format(
          "Usuario registrado con éxito:\nID: %s\nNombre: %s\nRol: %s",
          user.getId(), // Ahora se usa %s para el ID, ya que es un String
          user.getUserName(),
          roleUser.getName().name());

    } catch (Exception e) {
      e.printStackTrace(); // Muestra la excepción completa en el log
      throw new RuntimeException("Error al registrar el usuario: " + e.getMessage());
    }
  }

}
