package interpreter.model.stmt;

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
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssignStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        state.getExeStack().pop();
        new VarDeclStmt("v", new IntType()).execute(state);
    }

    @Test
    void assignsTheValueOfTheExpression() throws MyException {
        new AssignStmt("v", new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(2)),
                new ValueExp(new IntValue(3)))).execute(state);
        assertEquals(new IntValue(5), state.getSymTable().lookup("v"));
    }

    @Test
    void assignmentToUndeclaredVariableThrows() {
        var ex = assertThrows(StatementException.class,
                () -> new AssignStmt("x", new ValueExp(new IntValue(1))).execute(state));
        assertEquals("variable x was not declared", ex.getMessage());
    }

    @Test
    void typeMismatchThrows() {
        var ex = assertThrows(StatementException.class,
                () -> new AssignStmt("v", new ValueExp(new BoolValue(true))).execute(state));
        assertEquals("declared type of variable v and type of the assigned expression do not match",
                ex.getMessage());
    }

    @Test
    void errorsOfTheExpressionArePropagated() {
        assertThrows(ExpressionException.class,
                () -> new AssignStmt("v", new VarExp("undefined")).execute(state));
    }
}
