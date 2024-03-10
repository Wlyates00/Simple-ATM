import java.util.Scanner;

public class Main {

    public static final String INSTRUCTIONS =
            "Welcome to the ATM machine. \n" +
            "You will interact with this ATM through the use of a touchscreen. \n" +
            "The ATM will accept users inputs through the keyboard stimulating the press of buttons on a touch" +
                    "screen. \n" +
            "The ATM will prompt for 5 different inputs throughout the entire program: \n" +
            "- Single digit inputs followed by ENTER will be how the users will choose an option from the menus. \n" +
            "- Account number is a 8 digit inputs followed by ENTER will be what the user will enter to access their account. \n" +
            "- Operator password is a 9 letter password followed by ENTER. \n" +
            "- Dollar amount is a double followed by ENTER for how much money you want in total for the transaction. \n" +
            "- Bill amount is the quantity of one type of bill followed by ENTER. \n" +
            "Additionally you can type \'q\' or \'Q\' followed by ENTER. \n" +
            "\n" +
                    "ATM OPERATOR PASSWORD: SecureATM\n" +
                    "\n" +
                    "List of Accounts:\n" +
                    "\n" +
                    "- 00000000 ......... Checking Account\n" +
                    "- 11111111 ......... Checking Account\n" +
                    "- 22222222 ......... Checking Account\n" +
                    "- 33333333 ......... Checking Account\n" +
                    "- 44444444 ......... Checking Account\n" +
                    "- 55555555 ......... Checking Account\n" +
                    "- 66666666 ......... Checking Account\n" +
                    "- 77777777 ......... Checking Account\n" +
                    "- 88888888 ......... Checking Account\n" +
                    "- 99999999 ......... Checking Account\n" +
                    "- 10101010 ......... Savings Account" + "\n\n";

    public static void main(String[] args) {

        // Output program instruction to the user
        outputUserInstruction();

        Scanner scanner = new Scanner(System.in);
        Touchscreen tc = new Touchscreen(scanner);
        BankingSystem bs = new BankingSystem();
        ATM atm = new ATM();
        Connection c = new Connection(atm, tc, bs);

        tc.update(c);
    }

    private static void outputUserInstruction(){
        System.out.println(INSTRUCTIONS);
    }
}