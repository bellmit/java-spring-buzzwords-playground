package pl.piotrmacha.blog.cqrs;

import org.springframework.stereotype.Component;

@Component
final class SimpleCommandBus implements CommandBus, QueryBus {
    private HandlerRepository repository;

    SimpleCommandBus(HandlerRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T> void send(T command) {
        send(command, new Confirmation<>());
    }

    @Override
    public <T, R> void send(T command, Confirmation<R> confirmation) {
        repository.getByCommand(command.getClass())
                .forEach(proxy -> proxy.call(command, confirmation));
    }

    @Override
    public <T,R> R query(T query) {
        return repository.getByQuery(query.getClass()).query(query);
    }
}
