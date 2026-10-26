package interpreter.model.adt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyStackTest {
    private IStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new MyStack<>();
    }

    @Test
    void newStackIsEmpty() {
        assertTrue(stack.isEmpty());
    }

    @Test
    void popReturnsElementsInLifoOrder() throws ADTException {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void peekDoesNotRemoveTheTop() throws ADTException {
        stack.push(7);
        assertEquals(7, stack.peek());
        assertFalse(stack.isEmpty());
    }

    @Test
    void popOnEmptyStackThrows() {
        var ex = assertThrows(ADTException.class, () -> stack.pop());
        assertEquals("pop from an empty stack", ex.getMessage());
    }

    @Test
    void peekOnEmptyStackThrows() {
        assertThrows(ADTException.class, () -> stack.peek());
    }

    @Test
    void toStringShowsTopFirst() {
        stack.push(3);
        stack.push(2);
        stack.push(1);
        assertEquals("{1 | 2 | 3}", stack.toString());
    }
}