package interpreter.model.type;
import static org.junit.jupiter.api.Assertions.*;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
class TypeTest {
    @Test
    void intDefaultValueIsZero() {
        assertEquals(new IntValue(0), new IntType().defaultValue());
    }
    @Test
    void boolDefaultValueIsFalse() {
        assertEquals(new BoolValue(false), new BoolType().defaultValue());
    }
    @Test
    void typesOfTheSameKindAreEqual() {
        assertEquals(new IntType(), new IntType());
        assertNotEquals(new IntType(), new BoolType());
    }
    @Test
    void typesPrintTheirKeyword() {
        assertEquals("int", new IntType().toString());
        assertEquals("bool", new BoolType().toString());
    }
}