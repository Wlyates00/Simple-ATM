/**
 * Connects a touchscreen to an ATM and Banking System. The main purpose of this
 * class is state management: to keep track of the connection state and menu state.
 */
public class Connection {
    private Touchscreen touchscreen;
    private BankingSystem bankingSystem;
    private ATM atm;

    private Account currentAccount;
    private Account receivingAccount;

    private int state;

    private static final int DICONNECTED = 0;

    private static final int CONNECTED = 1;
    private static final int ACC_CHOICE = 2;
    private static final int ENTER_PIN = 3;

    private static final int CUSTOMER_MENU = 4;
    private static final int WITHDRAW_MONEY = 5;
    private static final int DEPOSIT_MONEY = 6;
    private static final int TRANSFER_MONEY = 7;
    private static final int VAULT_STATUS = 8;
    private static final int OPERATOR_MENU = 9;
    private static final int ADD_BILLS = 10;
    private static final int REMOVE_BILLS = 11;



    // This is a reusable "sub" state for the customer and operator menus
    private int substate;

    private static final String INITIAL_PROMPT =
            "Complete one of the following to access this ATM \n"
                   + "Enter 1 to enter an account number\n"
                   + "Enter 2 to enter an ATM operator password\n";

    private static final String CUSTOMER_MENU_TEXT =
            "Enter 1 to withdraw money from your account\n"
            + "Enter 2 to deposit money into your account\n"
            + "Enter 3 to transfer money into an account\n"
            + "Enter 4 to check your current balance\n"
            + "Enter 5 to check the ATM vault balance (**REQUIRES OPERATOR PASSWORD**)\n";

    private static final String OPERATOR_MENU_TEXT =
            "Enter 1 to display the number of each bill\n"
                    + "Enter 2 to put a given number of bills into the ATM\n"
                    + "Enter 3 to remove a given number of bills from the ATM\n";



    /**
     * Constructor for the Connection obj.
     *
     * @param a an ATM object
     * @param ts a Touchscreen object
     * @param b a BankingSystem object
     */
    public Connection(ATM a, Touchscreen ts, BankingSystem b){
        atm = a;
        bankingSystem = b;
        touchscreen = ts;
        startConnection();
    }

    /**
     * Initialization of a new state of connection and display the initial prompt.
     * This method is also used to restart state as well.
     *
     */
    public void startConnection(){
        // The connection is in the connected state
        state = CONNECTED;
        touchscreen.display(INITIAL_PROMPT);

        // Restarting the reusable substate
        substate = 0;
    }

    /**
     * Responds to correct inputs from the user from the touchscreen
     *
     * @param key is the inputs that were inputted from the user
     */
    public void atmState(String key){
        if (state == CONNECTED){
            accountType(key);
        }
        else if (state == ACC_CHOICE) {
            connectAccount(key);
        }
        else if (state == ENTER_PIN) {
            login(key);
        }
        else if (state == CUSTOMER_MENU) {
            customerMenu(key);
        }
        else if (state == WITHDRAW_MONEY) {
            withdrawMenu(key);
        }
        else if (state == DEPOSIT_MONEY) {
            depositMenu(key);
        }
        else if (state == TRANSFER_MONEY){
            transferMenu(key);
        }
        else if (state == VAULT_STATUS) {
            checkVaultMenu(key);
        }
        else if (state == OPERATOR_MENU) {
            operatorMenu(key);
        }
        else if (state == ADD_BILLS) {
            addBillsMenu(key);
        }
        else if (state == REMOVE_BILLS) {
            removeBillsMenu(key);
        }
    }


