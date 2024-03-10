/**
 * Extention of the Accounts class that allows for different characteristics from
 * the Checking Account class
 */
public class SavingsAccount extends Account {
    public static final String accountType = "Savings Account";

    /**
     * Constructor is taking in the pin from the Account class
     *
     * @param pin the PIN number to the account
     */
    public SavingsAccount(String pin){
        super(pin);
    }
}
