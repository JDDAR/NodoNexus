package org.api.java.Backend_NodoNexus.services;

import java.util.Collections;

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

  public boolean existsByUserName(String username) {
    return userRepository.existsByUserName(username);
  }

  // Metodo para guardar el nuevo usuario en la base de datos
  public void save(User user) {
    userRepository.save(user);
  }

}
