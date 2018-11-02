package pl.piotrmacha.blog.authentication.domain;

import pl.piotrmacha.blog.person.domain.Person;
import pl.piotrmacha.blog.utils.DisposableString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "person_authentication")
final public class AuthenticationDetails {
    @Id
    @Column(name = "person_id", nullable = false)
    private UUID personId;

    @Column(name = "person_email", nullable = false, unique = true)
    private String email;

    @Embedded
    @Basic(fetch = FetchType.LAZY)
    private PasswordHash passwordHash;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "person_id", nullable = true)
    @Basic(fetch = FetchType.LAZY)
    private Person person;

    public AuthenticationDetails() {}

    public AuthenticationDetails(Person person, String email, DisposableString password) {
        this.personId = person.identity();
        this.email = email;
        this.passwordHash = PasswordHash.fromPlaintextPassword(password);
        this.person = person;
    }

    public UUID personId() {
        return personId;
    }

    public boolean isPasswordCorrect(DisposableString password) {
        return passwordHash.verify(password);
    }
}
