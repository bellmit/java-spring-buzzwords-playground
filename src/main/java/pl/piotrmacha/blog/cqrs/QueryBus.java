package pl.piotrmacha.blog.cqrs;

public interface QueryBus {
    <T,R> R query(T query);
}
