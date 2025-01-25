package org.api.java.Backend_NodoNexus.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
public class UserDto {
  private String id;
  private String userName;
  private String role;

  public UserDto(String id, String userName, String role) {
    this.id = id;
    this.userName = userName;
    this.role = role;
  }

}
