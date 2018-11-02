package pl.piotrmacha.blog.cqrs;

import java.util.stream.Stream;

public interface HandlerRepository {
    Stream<HandlerProxy> getByCommand(Class command);
    HandlerProxy getByQuery(Class query);
}
