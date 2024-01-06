package com.blinder.api.user.security.config;

import com.blinder.api.common.Constants;
import com.blinder.api.user.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private static final String[] AUTH_WHITELIST = { //
            "/api/v1/h2-console", //
            "/api/v1/h2-console/**", //
            "/v3/api-docs/**", //
            "/swagger-ui/**", //
            "/swagger-ui.html", //
            "/swagger-ui/index.html", //
            "/api/v1/webjars/**", //
            "/api/v1/graphiql", //
            "/api/v1/api/graphql", //
    };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests()
                .requestMatchers(AUTH_WHITELIST).anonymous()
                .requestMatchers("/**").permitAll()
                // GET
                //.requestMatchers(HttpMethod.GET, "/users").hasAuthority(Constants.Roles.ADMIN)
                .requestMatchers(HttpMethod.GET, "/users/**", "/hobbies", "/genders", "/filter",
                        "/possibleMatches", "/auth/**", "/tvSeries", "/characteristics", "/musics",
                        "/musicCategories", "/movies", "movieCatehories", "/locations", "/images").permitAll()
                .requestMatchers(HttpMethod.GET, "/roles", "/reports").hasAuthority(Constants.Roles.ADMIN)
                //.requestMatchers(HttpMethod.GET, "/users").hasRole(Constants.Roles.ADMIN)
                // PUT
                .requestMatchers(HttpMethod.PUT, "/users", "/filter", "/reports").permitAll()
                .requestMatchers(HttpMethod.PUT, "/hobbies", "/roles",
                        "/genders").hasAuthority(Constants.Roles.ADMIN)
                // POST
                .requestMatchers(HttpMethod.POST, "/reports", "/possibleMatches",
                        "/auth/**", "/images").permitAll()
                .requestMatchers(HttpMethod.POST, "/users", "/roles", "/hobbies",
                        "/genders").hasAuthority(Constants.Roles.ADMIN)
                // PATCH
                .requestMatchers(HttpMethod.PATCH, "/users", "/characteristics").permitAll()
                // DELETE
                .requestMatchers(HttpMethod.DELETE, "/users", "/characteristics").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/roles", "/reports",
                        "/hobbies", "/genders").hasAuthority(Constants.Roles.ADMIN)
                /*
                .requestMatchers(HttpMethod.GET,"/roles", "/genders").permitAll()
                .requestMatchers("/roles","/roles").hasAuthority(Constants.Roles.ADMIN)
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/v1/images/**").permitAll()
                .requestMatchers("/api/v1/admins/**").hasAuthority(Constants.Roles.ADMIN)
                .requestMatchers("/api/v1/trainers/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/pokemons/**").permitAll()
                .requestMatchers("/api/v1/pokemons/**").hasAnyAuthority(Constants.Roles.ADMIN)
                .requestMatchers(HttpMethod.GET,"/api/v1/types/**").permitAll()
                .requestMatchers("/api/v1/types/**").hasAnyAuthority(Constants.Roles.ADMIN)*/
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
