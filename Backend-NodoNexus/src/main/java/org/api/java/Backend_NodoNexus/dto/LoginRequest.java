package org.api.java.Backend_NodoNexus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class LoginRequest { // Datos de entrada
  private String username;
  private String password;
}
