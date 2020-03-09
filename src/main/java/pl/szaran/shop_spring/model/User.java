package pl.szaran.shop_spring.model;

import lombok.*;
import pl.szaran.shop_spring.model.enums.Role;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String email;
    private Boolean enabled;

    /**
     * Admin będzie inicjowany przy starcie aplikacji, jesli w bazie danych nie będzie użytkowników
     * Pozostałi użytkownicy domyślnie będą posiadali rangę User
     */
    @Enumerated(EnumType.STRING)
    //private final Role role = Role.USER;
    private Role role = Role.USER;

}
