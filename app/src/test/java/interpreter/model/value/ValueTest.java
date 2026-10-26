package interpreter.model.value;
import static org.junit.jupiter.api.Assertions.*;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;
class ValueTest {
    @Test
    void intValueHasIntType() {
        assertEquals(new IntType(), new IntValue(5).getType());
    }
    @Test
    void boolValueHasBoolType() {
        assertEquals(new BoolType(), new BoolValue(true).getType());
    }
    @Test
    void valuesAreComparedByContent() {
        assertEquals(new IntValue(3), new IntValue(3));
        assertNotEquals(new IntValue(3), new IntValue(4));
    }
    @Test
    void valuesPrintAsLiterals() {
        assertEquals("2", new IntValue(2).toString());
        assertEquals("true", new BoolValue(true).toString());
    }
}