package pl.piotrmacha.blog.cqrs;

public interface CommandBus {
    <T> void send(T command);
    <T,R> void send(T command, Confirmation<R> confirmation);
}
