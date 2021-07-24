package com.example.springsecurity_apiproductosusuarios.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//para apis va disable para web enabled
                .authorizeRequests()
                .antMatchers("/","/index.html").permitAll()
                //.antMatchers("/api/productos").hasRole("CLINTE")
                //.antMatchers("/api/productos/detalles").hasAuthority(PRODUCTO_WRITE.getPermission())
                //.antMatchers(HttpMethod.PUT,"/api/productos/**").hasAuthority(PRODUCTO_WRITE.getPermission())
                //.antMatchers(HttpMethod.POST,"/api/**").hasAnyAuthority(PRODUCTO_WRITE.getPermission(),USER_WRITE.getPermission())
                //.antMatchers(HttpMethod.DELETE,"/api/**").hasRole(UserRole.ADMIN.getRole())
                //.antMatchers(HttpMethod.DELETE,"/api/**").hasAnyRole(UserRole.ADMIN.getRole(),UserRole.CLIENT.getRole())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(); //POSTMAN
                //.formLogin() // WEB
        //            .failureForwardUrl("/login?error&prueba")
        //            .successForwardUrl("/api/productos/1")
        //        .and()
        //        .logout()
        //            .deleteCookies("JSESSIONID");

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(5);
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService(){
        UserDetails usuario1 = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("clave"))
                //.roles(UserRole.ADMIN.name())
                .authorities(UserRole.ADMIN.getGrantedAuthorities())
                .build();

        UserDetails usuario2 = User.builder()
                .username("cliente")
                .password(passwordEncoder().encode("clave"))
                .roles(UserRole.CLIENT.name())
                .build();

        UserDetails usuario3 = User.builder()
                .username("vendedor")
                .password(passwordEncoder().encode("clave"))
                .roles(UserRole.SELLER.name())
                .build();

        return new InMemoryUserDetailsManager(usuario1,usuario2,usuario3);
    }
}

