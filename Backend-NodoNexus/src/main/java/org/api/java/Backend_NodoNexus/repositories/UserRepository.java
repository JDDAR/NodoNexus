package org.api.java.Backend_NodoNexus.repositories;

import java.util.Optional;

import org.api.java.Backend_NodoNexus.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUserName(String userName);

  Optional<User> findById(String id);

  Boolean existsByUserName(String username);
}
