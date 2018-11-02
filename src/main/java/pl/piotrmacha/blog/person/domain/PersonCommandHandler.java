package pl.piotrmacha.blog.person.domain;

import org.springframework.stereotype.Component;
import pl.piotrmacha.blog.cqrs.CommandHandler;
import pl.piotrmacha.blog.cqrs.CommandQueryAggregate;
import pl.piotrmacha.blog.cqrs.Confirmation;
import pl.piotrmacha.blog.person.command.CreatePersonCommand;

@Component
final public class PersonCommandHandler implements CommandQueryAggregate {
    private PersonRepository repository;

    PersonCommandHandler(PersonRepository repository) {
        this.repository = repository;
    }

    @CommandHandler
    public void handle(CreatePersonCommand command, Confirmation<Person> confirmation) {
        Person person = new Person(command);
        repository.save(person);
        confirmation.confirm(person);
    }
}
