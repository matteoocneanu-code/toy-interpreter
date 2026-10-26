package interpreter.model.adt;
import interpreter.exceptions.ADTException;

public interface IStack<T> {
    void push(T v);
    T pop() throws ADTException;
    T peek() throws ADTException;
    boolean isEmpty();
}
