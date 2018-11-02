package pl.piotrmacha.blog.cqrs;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Component
final class InMemoryHandlerRepository implements HandlerRepository {
    private Map<Class, List<HandlerProxy>> database = new HashMap<>();

    InMemoryHandlerRepository(List<CommandQueryAggregate> aggregates) {
        aggregates.forEach(aggregate -> {
            Stream.of(aggregate.getClass().getDeclaredMethods())
                    .filter(method ->
                            method.isAnnotationPresent(CommandHandler.class)
                            || method.isAnnotationPresent(QueryHandler.class)
                    )
                    .filter(method -> method.getParameterCount() > 0)
                    .forEach(method -> {
                        Class type = method.getParameterTypes()[0];
                        database.putIfAbsent(type, new LinkedList<>());
                        database.get(type).add(new HandlerProxy(aggregate, method));
                    });
        });
    }

    @Override
    public Stream<HandlerProxy> getByCommand(Class command) {
        return database.getOrDefault(command, new LinkedList<>()).stream();
    }

    @Override
    public HandlerProxy getByQuery(Class query) {
        List<HandlerProxy> queries = database.getOrDefault(query, new LinkedList<>());
        if (queries.size() == 0) {
            throw new RuntimeException("No query handler");
        }

        return queries.get(0);
    }
}
