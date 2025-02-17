package org.api.java.Backend_NodoNexus.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.api.java.Backend_NodoNexus.entities.User;
import org.api.java.Backend_NodoNexus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> findByUserName(String userName) {
    return userRepository.findByUserName(userName);
  }

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    User user = userRepository.findByUserName(userName)
        .orElseThrow(() -> new UsernameNotFoundException("Usuarion no encontrado..."));
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName().toString());

    return new org.springframework.security.core.userdetails.User(
        user.getUserName(),
        user.getPassword(),
        Collections.singleton(authority));
  }

  // Verificando si el nombre de usuario existe
  public boolean existsByUserName(String username) {
    return userRepository.existsByUserName(username);
  }

  // Metodo para guardar el nuevo usuario en la base de datos
  public void save(User user) {
    userRepository.save(user);
  }

  // Obteniendo usuarios
  public List<User> findAll() {
    return userRepository.findAll();
  }

  // Buscando Usuario por ID
  public Optional<User> findById(String id) {
    return userRepository.findById(id);
  }

  // Actualizando un usuario
  public User update(String id, User updateUser) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + id));

    user.setUserName(updateUser.getUserName());
    user.setPassword(updateUser.getPassword());
    user.setRole(updateUser.getRole());

    return userRepository.save(user);
  }

}
