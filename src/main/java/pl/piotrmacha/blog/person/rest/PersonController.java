package pl.piotrmacha.blog.person.rest;


import org.springframework.web.bind.annotation.*;
import pl.piotrmacha.blog.cqrs.CommandBus;
import pl.piotrmacha.blog.cqrs.Confirmation;
import pl.piotrmacha.blog.cqrs.QueryBus;
import pl.piotrmacha.blog.person.command.CreatePersonCommand;
import pl.piotrmacha.blog.person.domain.Person;
import pl.piotrmacha.blog.person.query.FetchPersonById;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping(path = "person")
final class PersonController {
    private CommandBus commandBus;
    private QueryBus queryBus;

    PersonController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @PostMapping
    public Mono<Person> create(@RequestBody CreatePersonCommand command) {
        Confirmation<Person> confirmation = new Confirmation<>();
        commandBus.send(command, confirmation);
        return Mono.justOrEmpty(confirmation.get());
    }

    @GetMapping(path = "/{id}")
    public Mono<Person> create(@PathVariable("id") UUID personId) {
        return queryBus.query(new FetchPersonById(personId));
    }
}
