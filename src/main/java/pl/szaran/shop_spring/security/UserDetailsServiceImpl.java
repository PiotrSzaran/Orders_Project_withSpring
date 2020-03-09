package pl.szaran.shop_spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.szaran.shop_spring.exceptions.ExceptionCode;
import pl.szaran.shop_spring.exceptions.MyException;
import pl.szaran.shop_spring.repository.UserRepository;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Qualifier("customUserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var userFromDB = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new MyException(ExceptionCode.REPOSITORY, "UserDetailsServiceImpl, loadUserByUsername() - username " + username + " doesn't exist"));

        System.out.println(userFromDB.getUsername() + " has successful logged in");

        return new User(
                userFromDB.getUsername(),
                userFromDB.getPassword(),
                userFromDB.getEnabled(), true, true, true,
                Stream.of(userFromDB.getRole().getFullName()).map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }
}