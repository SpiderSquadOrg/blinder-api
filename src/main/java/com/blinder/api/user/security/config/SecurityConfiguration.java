package com.blinder.api.user.security.config;

import com.blinder.api.user.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private static final String[] AUTH_WHITELIST = {
            "/api/v1/h2-console",
            "/api/v1/h2-console/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/api/v1/webjars/**",
            "/api/v1/graphiql",
            "/api/v1/api/graphql",
    };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .authorizeRequests()
                .requestMatchers(AUTH_WHITELIST).permitAll()

                // USER
                .requestMatchers(HttpMethod.GET, "/users/{userId}").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.GET, "/users/search").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.GET, "/users/chats/{chatId}").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.GET, "/users/{userId}/images").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.GET, "/users/filter/{userId}").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.POST, "/users").hasAuthority("admin")
                .requestMatchers(HttpMethod.PUT, "/users/{userId}").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.DELETE, "/users/{userId}").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.PATCH, "/users/{userId}/ban").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.PATCH, "/users/{userId}/unban").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.PATCH, "/users/{userId}/block").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.PATCH, "/users/{userId}/unblock").permitAll()  // TO DO: Stronger security

                // ROLE
                .requestMatchers(HttpMethod.GET, "/roles").permitAll()
                .requestMatchers(HttpMethod.GET, "/roles/{roleId}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.POST, "/roles").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.PUT, "/roles/{roleId}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.DELETE, "/roles/{roleId}").hasAnyAuthority("admin")

                // REPORT
                .requestMatchers(HttpMethod.GET, "/reports").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.GET, "/reports/search").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.GET, "/reports/{reportId}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.POST, "/reports").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.PUT, "/reports/{reportId}").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.DELETE, "/reports/{reportId}").hasAnyAuthority("admin")

                // HOBBY
                .requestMatchers(HttpMethod.GET, "/hobbies").permitAll()
                .requestMatchers(HttpMethod.GET, "/hobbies/{hobbyId}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.POST, "/hobbies").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.PUT, "/hobbies/{hobbyId}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.DELETE, "/hobbies/{hobbyId}").hasAnyAuthority("admin")

                // GENDERS
                .requestMatchers(HttpMethod.GET, "/genders").permitAll()
                .requestMatchers(HttpMethod.GET, "/genders/{genderId}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.POST, "/genders").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.PUT, "/genders/{genderId}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.DELETE, "/genders/{genderId}").hasAnyAuthority("admin")

                // FILTER
                .requestMatchers(HttpMethod.GET, "/filters/{userId}").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.GET, "/filters/byFilter/{filterId}").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.PUT, "/filters/{userId}").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.PUT, "/filters/reset/{userId}").permitAll()  // TO DO: Stronger security

                // POSSIBLE MATCH
                .requestMatchers(HttpMethod.GET, "/possibleMatches").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.GET, "/possibleMatches/{status}").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.GET, "/possibleMatches/matched").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.GET, "/possibleMatches/liked").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.GET, "/possibleMatches/users_who_like").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.GET, "/possibleMatches/disliked").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.POST, "/possibleMatches/like/{possibleMatchId}").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.POST, "/possibleMatches/dislike/{possibleMatchId}").permitAll()  // TO DO: Stronger security

                // Image
                .requestMatchers(HttpMethod.POST, "/images/upload").permitAll()
                .requestMatchers(HttpMethod.GET, "/images/{publicId}").permitAll()

                // AUTH
                .requestMatchers(HttpMethod.GET, "/auth/active").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()  // TO DO: Stronger security
                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()  // TO DO: Stronger security

                // CHARACTERISTICS
                .requestMatchers(HttpMethod.GET, "/characteristics").hasAnyAuthority("admin")
                .requestMatchers(HttpMethod.GET, "/characteristics/byUser").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/characteristics/musics").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/characteristics/musics/{musicId}").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/characteristics/musics/categories").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/characteristics/musics/categories/{musicCategoryId}").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/characteristics/movies").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/characteristics//movies/{movieId}").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/characteristics/movies/categories").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/characteristics/movies/categories/{movieCategoryId}").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/characteristics/tvSeries").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/characteristics/tvSeries/{tvSeriesId}").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/characteristics/tvSeries/categories").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/characteristics/tvSeries/categories/{tvSeriesCategoryId}").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/characteristics//hobby/{name}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/characteristics/hobby/{name}").permitAll()

                // TVSERIES
                .requestMatchers(HttpMethod.GET, "/tvSeries/search").permitAll()

                // TEST
                .requestMatchers(HttpMethod.GET, "/test").permitAll()

                // MUSIC
                .requestMatchers(HttpMethod.GET, "/musics/search").permitAll()
                .requestMatchers(HttpMethod.GET, "/musics/{id}").permitAll()

                // MUSIC CATEGORY
                .requestMatchers(HttpMethod.GET, "/musicCategories").permitAll()

                // MOVIE
                .requestMatchers(HttpMethod.GET, "/movies/search").permitAll()

                // MOVIE CATEGORY
                .requestMatchers(HttpMethod.GET, "/movieCategories").permitAll()

                // LOCATION
                .requestMatchers(HttpMethod.GET, "/locations/countries").permitAll()
                .requestMatchers(HttpMethod.GET, "/locations/states/country/{iso2}").permitAll()
                .requestMatchers(HttpMethod.GET, "/locations/countries/{ciso}/states/{siso}").permitAll()
                .requestMatchers(HttpMethod.GET, "/locations/countries/{ciso}").permitAll()

                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}