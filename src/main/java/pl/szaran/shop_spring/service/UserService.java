package pl.szaran.shop_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.szaran.shop_spring.exceptions.ExceptionCode;
import pl.szaran.shop_spring.exceptions.MyException;
import pl.szaran.shop_spring.model.User;
import pl.szaran.shop_spring.model.dto.UserDTO;
import pl.szaran.shop_spring.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final EmailService emailService;

    public boolean doesUserExists(String username) {
        if (username == null) {
            throw new MyException(ExceptionCode.SERVICE, " USER SERVICE, doesUserExists() - username is null");
        }

        return userRepository.findByUsername(username).isPresent();
    }

    public UserDTO findByUsername(String username) {
        if (username == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER SERVICE, findByUsername() - username is null");
        }

        return userRepository
                .findByUsername(username)
                .map(ModelMapper::fromUserToUserDTO)
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "USER SERVICE, findByUsername() - NO USER WITH USERNAME: " + username));
    }

    public boolean doesEmailExists(String email) {
        if (email == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER SERVICE, doesEmailExists - email is null");
        }

        return userRepository.findByEmail(email).isPresent();
    }

    public Long add(UserDTO userDTO) {
        if (userDTO == null) {
            throw new MyException(ExceptionCode.SERVICE, "USER SERVICE, add() - userDTO is null");
        }

        User user = ModelMapper.fromUserDTOToUser(userDTO);

        user.setEnabled(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var insertUser = userRepository.save(user);

        System.out.println("Dodano nowego doktora z loginem " + user.getUsername());

        // --------------------------------------------------------------------
        // PRZYGOTOWANIE EMAIL
        // --------------------------------------------------------------------
        var tokenWithDoctor = tokenService.prepareToken(insertUser.getId());
        System.out.println("Wygenerowano token aktywujacy konto dla " + user.getEmail());
        var messagePart1 = "Click link to activate account: ";
        var messagePart2 = "http://localhost:8092/security/account-activation?token=";
        var messagePart3 = tokenWithDoctor.getToken();
        var message = messagePart1 + messagePart2 + messagePart3;

        emailService.send(insertUser.getEmail(), "Account Activation", message);
        System.out.println("Wysłano token do " + user.getEmail());

        return insertUser.getId();
    }
}

