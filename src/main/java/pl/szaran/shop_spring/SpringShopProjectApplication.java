package pl.szaran.shop_spring;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.szaran.shop_spring.model.User;
import pl.szaran.shop_spring.model.enums.Role;
import pl.szaran.shop_spring.repository.UserRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringShopProjectApplication {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringShopProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(){
        return args -> {
            if (userRepository.count() == 0) {
                User user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                user.setEmail("test@test.pl"); //this should be some working email address ;)
                user.setEnabled(true);
                user.setRole(Role.ADMIN);
                userRepository.save(user);
            }
        };
    }

}
