package org.curso.data.jpa.ejemplo.springjpa.services;

import org.curso.data.jpa.ejemplo.springjpa.entities.Role;
import org.curso.data.jpa.ejemplo.springjpa.entities.Usuario;
import org.curso.data.jpa.ejemplo.springjpa.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userRepository.getUserByUsername(username);
        if (user == null) {
            log.error("El usario no existe" + username);
            throw new UsernameNotFoundException("El usario no existe" + username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role rol : user.getRoles()) {
            log.info("Role usuario: " + rol.getNombre());
            authorities.add(new SimpleGrantedAuthority(rol.getNombre()));
        }

        if (authorities.isEmpty()) {
            log.error("El usuario " + username + " no tiene roles asignados");
            throw new UsernameNotFoundException("El usario " + username + " no tiene roles asiganados");
        }
        return new User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
    }
}
