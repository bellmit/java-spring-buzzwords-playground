package pl.piotrmacha.blog.person.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.piotrmacha.blog.authentication.domain.AuthenticationDetails;
import pl.piotrmacha.blog.person.command.CreatePersonCommand;
import pl.piotrmacha.blog.utils.DisposableString;

import javax.persistence.*;
import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "person")
final public class Person {
    @Id
    @Column(name = "person_id", nullable = false)
    @JsonProperty
    private UUID personId;

    @Column(name = "person_name", nullable = false, length = 128)
    @JsonProperty
    private String name;

    @Column(name = "person_created_at", nullable = false)
    @JsonProperty
    private Instant createdAt;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "person")
    private AuthenticationDetails authenticationDetails;

    public Person() {}

    public Person(CreatePersonCommand command) {
        this.personId = UUID.randomUUID();
        this.name = command.name;
        this.createdAt = Clock.systemDefaultZone().instant();
        this.authenticationDetails = new AuthenticationDetails(
                this, command.email, new DisposableString(command.password.toCharArray())
        );
    }

    public UUID identity() {
        return personId;
    }
}
