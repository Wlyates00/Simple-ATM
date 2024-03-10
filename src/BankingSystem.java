import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Banking System is in charge of creating and finding accounts
 */
public class BankingSystem {

    // Array of all created accounts
    private ArrayList<Account> accounts;

    // Assigning account numbers for all accounts with hashmap
    private HashMap<String, Integer> accountNumberHashmap =
            new HashMap<String, Integer>();

    /**
     * Constructor for the BankingSystem obj.
     *
     * Initializing the user accounts here
     */
    public BankingSystem(){
        accounts = new ArrayList<Account>();
        accountNumberHashmap = new HashMap<String, Integer>();

        for (int i = 0; i < 10; i++) {
            // Creating checking accounts here
            String pin = "1234";
            accounts.add(new CheckingAccount(pin));
        }
        for (int i = 0; i < 1; i++) {
            // Creating savings accounts here
            String pin = "4321";
            accounts.add(new SavingsAccount(pin));
        }

        accountNumberHashmap.put("00000000", 0);
        accountNumberHashmap.put("11111111", 1);
        accountNumberHashmap.put("22222222", 2);
        accountNumberHashmap.put("33333333", 3);
        accountNumberHashmap.put("44444444", 4);
        accountNumberHashmap.put("55555555", 5);
        accountNumberHashmap.put("66666666", 6);
        accountNumberHashmap.put("77777777", 7);
        accountNumberHashmap.put("88888888", 8);
        accountNumberHashmap.put("99999999", 9);

        accountNumberHashmap.put("10101010", 10);


    }

    /**
     * Locate an appropriate account
     *
     * @param key the account number
     * @return an account or null if the account number was invalid
     */
    public Account findAccount(String key){
        if (accountNumberHashmap.get(key) != null){
            return accounts.get(accountNumberHashmap.get(key));
        }
        else return null;
    }
}
