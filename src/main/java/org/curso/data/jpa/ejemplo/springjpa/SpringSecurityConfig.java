package org.curso.data.jpa.ejemplo.springjpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //creacion de los usuarios
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager detailsManager = new InMemoryUserDetailsManager();
        detailsManager.createUser(User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("ADMIN", "USER").build());

        detailsManager.createUser(User.withUsername("Jhon").password(passwordEncoder().encode("123")).roles("USER").build());

        return detailsManager;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz ->
                    authz.requestMatchers("/", "/css/**", "/js/**", "/listar").permitAll()
                            .requestMatchers("/uploads/**").hasAnyRole("USER")
                            .requestMatchers("/ver/**").hasRole("USER")
                            .requestMatchers("/factura/**").hasRole("ADMIN")
                            .requestMatchers("/form/**").hasRole("ADMIN")
                            .requestMatchers("/eliminar/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
                )
                .formLogin(login -> login.loginPage("/login"))
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

}
