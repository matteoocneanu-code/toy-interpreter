package interpreter.model.stmt;
import static org.junit.jupiter.api.Assertions.*;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import org.junit.jupiter.api.Test;
class NopStmtTest {
    @Test
    void nopLeavesTheStateUnchanged() throws MyException {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new
                NopStmt());
        state.getExeStack().pop().execute(state);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals("{}", state.getSymTable().toString());
        assertEquals(0, state.getOut().size());
    }
}