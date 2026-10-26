package interpreter.model.adt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyDictionaryTest {
    private IDictionary<String, Integer> dict;

    @BeforeEach
    void setUp() {
        dict = new MyDictionary<>();
    }

    @Test
    void putThenLookupReturnsTheValue() throws ADTException {
        dict.put("a", 2);
        assertTrue(dict.isDefined("a"));
        assertEquals(2, dict.lookup("a"));
    }

    @Test
    void updateChangesTheValue() throws ADTException {
        dict.put("a", 2);
        dict.update("a", 5);
        assertEquals(5, dict.lookup("a"));
    }

    @Test
    void missingKeyIsNotDefined() {
        assertFalse(dict.isDefined("x"));
    }

    @Test
    void lookupOfMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.lookup("x"));
    }

    @Test
    void putOfExistingKeyThrows() throws ADTException {
        dict.put("a", 1);
        assertThrows(ADTException.class, () -> dict.put("a", 2));
    }

    @Test
    void updateOfMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.update("x", 1));
    }

    @Test
    void toStringUsesArrowNotation() throws ADTException {
        dict.put("v", 2);
        assertEquals("{v -> 2}", dict.toString());
    }
}