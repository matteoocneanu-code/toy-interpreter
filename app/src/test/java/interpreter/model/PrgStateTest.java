package interpreter.model;
import static org.junit.jupiter.api.Assertions.*;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import org.junit.jupiter.api.Test;
class PrgStateTest {
    @Test
    void newStateHasTheProgramOnTheStackAndEmptyTables() throws MyException {
        IStmt program = new NopStmt();
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),
                program);
        assertSame(program, state.getExeStack().peek());
        assertEquals(0, state.getOut().size());
        assertEquals("{}", state.getSymTable().toString());
        assertSame(program, state.getOriginalProgram());
    }
    @Test
    void toStringShowsTheThreeStructures() {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new
                NopStmt());
        assertEquals("ExeStack: {nop}\nSymTable: {}\nOut: {}\n", state.toString());
    }
}