    /**
     *  Runs when the input is 12 char long
     *
     * @param key the inputted account number
     */
    private void connectAccount(String key){
        // Logging into bank account
        if(key.length() == 8){
            currentAccount = bankingSystem.findAccount(key);
            if (currentAccount != null){
                state = ENTER_PIN;
                touchscreen.display("Enter your PIN: ");
            }
            else{
                touchscreen.display("Invalid account number");
            }
        }

        // Logging into Operator Mode
        if(key.length() == 9){
            if (atm.validateOperatorPassword(key)){
                state = OPERATOR_MENU;
                touchscreen.display(OPERATOR_MENU_TEXT);
            }
            else{
                touchscreen.display("Invalid operator password");

            }
        }

        // Telling the user to try again
        else if (key.length() != 9 && key.length() != 8) touchscreen.display("Try again!");
    }

    /**
     * Validation of the PIN associated to a specified account earlier in the programs life
     *
     * @param key is the PIN entered by the user
     */
    private void login(String key){
        if (currentAccount.checkPIN(key)){
            state = CUSTOMER_MENU;
            touchscreen.display(CUSTOMER_MENU_TEXT);
        }
        else touchscreen.display("Invalid PIN! Try again.");
    }

    /**
     * The beginning choice to choose either operator account or bank account
     *
     * @param option is either 1 or 2 from user input otherwise the output is "Try again!"
     */
    private void accountType(String option){
        // Use touchscreen to show either operator message base on input or ask for bank account number
        if (option.equals("1")){
            state = ACC_CHOICE;
            touchscreen.display("Enter your bank account number: ");
        }
        else if (option.equals("2")){
            state = ACC_CHOICE;
            touchscreen.display("Enter your ATM operator password: ");
        }
        else{
            touchscreen.display(INITIAL_PROMPT);
        }
    }

    /**
     * Responds to the users input to change the programs state.
     *
     * @param option the users option they have chosen
     */
    private void customerMenu(String option){
        //withdraw
        if (option.equals("1")){
            state = WITHDRAW_MONEY;
            touchscreen.display(currentAccount.getFormattedBalance() +
                    "\n" + atm.WITHDRAW_100_PROMPT);
        }
        //deposit
        if (option.equals("2")){
            state = DEPOSIT_MONEY;
            touchscreen.display(currentAccount.getFormattedBalance() +
                    "\n" + atm.DEPOSIT_100_PROMPT);
        }
        //transfer
        if (option.equals("3")){
            state = TRANSFER_MONEY;
            touchscreen.display(currentAccount.getFormattedBalance() + "\n" + atm.TRANSFER_ACCOUNT_PROMPT);
        }

        //check current balance
        if (option.equals("4")){
            touchscreen.display(currentAccount.getFormattedBalance());
            touchscreen.display(CUSTOMER_MENU_TEXT);
        }

        // check atm vault balance
        if (option.equals("5")){
            state = VAULT_STATUS;
            touchscreen.display("Enter the ATM operator password");
        }
    }

