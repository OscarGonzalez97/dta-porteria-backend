package com.roshka.dtaporteria.config;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.roshka.dtaporteria.dto.UserDTO;
import com.roshka.dtaporteria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



import java.util.ArrayList;

import java.util.List;

@Configuration
public class ApplicationConfig {
    @Autowired
    UserService userService;
    @Autowired
    FirebaseAuth auth;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserRecord userRecord = null;
                try {
                    userRecord = auth.getUser(username);
                } catch (FirebaseAuthException e) {
                    throw new RuntimeException(e);
                }
                UserDTO userDTO = userService.getById(userRecord.getEmail());

                if (userDTO == null) {
                    throw new UsernameNotFoundException("Usuario no encontrado");
                }
                List<SimpleGrantedAuthority> rol = new ArrayList<SimpleGrantedAuthority>();
                rol.add(new SimpleGrantedAuthority(userDTO.getRol()));
                System.out.println(rol);
                return new UserRecordCustom(username, userRecord.getEmail(), true, rol);
            }
        };
    }


}
