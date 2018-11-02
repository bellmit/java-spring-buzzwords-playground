package pl.piotrmacha.blog.authentication.domain;

import org.springframework.security.crypto.bcrypt.BCrypt;
import pl.piotrmacha.blog.security.DisposableString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
final class PasswordHash {
    @Column(name = "person_password_hash", nullable = false)
    private String hash;

    public void verify(DisposableString password) {
        String passwordString = new String(password.chars());
        password.dispose();
        BCrypt.checkpw(passwordString, hash);
    }

    static PasswordHash fromPlaintextPassword(DisposableString password) {
        PasswordHash self = new PasswordHash();
        String passwordString = new String(password.chars());
        self.hash = BCrypt.hashpw(passwordString, BCrypt.gensalt());
        password.dispose();
        return self;
    }
}
