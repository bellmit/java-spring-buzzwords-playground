package pl.piotrmacha.blog.cqrs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class HandlerProxy {
    private Object object;
    private Method method;

    HandlerProxy(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    <T> void call(Object command, Confirmation<T> confirmation) {
        try {
            method.invoke(object, command, confirmation);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    <T> T query(Object query) {
        try {
            return (T) method.invoke(object, query);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
