package org.api.java.Backend_NodoNexus.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idRol;

  @Column(nullable = false, unique = true)
  private String nameRol;
  private String descripcion;

  @OneToMany(mappedBy = "idRolUser")
  private List<User> listUser;

}
