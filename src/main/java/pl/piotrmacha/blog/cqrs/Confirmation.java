package pl.piotrmacha.blog.cqrs;

final public class Confirmation<T> {
    private T payload;

    public void confirm(T payload) {
        this.payload = payload;
    }

    public T get() {
        return payload;
    }
}
