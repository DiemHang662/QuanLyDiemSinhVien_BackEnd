package com.btl.configs;

import com.btl.filters.JwtAuthenticationFilter;
import com.btl.service.NguoiDungService;
import com.btl.components.JwtService;
import com.btl.filters.CustomAccessDeniedHandler;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfigs extends WebSecurityConfigurerAdapter {

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", "dmjydfpev",
                "api_key", "153344775849929",
                "api_secret", "soU85tUSegDS6ZqoM1l6jERP7Ug"
        );
        return new Cloudinary(config);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(nguoiDungService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow credentials (cookies, authorization headers, etc.)
        config.setAllowCredentials(true);

        // Specify allowed origins, or use "*" for allowing all origins
        config.addAllowedOriginPattern("*");  // You can specify your front-end domain here

        // Allow specific HTTP methods
        config.addAllowedMethod("*"); // Allows all HTTP methods (GET, POST, PUT, DELETE, etc.)

        // Allow specific headers
        config.addAllowedHeader("*"); // Allows all headers

        // Set the maximum age for the preflight request cache (optional)
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/dangki", "/api/auth/login").permitAll()
                .antMatchers("/api/auth/currentUser").authenticated()
                .antMatchers(HttpMethod.POST, "/api/sinhvien/{id}/upload-avatar").hasRole("SINHVIEN")
                .antMatchers(HttpMethod.GET, "/api/sinhvien/**").hasRole("GIANGVIEN")
                .antMatchers(HttpMethod.POST, "/api/sinhvien/**").hasRole("GIANGVIEN")
                .antMatchers(HttpMethod.DELETE, "/api/sinhvien/**").hasRole("GIANGVIEN")
                .antMatchers(HttpMethod.GET, "/api/monhoc/**").hasAnyRole("GIANGVIEN", "SINHVIEN")
                .antMatchers(HttpMethod.POST, "/api/monhoc/**").hasRole("GIANGVIEN")
                .antMatchers(HttpMethod.DELETE, "/api/monhoc/**").hasRole("GIANGVIEN")
                .antMatchers(HttpMethod.GET, "/api/giangvien/**").hasRole("GIANGVIEN")
                .antMatchers(HttpMethod.POST, "/api/giangvien/**").hasRole("GIANGVIEN")
                .antMatchers(HttpMethod.POST, "/api/giangvien/{id}/upload-avatar").hasRole("GIANGVIEN")
                .antMatchers(HttpMethod.DELETE, "/api/giangvien/**").hasRole("GIANGVIEN")
                .antMatchers(HttpMethod.GET, "/api/diem/**").hasAnyRole("GIANGVIEN", "SINHVIEN")
                .antMatchers(HttpMethod.POST, "/api/diem/**").hasRole("GIANGVIEN")
                .antMatchers(HttpMethod.DELETE, "/api/diem/**").hasRole("GIANGVIEN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler());
    }

}