    /**
     * A submenu of the CUSTOMER menu that responds to the users input to withdraw specific bills by the desired amounts
     *
     * @param amount the amount of bills wanted
     */
    private void withdrawMenu(String amount){
        // --------- 100 BILL SECTION ---------
        if (substate == 0 && Integer.parseInt(amount) >= 0) {

            // This is the total amount with this bill
            // (Used for adjusting total account balance)
            int currentBillAmount = 100 * Integer.parseInt(amount);

            // Bools for checking if user and ATM has enough money
            boolean userHasMoney = currentAccount.withdraw(currentBillAmount);
            boolean atmHasMoney = atm.eject100Bills(Integer.parseInt(amount), userHasMoney);


            // IF ATM has enough money it goes through, else it tells you not enough bills
            if (!atmHasMoney) touchscreen.display(atm.BILL_ERROR);

            // Check if user can remove each bill as they go if not prompt the user with that info
            if (!userHasMoney) touchscreen.display(atm.WITHDRAW_ERROR);

            substate++;

            // Next substate prompt
            touchscreen.display(atm.WITHDRAW_50_PROMPT);
        }

        // --------- 50 BILL SECTION ---------
        else if (substate == 1 && Integer.parseInt(amount) >= 0){

            // This is the total amount with this bill
            // (Used for adjusting total account balance)
            int currentBillAmount = 50 * Integer.parseInt(amount);

            // Bools for checking if user and ATM has enough money
            boolean userHasMoney = currentAccount.withdraw(currentBillAmount);
            boolean atmHasMoney = atm.eject50Bills(Integer.parseInt(amount), userHasMoney);


            // ATM has enough money it goes through, else it tells you not enough bills
            if (!atmHasMoney) touchscreen.display(atm.BILL_ERROR);

            // Remove each bill as we go
            if (!userHasMoney) touchscreen.display(atm.WITHDRAW_ERROR);

            substate++;

            // Next substate prompt
            touchscreen.display(atm.WITHDRAW_20_PROMPT);
        }

        // --------- 20 BILL SECTION ---------
        else if (substate == 2 && Integer.parseInt(amount) >= 0){

            // This is the total amount with this bill
            // (Used for adjusting total account balance)
            int currentBillAmount = 20 * Integer.parseInt(amount);

            // Bools for checking if user and ATM has enough money
            boolean userHasMoney = currentAccount.withdraw(currentBillAmount);
            boolean atmHasMoney = atm.eject20Bills(Integer.parseInt(amount), userHasMoney);

            // ATM has enough money it goes through, else it tells you not enough bills
            if (!atmHasMoney) touchscreen.display(atm.BILL_ERROR);

            // Remove each bill as we go
            if (!userHasMoney) touchscreen.display(atm.WITHDRAW_ERROR);

            substate++;

            // Next substate prompt
            touchscreen.display(atm.WITHDRAW_5_PROMPT);
        }

        // --------- 5 BILL SECTION ---------
        else if (substate == 3){

            // This is the total amount with this bill
            // (Used for adjusting total account balance)
            int currentBillAmount = 5 * Integer.parseInt(amount);

            // Booleans for checking if user and ATM has enough money
            boolean userHasMoney = currentAccount.withdraw(currentBillAmount);
            boolean atmHasMoney = atm.eject5Bills(Integer.parseInt(amount), userHasMoney);


            // ATM has enough money it goes through, else it tells you not enough bills
            if (!atmHasMoney) touchscreen.display(atm.BILL_ERROR);

            // Remove each bill as we go
            if (!userHasMoney) touchscreen.display(atm.WITHDRAW_ERROR);


            touchscreen.display("NEW BALANCE: $" + currentAccount.getBalance());

            startConnection();
        }
    }

    /**
     * A submenu of the CUSTOMER menu that responds to the users input to deposit specific bills by the desired amounts
     *
     * @param amount the amount of bills the user wants to input
     */
    private void depositMenu(String amount){
        // -------- 100 Bill Section --------
        if (substate == 0 && Integer.parseInt(amount) >= 0) {

            // This is the total amount with this bill
            // (Used for adjusting total account balance)
            int currentBillAmount = 100 * Integer.parseInt(amount);

            // Increasing account balance
            currentAccount.deposit(currentBillAmount);

            // Checking and Adding cash to ATM
            atm.validate100Bills(Integer.parseInt(amount));

            // Increasing substate to go to the next bill
            substate++;

            // Next substate prompt
            touchscreen.display(atm.DEPOSIT_50_PROMPT);
        }

        // -------- 50 Bill Section -------
        else if (substate == 1 && Integer.parseInt(amount) >= 0){

            // This is the total amount with this bill
            // (Used for adjusting total account balance)
            int currentBillAmount = 50 * Integer.parseInt(amount);

            // Increasing account balance
            currentAccount.deposit(currentBillAmount);

            // Checking and Adding cash to ATM
            atm.validate50Bills(Integer.parseInt(amount));

            // Increasing substate to go to the next bill
            substate++;

            // Next substate prompt
            touchscreen.display(atm.DEPOSIT_20_PROMPT);
        }

        // ------- 20 Bill Section --------
        else if (substate == 2 && Integer.parseInt(amount) >= 0){

            // This is the total amount with this bill. For example: (billAmount * desiredAmountOfBills)
            // Used for adjusting total account balance
            int currentBillAmount = 20 * Integer.parseInt(amount);

            // Increasing account balance
            currentAccount.deposit(currentBillAmount);

            // Checking and Adding cash to ATM
            atm.validate20Bills(Integer.parseInt(amount));

            // Increasing substate to go to the next bill
            substate++;

            // Next substate prompt
            touchscreen.display(atm.DEPOSIT_5_PROMPT);
        }

        // -------- 5 Bill Section --------
        else if (substate == 3){

            // This is the total amount with this bill
            // (Used for adjusting total account balance)
            int currentBillAmount = 5 * Integer.parseInt(amount);

            // Increasing account balance
            currentAccount.deposit(currentBillAmount);

            // Checking and Adding cash to ATM
            atm.validate5Bills(Integer.parseInt(amount));


            touchscreen.display("NEW BALANCE: $" + currentAccount.getBalance());

            startConnection();
        }
    }

