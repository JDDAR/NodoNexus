package org.api.java.Backend_NodoNexus.repositories;

import java.util.Optional;

import org.api.java.Backend_NodoNexus.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
