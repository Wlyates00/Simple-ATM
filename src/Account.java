/**
 * An abstract class that SavingsAccount and CheckingAccount extend from to be interchangable
 */

import java.text.DecimalFormat;

public abstract class Account {
    private String accountNumber;
    private String pinNumber;
    // Starting each account with 100.11 dollars
    private Double balance = 100.00;

    /**
     * A constructor for adding a PIN number to each account when initalized
     *
     * @param pin the PIN for the account
     */
    public Account(String pin){
        pinNumber = pin;
    }

    /**
     * Checking if an inputed string is equal to the user PIN number
     *
     * @param pin the inputted pin but the user
     * @return return true if it is correct, else it is invalid
     */

    public boolean checkPIN(String pin){
        // Return true if the pin is correct
        return pinNumber.equals(pin);
    }

    /**
     * Formatting the balance of the account to 2 decimal places and returning it
     *
     * @return the formatted balance as a double
     */
    public String getBalance(){
        // Formatting balance to 2 decimals
        DecimalFormat df = new DecimalFormat("#.##");

        // Returning formatted balance
        return (df.format(balance));
    }

    /**
     * Formatting the balance of the account neatly and returning it
     *
     * @return the formatted balance as a string
     */
    public String getFormattedBalance(){
        // Returning A worded balance with formatting
        return ("CURRENT BALANCE: $" + getBalance());
    }

    /**
     *
     *
     * @param amount the total amount to remove from the account
     * @return true if the transaction was successful, else the user does not have the funds
     */
    public boolean withdraw(double amount){
        // If the user has enough money withdraw and return true
        if (balance >= amount) {
            balance = balance - amount;
            return true;
        }
        // If user does not have enough money return true
        return false;
    }

    /**
     * Increases the balance of the account
     *
     * @param amount the total amount of money to add to the balance
     */
    public void deposit(double amount){
        // Increase the balance by an inputted amount
        balance = balance + amount;
    }
}
