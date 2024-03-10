/**
 * Extention of the Accounts class that allows for different characteristics from
 * the Savings Account class
 */
public class CheckingAccount extends Account {
    public static final String accountType = "Checking Account";

    /**
     * Constructor is taking in the pin from the Account class
     *
     * @param pin the PIN number to the account
     */
    public CheckingAccount(String pin){
        super(pin);
    }
}
