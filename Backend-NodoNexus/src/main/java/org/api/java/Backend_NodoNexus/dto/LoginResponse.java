package org.api.java.Backend_NodoNexus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class LoginResponse {
  private String token;
  private String username;
  private String role;
}
