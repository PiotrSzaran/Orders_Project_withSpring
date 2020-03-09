package pl.szaran.shop_spring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.szaran.shop_spring.model.enums.Role;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String passwordConfirmation;
    private String email;
    private Boolean enabled;
    @Enumerated(EnumType.STRING)
    private Role role;
}
