package pl.piotrmacha.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import pl.piotrmacha.blog.authentication.application.TokenAuthenticationManager;

@EnableWebFluxSecurity
class SecurityConfiguration {
    @Bean
    protected SecurityWebFilterChain http(ServerHttpSecurity http,
                                          TokenAuthenticationManager authenticationManager,
                                          SecurityContextRepository securityContextRepository)
            throws Exception {
        return http
                .csrf().disable()
                .formLogin().disable()
                .securityContextRepository(securityContextRepository)
                .authenticationManager(authenticationManager)
                .authorizeExchange()
                    .pathMatchers(HttpMethod.POST, "/person").permitAll()
                    .pathMatchers(HttpMethod.POST, "/auth").permitAll()
                    .anyExchange().authenticated()
                .and()
                .build();
    }
}
