package org.api.java.Backend_NodoNexus.entities;

import org.api.java.Backend_NodoNexus.enums.RoleList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  @Enumerated(EnumType.ORDINAL) // 0,1,2....
  private RoleList name;
}
