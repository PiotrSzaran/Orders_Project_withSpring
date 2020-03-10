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
}
