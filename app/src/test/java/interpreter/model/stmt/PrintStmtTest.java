package interpreter.model.stmt;
import static org.junit.jupiter.api.Assertions.*;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrintStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void appendsValuesToOutInOrder() throws MyException {
        new PrintStmt(new ValueExp(new IntValue(1))).execute(state);
        new PrintStmt(new ValueExp(new IntValue(2))).execute(state);
        assertEquals(2, state.getOut().size());
        assertEquals("{1, 2}", state.getOut().toString());
    }

    @Test
    void errorsOfTheExpressionArePropagated() {
        assertThrows(ExpressionException.class, () -> new PrintStmt(new VarExp("x")).execute(state));
        assertEquals(0, state.getOut().size());
    }
}