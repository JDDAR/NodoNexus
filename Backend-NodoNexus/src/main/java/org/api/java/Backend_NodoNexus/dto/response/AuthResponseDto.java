package org.api.java.Backend_NodoNexus.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class AuthResponseDto {
  private String token;
  private UserDto user;

  public AuthResponseDto(String token, UserDto user) {
    this.token = token;
    this.user = user;
  }
}
