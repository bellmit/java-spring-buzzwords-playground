package pl.piotrmacha.blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
class SecurityConfiguration {
    @Bean
    protected SecurityWebFilterChain http(ServerHttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeExchange()
                    .anyExchange()
                    .permitAll()
                    .and()
                .build();
    }
}
