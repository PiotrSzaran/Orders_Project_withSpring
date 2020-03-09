package pl.szaran.shop_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szaran.shop_spring.exceptions.ExceptionCode;
import pl.szaran.shop_spring.exceptions.MyException;
import pl.szaran.shop_spring.model.dto.UserDTO;
import pl.szaran.shop_spring.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
}

