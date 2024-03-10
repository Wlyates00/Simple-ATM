import java.awt.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * A screen that can take input as well as display output
 * based on what the user chooses
 */
public class Touchscreen {
    private Scanner scanner;

    /**
     *
     * @param aScanner parses text from character-input stream
     */
    public Touchscreen(Scanner aScanner){
        scanner = aScanner;
    }

    /**
     * A method to display text and numbers to the screen
     *
     * @param output either user options and text, as well as numbers
     * @param <T> A generic type to allow for the same display method for digits and letters
     */
    public <T> void display(T output){
        System.out.println(output);
    }

    /**
     * A continuous loop taking input for the program
     *
     * @param c the connection that connects to the ATM and BankingSystem, which controls
     *          the state of the program
     */
    public void update(Connection c){
        boolean run = true;
        while (run){
            String input = scanner.nextLine();
            if (input.length() != 0) {
                if (input.equalsIgnoreCase("q")) {
                    c.startConnection();
                }
                c.atmState(input);
            }
            else {
                System.out.println("You must enter something!");
            }
        }
    }
}
