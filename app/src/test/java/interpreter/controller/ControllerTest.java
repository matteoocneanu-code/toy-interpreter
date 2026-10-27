package interpreter.controller;
import static org.junit.jupiter.api.Assertions.*;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.AssignStmt;
import interpreter.model.stmt.CompStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;

class ControllerTest {
    private static final IStmt EX1 = new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                    new PrintStmt(new VarExp("v"))));

    private static PrgState newState(IStmt program) {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), program);
    }

    @Test
    void oneStepFollowsTheTraceOfSection3_3() throws MyException {
        PrgState state = newState(EX1);
        Controller ctrl = new Controller(new Repository(state), false);
        String[][] expected = {
                {"{int v | (v = 2; print(v))}", "{}", "{}"},
                {"{(v = 2; print(v))}", "{v -> 0}", "{}"},
                {"{v = 2 | print(v)}", "{v -> 0}", "{}"},
                {"{print(v)}", "{v -> 2}", "{}"},
                {"{}", "{v -> 2}", "{2}"},
        };
        for (String[] step : expected) {
            ctrl.oneStep(state);
            assertEquals(step[0], state.getExeStack().toString());
            assertEquals(step[1], state.getSymTable().toString());
            assertEquals(step[2], state.getOut().toString());
        }
    }

    @Test
    void allStepRunsUntilTheStackIsEmpty() throws MyException {
        PrgState state = newState(EX1);
        new Controller(new Repository(state), false).allStep();
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void oneStepOnEmptyStackThrows() throws MyException {
        PrgState state = newState(EX1);
        Controller ctrl = new Controller(new Repository(state), false);
        ctrl.allStep();
        var ex = assertThrows(StatementException.class, () -> ctrl.oneStep(state));
        assertEquals("program state stack is empty", ex.getMessage());
    }

    @Test
    void errorsStopTheExecution() {
        IStmt program = new CompStmt(new VarDeclStmt("v", new IntType()),
                new AssignStmt("v", new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(1)),
                        new ValueExp(new IntValue(0)))));
        Controller ctrl = new Controller(new Repository(newState(program)), false);
        assertThrows(ExpressionException.class, ctrl::allStep);
    }
}