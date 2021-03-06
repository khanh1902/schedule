package com.laptrinhjava.LichHoc.security;

import com.laptrinhjava.LichHoc.security.jwt.AuthEntryPointJwt;
import com.laptrinhjava.LichHoc.security.jwt.AuthTokenFilter;
import com.laptrinhjava.LichHoc.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
//        configuration.setAllowedOrigins(Collections.singletonList("https://schedule-management.netlify.app"));
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://schedule-management.netlify.app"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "content-type"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();

        // C??c trang kh??ng y??u c???u login
        http.authorizeRequests().antMatchers("api/auth/**").permitAll();

        // Trang d??nh cho c??? ADMIN
        http.authorizeRequests().antMatchers("/api/test/**", "/api/room/**", "/api/course/**", "/api/home/**")
                .access("hasRole('ADMIN')");
         // Trang d??nh cho c??? ADMIN v?? USER
//        http.authorizeRequests().antMatchers("/api/product/**", "/api/user/**", "/api/category/**", "/api/bill/**")
//                .access("hasAnyRole('USER', 'ADMIN')");

        http.httpBasic().and().csrf().disable();

        http.authorizeRequests().and().formLogin()//
                .loginProcessingUrl("/j_spring_security_login")//
                .loginPage("/login")//
                .defaultSuccessUrl("/admin")//
                .failureUrl("/login?message=error")//
                .usernameParameter("userName")//
                .passwordParameter("password")//

                // C???u h??nh cho Logout Page.
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        // Th??m m???t l???p Filter ki???m tra jwt
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}

