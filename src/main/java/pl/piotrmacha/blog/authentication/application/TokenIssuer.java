package pl.piotrmacha.blog.authentication.application;

import org.springframework.stereotype.Service;
import pl.piotrmacha.blog.authentication.domain.AuthenticationDetailsRepository;
import pl.piotrmacha.blog.authentication.domain.AuthenticationToken;
import pl.piotrmacha.blog.authentication.domain.AuthenticationTokenRepository;
import pl.piotrmacha.blog.utils.DisposableString;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.time.Clock;

@Service
final public class TokenIssuer {
    private AuthenticationDetailsRepository authenticationRepository;
    private AuthenticationTokenRepository tokenRepository;
    private SecureRandom random;
    private final Clock clock;

    public TokenIssuer(AuthenticationDetailsRepository authenticationRepository,
                       AuthenticationTokenRepository tokenRepository,
                       SecureRandom random,
                       Clock clock) {
        this.authenticationRepository = authenticationRepository;
        this.tokenRepository = tokenRepository;
        this.random = random;
        this.clock = clock;
    }

    public Mono<AuthenticationToken> login(String email, DisposableString password) {
        return Mono.justOrEmpty(
                authenticationRepository.findByEmail(email)
                        .filter(auth -> auth.isPasswordCorrect(password))
                        .map(auth -> {
                            AuthenticationToken token = new AuthenticationToken(auth.personId(), random, clock);
                            tokenRepository.save(token);
                            return token;
                        })
        );
    }
}
