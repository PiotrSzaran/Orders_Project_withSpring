package pl.szaran.shop_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.szaran.shop_spring.exceptions.ExceptionCode;
import pl.szaran.shop_spring.exceptions.MyException;
import pl.szaran.shop_spring.model.Token;
import pl.szaran.shop_spring.repository.TokenRepository;
import pl.szaran.shop_spring.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Value("${token.expiration.time.minutes}")
    private Integer tokenValidationTimeInMinutes;

    public Token prepareToken(Long userId) {

        if (userId == null) {
            throw new MyException(ExceptionCode.SERVICE, "TOKEN SERVICE: prepareToken() - user id is null");
        }

        var user = userRepository
                .findById(userId)
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "TOKEN SERVICE: prepareToken() - no user with id"));

        var token = Token
                .builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expirationDateTime(LocalDateTime.now().plusMinutes(tokenValidationTimeInMinutes))
                .build();

        return tokenRepository.save(token);
    }

    public Long validateToken(String token) {

        if (token == null) {
            throw new MyException(ExceptionCode.SERVICE, "TOKEN SERVICE: validateToken() - token is null");
        }

        var tokenWithUserID = tokenRepository
                .findByToken(token)
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "TOKEN SERVICE: validateToken() - token is incorrect"));

        if (tokenWithUserID.getExpirationDateTime().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(tokenWithUserID); //usuwam token po upływie ważności tokena
            throw new MyException(ExceptionCode.SERVICE, "TOKEN SERVICE: validateToken() - token has been expired");
        }

        return tokenWithUserID.getUser().getId();
    }

    public void deleteToken(String token) {

        if (token == null) {
            throw new MyException(ExceptionCode.SERVICE, "TOKEN SERVICE: deleteToken() - token is null");
        }

        var tokenWithUserID = tokenRepository
                .findByToken(token)
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "TOKEN SERVICE: deleteToken() - token is incorrect"));

        tokenRepository.delete(tokenWithUserID);
    }

    public Long activateUser(String token) {

        var userID = validateToken(token);
        var user = userRepository.findById(userID).orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "TOKEN SERVICE: activateUser() - error when finding user by id"));
        user.setEnabled(true);
        System.out.println("Aktywowano użytkownika " + user.getUsername());
        deleteToken(token); //usuwam token po udanej aktywacji uzytkownika
        System.out.println("Usunięto token aktywacyjny dla " + user.getEmail());
        return userRepository
                .save(user)
                .getId();
    }
}
