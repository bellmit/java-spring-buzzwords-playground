package pl.piotrmacha.blog.authentication.application;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import pl.piotrmacha.blog.authentication.domain.AuthenticationTokenRepository;
import pl.piotrmacha.blog.person.domain.PersonRepository;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
final public class TokenAuthenticationManager implements ReactiveAuthenticationManager {
    private AuthenticationTokenRepository tokenRepository;
    private PersonRepository personRepository;
    private Clock clock;

    public TokenAuthenticationManager(AuthenticationTokenRepository tokenRepository,
                                      PersonRepository personRepository,
                                      Clock clock) {
        this.tokenRepository = tokenRepository;
        this.personRepository = personRepository;
        this.clock = clock;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = (String) authentication.getCredentials();

        return Mono.justOrEmpty(
                tokenRepository.findByToken(token)
                        .filter(auth -> auth.isValid(clock))
                        .map(auth -> {
                            auth.refresh(clock);
                            tokenRepository.save(auth);
                            return auth;
                        })
                        .map(auth -> new PreAuthenticatedAuthenticationToken(
                                personRepository.findById(auth.subjectId()),
                                token, grandAuthorities()
                        ))
                        .orElse((PreAuthenticatedAuthenticationToken) authentication)
        );
    }

    private Set<GrantedAuthority> grandAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }
}