    /**
     * A submenu of the CUSTOMER menu that connects to a different account in order transfer money
     *
     * @param input the account number or amount to transfer from the user
     */
    private void transferMenu(String input){
        // Showing if the length of input is wrong off the start
        if (substate == 0 && input.length() != 8) touchscreen.display("Invalid Length!");

        // First state is to connect account
        if (substate == 0 && input.length() == 8) {
            // Connecting to account
            receivingAccount = bankingSystem.findAccount(input);

            if (receivingAccount != null){
                // If account is found the program proceeds
                substate++;

                // Asking user to input desired amount of money
                touchscreen.display(atm.TRANSFER_PROMPT);
            }

            // If Account number is wrong this is displayed
            else touchscreen.display("Try again!");
        }

        // Second state is transaction state
        else if (substate == 1 && Double.parseDouble(input) >= 0) {
            // Amount to transfer
            double amt = Double.parseDouble(input);

            // If sender has money go through on both ends
            if (currentAccount.withdraw(amt)){
                receivingAccount.deposit(amt);
            }
            // Display error if sender has to few funds
            else touchscreen.display(atm.TRANSFER_ERROR);

            // Showing end balance
            touchscreen.display("NEW BALANCE: " + currentAccount.getBalance());

            // Restart connection
            startConnection();
        }
    }

    /**
     * The only operation in the CUSTOMER menu that requires an operator password. It displays
     * current vault balance only after typing the correct password.
     *
     * @param input the operator password that is entered
     */
    private void checkVaultMenu(String input){
        if (atm.validateOperatorPassword(input)){
            // Out putting the balance neatly
            touchscreen.display("The vault currently has: $" + atm.checkVaultBalance());

            // Restarting from the customer menu instead of dropping connection
            touchscreen.display(CUSTOMER_MENU_TEXT);
            state = CUSTOMER_MENU;
        }
        else touchscreen.display("Invalid Password. Try again!");
    }

    /**
     * Responds to the users input to change the programs state.
     *
     * @param option the chosen option inputted from the user
     */
    private void operatorMenu(String option){
        if (option.equals("1")){
            // Display the number of each bill
            touchscreen.display(atm.checkVaultStatus());

            // Pausing the program briefly to give a chance for the
            // user to read it.
            pauseProgramBriefly();

            // Re-displaying Operator Menu
            touchscreen.display(OPERATOR_MENU_TEXT);
        }
        else if (option.equals("2")){
            // Add bills Menu
            touchscreen.display("CURRENT VAULT BALANCE: $" + atm.checkVaultBalance() + "\n" + atm.checkVaultStatus());
            touchscreen.display(atm.ADD_100_PROMPT);
            state = ADD_BILLS;
        }
        else if (option.equals("3")) {
            // Remove bills menu
            touchscreen.display("CURRENT VAULT BALANCE: $" + atm.checkVaultBalance() + "\n" + atm.checkVaultStatus());
            touchscreen.display(atm.TAKEOUT_100_PROMPT);
            state = REMOVE_BILLS;
        }
    }

