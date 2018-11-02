package pl.piotrmacha.blog.authentication.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piotrmacha.blog.authentication.application.TokenIssuer;
import pl.piotrmacha.blog.authentication.domain.AuthenticationToken;
import pl.piotrmacha.blog.utils.DisposableString;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "auth")
final public class AuthenticationController {
    private TokenIssuer tokenIssuer;

    public AuthenticationController(TokenIssuer tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    @PostMapping
    public Mono<AuthenticationToken> login(@RequestBody LoginRequest request) {
        return tokenIssuer.login(request.email, new DisposableString(request.password.toCharArray()));
    }

    public static class LoginRequest {
        @JsonProperty
        String email;

        @JsonProperty
        String password;
    }
}
