package interpreter.view;
import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.repository.Repository;
import interpreter.view.Examples.Example;
import java.util.List;
import java.util.Scanner;

public class TextMenu {
    private final List<Example> examples;
    private final Scanner in = new Scanner(System.in);
    private Example selected;

    public TextMenu(List<Example> examples) {
        this.examples = examples;
    }

    public void show() {
        while (true) {
            System.out.println();
            System.out.println("Selected program: " + (selected == null ? "none" : selected.source()));
            System.out.println("1. Select a program");
            System.out.println("2. Run the selected program");
            System.out.println("0. Exit");
            String option = readLine("> ");
            if (option == null) return;
            switch (option) {
                case "1" -> selectProgram();
                case "2" -> runSelected();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid option: " + option);
            }
        }
    }

    private void selectProgram() {
        for (int i = 0; i < examples.size(); i++)
            System.out.println("  " + (i + 1) + ". " + examples.get(i).source());
        String line = readLine("Program number: ");
        if (line == null) return;
        try {
            int index = Integer.parseInt(line) - 1;
            if (index < 0 || index >= examples.size()) {
                System.out.println("There is no program with number " + line);
                return;
            }
            selected = examples.get(index);
        } catch (NumberFormatException e) {
            System.out.println("Not a number: " + line);
        }
    }

    private void runSelected() {
        if (selected == null) {
            System.out.println("Select a program first.");
            return;
        }
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),
                selected.program());
        Controller controller = new Controller(new Repository(state), true);
        try {
            controller.allStep();
            System.out.println("Execution finished. Out: " + state.getOut());
        } catch (MyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return in.hasNextLine() ? in.nextLine().trim() : null;
    }
}