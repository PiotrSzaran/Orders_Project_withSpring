package pl.szaran.shop_spring.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(@Qualifier("customUserDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .authorizeRequests()
                .antMatchers("/", "/index", "/security/**", "/notFound", "/css/**", "/js/**", "/webjars/**", "fragments/**").permitAll()

                .antMatchers("/admin/**")
                .hasAnyRole("ADMIN")

                .anyRequest()
                .authenticated()

                // KONFIGURACJA FORMULARZA LOGOWANIA
                .and()
                .formLogin()
                .loginPage("/security/login").permitAll() //nasza strona logowania
                .loginProcessingUrl("/app-login") //tu podajemy login i hasło
                .successHandler(myAuthenticationSuccessHandler())
                .usernameParameter("username")
                .passwordParameter("password")
                //.defaultSuccessUrl("/", true) //zakomentowane zeby użytkownicy logowali się na swoje strony z zasobami
                .failureUrl("/security/login/error")
                .permitAll()

                // KONFIGURACJA FORMULARZA WYLOGOWANIA
                .and()
                .logout().permitAll()
                .logoutUrl("/app-logout")
                .clearAuthentication(true)
                .logoutSuccessUrl("/security/login")

                // BLEDY LOGOWANIA
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())

                .and()
                .httpBasic();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.sendRedirect("/security/accessDenied");
        };
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
}