    /**
     * A submenu of the OPERATOR menu that responds to the operator input to add specific bills by
     * the desired amount
     *
     * @param amount the amount of bills the user wants to input
     */
    private void addBillsMenu(String amount){
        // -------- 100 Bill Section --------
        if (substate == 0 && Integer.parseInt(amount) >= 0) {
            // Adding bills to ATM
            atm.validate100Bills(Integer.parseInt(amount));

            // Increasing substate to go to the next bill
            substate++;

            // Next substate prompt
            touchscreen.display(atm.ADD_50_PROMPT);
        }

        // -------- 50 Bill Section -------
        else if (substate == 1 && Integer.parseInt(amount) >= 0){

            // Adding bills to ATM
            atm.validate50Bills(Integer.parseInt(amount));

            // Increasing substate to go to the next bill
            substate++;

            // Next substate prompt
            touchscreen.display(atm.ADD_20_PROMPT);
        }

        // ------- 20 Bill Section --------
        else if (substate == 2 && Integer.parseInt(amount) >= 0){

            // Adding bills to ATM
            atm.validate20Bills(Integer.parseInt(amount));

            // Increasing substate to go to the next bill
            substate++;

            // Next substate prompt
            touchscreen.display(atm.ADD_5_PROMPT);
        }

        // -------- 5 Bill Section --------
        else if (substate == 3){

            // Adding bills to ATM
            atm.validate5Bills(Integer.parseInt(amount));

            // Displaying the new vault balance
            touchscreen.display("NEW BALANCE: $" + atm.checkVaultBalance() + "\n" + atm.checkVaultStatus());

            startConnection();
        }
    }

    /**
     * A submenu of the OPERATOR menu that responds to the operator input to remove specific bills by
     * the desired amount
     *
     * @param amount the amount of bills the user wants to remove
     */
    private void removeBillsMenu(String amount){
        // -------- 100 Bill Section --------
        if (substate == 0 && Integer.parseInt(amount) >= 0) {
            // Ejecting the bills at no cost to the operator
            if (!atm.eject100Bills(Integer.parseInt(amount), true))
                touchscreen.display(atm.BILL_ERROR);

            // Increasing substate to go to the next bill
            substate++;

            // Next substate prompt
            touchscreen.display(atm.TAKEOUT_50_PROMPT);
        }

        // -------- 50 Bill Section -------
        else if (substate == 1 && Integer.parseInt(amount) >= 0){

            // Ejecting the bills at no cost to the operator
            if (!atm.eject50Bills(Integer.parseInt(amount), true))
                touchscreen.display(atm.BILL_ERROR);

            // Increasing substate to go to the next bill
            substate++;

            // Next substate prompt
            touchscreen.display(atm.TAKEOUT_20_PROMPT);
        }

        // ------- 20 Bill Section --------
        else if (substate == 2 && Integer.parseInt(amount) >= 0){

            // Ejecting the bills at no cost to the operator
            if (!atm.eject20Bills(Integer.parseInt(amount), true))
                touchscreen.display(atm.BILL_ERROR);

            // Increasing substate to go to the next bill
            substate++;

            // Next substate prompt
            touchscreen.display(atm.TAKEOUT_5_PROMPT);
        }

        // -------- 5 Bill Section --------
        else if (substate == 3){

            // Ejecting the bills at no cost to the operator
            if (!atm.eject5Bills(Integer.parseInt(amount), true))
                touchscreen.display(atm.BILL_ERROR);

            // Displaying the new vault balance
            touchscreen.display("NEW BALANCE: $" + atm.checkVaultBalance() + "\n" + atm.checkVaultStatus());

            startConnection();
        }
    }

    /**
     * A function to pause the program briefly to give the user the time to
     * read text on the touch screen.
     */
    private void pauseProgramBriefly(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
