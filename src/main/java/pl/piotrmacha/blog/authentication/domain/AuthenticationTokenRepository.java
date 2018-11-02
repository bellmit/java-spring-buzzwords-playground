package pl.piotrmacha.blog.authentication.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationTokenRepository extends CrudRepository<AuthenticationToken, String> {
    Optional<AuthenticationToken> findByToken(String token);
}
