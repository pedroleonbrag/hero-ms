package com.pedroleon.app.config;

import com.pedroleon.app.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import tech.jhipster.config.JHipsterProperties;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final JHipsterProperties jHipsterProperties;

    public SecurityConfiguration(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz ->
                // prettier-ignore
                authz
	                .requestMatchers("/", "/index.html", "/*.js", "/*.map", "/*.css").permitAll()
	                .requestMatchers("/*.ico", "/*.png", "/*.svg", "/*.webapp").permitAll()
	                .requestMatchers("/app/**").permitAll()
	                .requestMatchers("/i18n/**").permitAll()
	                .requestMatchers("/content/**").permitAll()
	                .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/authenticate").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/file/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/file/**").permitAll()
                    //.requestMatchers(HttpMethod.GET, "/api/hero/create").permitAll()
                    .requestMatchers("/api/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    .requestMatchers("/api/**").authenticated()
                    .requestMatchers("/v3/api-docs/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    .requestMatchers("/management/health").permitAll()
                    .requestMatchers("/management/health/**").permitAll()
                    .requestMatchers("/management/info").permitAll()
                    .requestMatchers("/management/prometheus").permitAll()
                    .requestMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptions ->
                exceptions
                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                    .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        return http.build();
    }
    //	@Bean
    //	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //		http.csrf(csrf -> csrf.disable())
    //			.authorizeHttpRequests(
    //					authz -> authz
    //                  .requestMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
    //                  .requestMatchers(HttpMethod.GET, "/api/authenticate").permitAll()
    //                  .requestMatchers(HttpMethod.GET, "/api/hero/create").permitAll()
    //                  .requestMatchers("/api/**").authenticated()
    //			)
    //          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //          .exceptionHandling(exceptions ->
    //              exceptions
    //                  .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
    //                  .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
    //          )
    //		 .oauth2ResourceServer(oauth2 -> oauth2.jwt())
    //		;
    //		return http.build();
    //	}

}
