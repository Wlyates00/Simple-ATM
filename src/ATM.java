/**
 * The ATM holds all the ATM's prompts and methods to keep the vault balance in sync
 */
public class ATM {
    private Vault vault;
    private String password = "SecureATM";

    // vv------------- CUSTOMER PROMPTS & ERRORS -----------------vv
    public static final String DEPOSIT_100_PROMPT =
            "How many $100 bills do you want to deposit?";
    public static final String DEPOSIT_50_PROMPT =
            "How many $50 bills do you want to deposit?";
    public static final String DEPOSIT_20_PROMPT =
            "How many $20 bills do you want to deposit?";
    public static final String DEPOSIT_5_PROMPT =
            "How many $5 bills do you want to deposit?";
    public static final String WITHDRAW_100_PROMPT =
            "How many $100 bills do you want to withdraw?";
    public static final String WITHDRAW_50_PROMPT =
            "How many $50 bills do you want to withdraw?";
    public static final String WITHDRAW_20_PROMPT =
            "How many $20 bills do you want to withdraw?";
    public static final String WITHDRAW_5_PROMPT =
            "How many $5 bills do you want to withdraw?";
    public static final String WITHDRAW_ERROR =
            "You do not have the funds to transfer that amount";
    public static final String BILL_ERROR =
            "This atm does not have the bills for that transaction at this time";
    public static final String TRANSFER_ACCOUNT_PROMPT =
            "Enter any valid bank account number you would like to transfer to";
    public static final String TRANSFER_PROMPT =
            "Enter the amount you want to transfer to the dollar amount (e.g. 12.62 or 12)";
    public static final String TRANSFER_ERROR =
            "Your account does not have enough funds to make that transaction at this time";

    // ^^------------- CUSTOMER PROMPTS & ERRORS -----------------^^

    // vv------------- OPERATOR PROMPTS & ERRORS -----------------vv
    public static final String ADD_100_PROMPT =
            "How many $100 bills do you want to add to the ATM?";
    public static final String ADD_50_PROMPT =
            "How many $50 bills do you want to add to the ATM?";
    public static final String ADD_20_PROMPT =
            "How many $20 bills do you want to add to the ATM?";
    public static final String ADD_5_PROMPT =
            "How many $5 bills do you want to add to the ATM?";
    public static final String TAKEOUT_100_PROMPT =
            "How many $100 bills do you want to take out of the ATM?";
    public static final String TAKEOUT_50_PROMPT =
            "How many $100 bills do you want to take out of the ATM?";
    public static final String TAKEOUT_20_PROMPT =
            "How many $100 bills do you want to take out of the ATM?";
    public static final String TAKEOUT_5_PROMPT =
            "How many $100 bills do you want to take out of the ATM?";

    // ^^------------- OPERATOR PROMPTS & ERRORS -----------------^^


    /**
     * This constructor is in charge of initializing the vault
     * when the ATM itself is initialized.
     */
    public ATM(){
        vault = new Vault();
    }

    /**
     * Responsible for taking in a string and return true if the string is a correct password and false if not.
     * @param key the user inputted password
     * @return true or false, depending on if the entered password is correct
     */
    public boolean validateOperatorPassword(String key){
        return key.equals(password);
    }

    /**
     * Responsible for ejecting the bills out of the machine while managing the vault values
     *
     * @param amt the amount of bills wanted
     * @param hasMoney if the user has enough money for said amount of bills
     * @return returning true if the ejection was successful on the ATM end of things
     */
    public boolean eject100Bills(int amt, boolean hasMoney){
        // If user has money for it they can withdraw
        if (hasMoney) return vault.remove100Bills(amt);
        else return vault.remove100Bills(0);
    }

    /**
     * Responsible for ejecting the bills out of the machine while managing the vault values
     *
     * @param amt the amount of bills wanted
     * @param hasMoney if the user has enough money for said amount of bills
     * @return returning true if the ejection was successful on the ATM end of things
     */
    public boolean eject50Bills(int amt, boolean hasMoney){
        if (hasMoney) return vault.remove50Bills(amt);
        else return vault.remove50Bills(0);
    }

    /**
     * Responsible for ejecting the bills out of the machine while managing the vault values
     *
     * @param amt the amount of bills wanted
     * @param hasMoney if the user has enough money for said amount of bills
     * @return returning true if the ejection was successful on the ATM end of things
     */
    public boolean eject20Bills(int amt, boolean hasMoney){
        if (hasMoney) return vault.remove20Bills(amt);
        else return vault.remove20Bills(0);
    }

    /**
     * Responsible for ejecting the bills out of the machine while managing the vault values
     *
     * @param amt the amount of bills wanted
     * @param hasMoney if the user has enough money for said amount of bills
     * @return returning true if the ejection was successful on the ATM end of things
     */
    public boolean eject5Bills(int amt, boolean hasMoney){
        if (hasMoney) return vault.remove5Bills(amt);
        else return vault.remove5Bills(0);
    }

    /**
     * Responsible for accepting the bills and sending to the vault.
     *
     * @param amt the amount of bills is put into the vault
     */
    public void validate100Bills(int amt){
        vault.add100Bills(amt);
    }

    /**
     * Responsible for accepting the bills and sending to the vault.
     *
     * @param amt the amount of bills is put into the vault
     */
    public void validate50Bills(int amt){
        vault.add50Bills(amt);
    }

    /**
     * Responsible for accepting the bills and sending to the vault.
     *
     * @param amt the amount of bills is put into the vault
     */
    public void validate20Bills(int amt){
        vault.add20Bills(amt);
    }

    /**
     * Responsible for accepting the bills and sending to the vault.
     *
     * @param amt the amount of bills is put into the vault
     */
    public void validate5Bills(int amt){
        vault.add5Bills(amt);
    }

    /**
     * Gets the amount of each bill type
     *
     * @return a string holding the values of the vault bill quantities
     */
    public String checkVaultStatus(){
        return vault.getBillAmounts();
    }

    /**
     * Gets the amount of money in total within the ATM
     *
     * @return string holding the total value of the vault
     */
    public int checkVaultBalance(){
        return vault.getBalance();
    }
}
