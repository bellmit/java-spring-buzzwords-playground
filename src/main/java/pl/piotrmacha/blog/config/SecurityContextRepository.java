package pl.piotrmacha.blog.config;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import pl.piotrmacha.blog.authentication.application.TokenAuthenticationManager;
import reactor.core.publisher.Mono;

@Component
final public class SecurityContextRepository implements ServerSecurityContextRepository {
    private TokenAuthenticationManager authenticationManager;

    public SecurityContextRepository(TokenAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (token == null) {
            return Mono.empty();
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return this.authenticationManager
                .authenticate(new PreAuthenticatedAuthenticationToken(null, token))
                .map(SecurityContextImpl::new);
    }
}
