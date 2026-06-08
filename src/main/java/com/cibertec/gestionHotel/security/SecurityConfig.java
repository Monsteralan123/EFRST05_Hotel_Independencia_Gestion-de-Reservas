package com.cibertec.gestionHotel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();    
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable()) 
            
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/usuarios/registrar").permitAll()
                    .requestMatchers("/api/usuarios/login").permitAll()
                    
                    .requestMatchers("/api/habitaciones/crear", "/api/habitaciones/editar/**").hasRole("ADMINISTRADOR")
                    .requestMatchers("/api/servicios-adicionales/**").hasRole("ADMINISTRADOR")
                    .requestMatchers("/api/estacionamientos/crear").hasRole("ADMINISTRADOR")
                    
                    .requestMatchers("/api/habitaciones/listar-disponibles").hasAnyRole("ADMINISTRADOR", "CLIENTE")
                    .requestMatchers("/api/reservas/**").hasAnyRole("ADMINISTRADOR", "CLIENTE")
                    .requestMatchers("/api/pagos/**").hasAnyRole("ADMINISTRADOR", "CLIENTE")
                    
                    .anyRequest().authenticated()
            )
            
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
}