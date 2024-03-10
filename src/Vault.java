/**
 *  The vault keeps all the money data from the ATM store away privately
 */
public class Vault {
    private int balance = 17500;

    private int bill_100_amount = 100;
    private int bill_50_amount = 100;
    private int bill_20_amount = 100;
    private int bill_5_amount = 100;

    /**
     * Responsible for removing a specified amount of bills if the atm has enough money
     *
     * @param amount the amount of bills that should be removed
     * @return a true or false value showing if the ATM has enough funds
     */
    public boolean remove100Bills(int amount){
        if (bill_100_amount >= amount){
            balance -= 100 * amount;
            bill_100_amount = bill_100_amount - amount;
            return true;
        }
        else return false;
    }

    /**
     * Responsible for removing a specified amount of bills if the atm has enough money
     *
     * @param amount the amount of bills that should be removed
     * @return a true or false value showing if the ATM has enough funds
     */
    public boolean remove50Bills(int amount){
        if (bill_50_amount >= amount){
            balance -= 50 * amount;
            bill_50_amount = bill_50_amount - amount;
            return true;
        }
        else return false;
    }

    /**
     * Responsible for removing a specified amount of bills if the atm has enough money
     *
     * @param amount the amount of bills that should be removed
     * @return a true or false value showing if the ATM has enough funds
     */
    public boolean remove20Bills(int amount){
        if (bill_20_amount >= amount){
            balance -= 20 * amount;
            bill_20_amount = bill_20_amount - amount;
            return true;
        }
        else return false;
    }

    /**
     * Responsible for removing a specified amount of bills if the atm has enough money
     *
     * @param amount the amount of bills that should be removed
     * @return a true or false value showing if the ATM has enough funds
     */
    public boolean remove5Bills(int amount){
        if (bill_5_amount >= amount){
            balance -= 5 * amount;
            bill_5_amount = bill_5_amount - amount;
            return true;
        }
        else return false;
    }

    /**
     * Responsible for adding a specified amount of bills to the ATM
     *
     * @param amount the amount of bills that should be added
     */
    public void add100Bills(int amount){
        balance += 100 * amount;
        bill_100_amount = bill_100_amount + amount;
    }

    /**
     * Responsible for adding a specified amount of bills to the ATM
     *
     * @param amount the amount of bills that should be added
     */
    public void add50Bills(int amount){
        balance += 50 * amount;
        bill_50_amount = bill_50_amount + amount;

    }

    /**
     * Responsible for adding a specified amount of bills to the ATM
     *
     * @param amount the amount of bills that should be added
     */
    public void add20Bills(int amount){
        balance += 20 * amount;
        bill_20_amount = bill_20_amount + amount;

    }

    /**
     * Responsible for adding a specified amount of bills to the ATM
     *
     * @param amount the amount of bills that should be added
     */
    public void add5Bills(int amount){
        balance += 5 * amount;
        bill_5_amount = bill_5_amount + amount;

    }

    /**
     * Responsible for returning the balance as an int
     *
     * @return an integer showing the total balance of the vault
     */
    public int getBalance(){
        return balance;
    }

    /**
     * Responsible for returning a string showing the amount of all bills
     *
     * @return an integer showing the total amount of each bill
     */
    public String getBillAmounts(){
        return "$100......."+bill_100_amount+"\n"
                +"$50......."+bill_50_amount+"\n"
                + "$20......."+bill_20_amount+"\n"
                + "$5......."+bill_5_amount+"\n";
    }
}
