package pl.piotrmacha.blog.person.query;

import org.springframework.stereotype.Component;
import pl.piotrmacha.blog.cqrs.CommandQueryAggregate;
import pl.piotrmacha.blog.cqrs.QueryHandler;
import pl.piotrmacha.blog.person.domain.Person;
import pl.piotrmacha.blog.person.domain.PersonRepository;
import reactor.core.publisher.Mono;

@Component
final public class FetchPersonByIdHandler implements CommandQueryAggregate {
    private PersonRepository repository;

    public FetchPersonByIdHandler(PersonRepository repository) {
        this.repository = repository;
    }

    @QueryHandler
    public Mono<Person> handle(FetchPersonById query) {
        return Mono.justOrEmpty(repository.findById(query.personId()));
    }
}
