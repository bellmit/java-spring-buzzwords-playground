package pl.piotrmacha.blog.authentication.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "authentication_token")
final public class AuthenticationToken {
    @Id
    @Column(name = "authentication_token")
    @JsonProperty
    private String token;

    @Column(name = "authentication_subject_id")
    private UUID subjectId;

    @Column(name = "authentication_expires_at")
    @JsonProperty
    private Instant expiresAt;

    public AuthenticationToken() {}

    public AuthenticationToken(UUID subjectId, SecureRandom random, Clock clock) {
        this.token = random.ints(255, 0, 62)
                .mapToObj("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
        this.subjectId = subjectId;
        refresh(clock);
    }

    public UUID subjectId() {
        return subjectId;
    }

    public boolean isValid(Clock clock) {
        return expiresAt.isAfter(clock.instant());
    }

    public void refresh(Clock clock) {
        expiresAt = clock.instant().plus(Duration.ofHours(1));
    }
}
