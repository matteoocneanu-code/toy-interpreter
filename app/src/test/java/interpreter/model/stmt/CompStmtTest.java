package interpreter.model.stmt;
import static org.junit.jupiter.api.Assertions.*;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import org.junit.jupiter.api.Test;
class CompStmtTest {
    @Test
    void pushesSecondThenFirst() throws MyException {
        IStmt first = new NopStmt();
        IStmt second = new NopStmt();
        IStmt comp = new CompStmt(first, second);
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), comp);
        state.getExeStack().pop().execute(state);
        assertSame(first, state.getExeStack().pop());
        assertSame(second, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }
}