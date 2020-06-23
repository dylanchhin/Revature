package bank.services;
import bank.dataaccess.AccountDataAccessObject;
import bank.dataaccess.PostGresConnectionUtil;
import bank.dataaccess.TransactionDAO;
import bank.dataaccess.UserNameBankAccountIDPairDAO;
import bank.model.BankAccount;
import bank.model.Transaction;
import bank.model.User;
import bank.model.UserNameBankAccountIDPair;

import java.sql.Timestamp;
import java.util.ArrayList;

public class AccountService {
    private AccountDataAccessObject accountDAO;

    private UserNameBankAccountIDPairDAO userNameAccountDao;

    private TransactionDAO transactionDAO;

    public AccountService(){
        this.accountDAO = new AccountDataAccessObject(new PostGresConnectionUtil());
        this.userNameAccountDao = new UserNameBankAccountIDPairDAO(new PostGresConnectionUtil());
        this.transactionDAO = new TransactionDAO(new PostGresConnectionUtil());
    };

    public AccountService(AccountDataAccessObject accountRepo, UserNameBankAccountIDPairDAO userNameAccountDao, TransactionDAO transactionDAO) {

        this.accountDAO = accountRepo;
        this.userNameAccountDao = userNameAccountDao;
        this.transactionDAO = transactionDAO;
    }

    /**
     * Handles deposit request to the account table
     * @param userName Current user username
     * @param accountID Current user account id
     * @param amount Amount wanting to deposit
     * @return whether the deposit worked or not
     */
    public boolean deposit(String userName, Integer accountID, double amount) {
        if(amount < 0)
        {
            throw new IllegalArgumentException("Invalid Amount");
        }
        //Check if username matches accountID
        UserNameBankAccountIDPair pair = new UserNameBankAccountIDPair(accountID, userName);
        if(!(userNameAccountDao.relationshipBetweenUserAndAccountExists(pair)))
        {
            throw new IllegalArgumentException("User did not have access or account did not exist");
        }
        BankAccount[] currentAccount = accountDAO.retrieveByID(accountID);
        double oldBalance = currentAccount[0].getCurrentBalance();
        currentAccount[0].setCurrentBalance(currentAccount[0].getCurrentBalance() + amount);
        boolean wasUpdated = accountDAO.update(currentAccount[0]);
        if(wasUpdated)
        {
            //Success
            Timestamp timeOfTransaction = new Timestamp(System.currentTimeMillis());
            Transaction transaction = new Transaction(0, accountID, oldBalance, amount, "deposit", timeOfTransaction );
            transactionDAO.save(transaction);
            return true;
        }
        else
        {
            //Fail
            return false;
        }
    }

    /**
     * Handles withdraw request from the user
     * @param userName Current user username
     * @param accountID Current user account id
     * @param amount Amount wanting to deposit
     * @return whether the withdraw worked or not
     */
    public boolean withdraw(String userName, Integer accountID, double amount) {
        if(amount < 0)
        {
            throw new IllegalArgumentException("Invalid Amount");
        }
        //Check if username matches accountID
        UserNameBankAccountIDPair pair = new UserNameBankAccountIDPair(accountID, userName);
        if(!(userNameAccountDao.relationshipBetweenUserAndAccountExists(pair)))
        {
            throw new IllegalArgumentException("User did not have access or account did not exist");
        }
        BankAccount[] currentAccount = accountDAO.retrieveByID(accountID);
        double oldBalance = currentAccount[0].getCurrentBalance();
        if(oldBalance - amount < 0)
        {
            throw new IllegalArgumentException("Too Low");
        }
        currentAccount[0].setCurrentBalance(currentAccount[0].getCurrentBalance() - amount);
        boolean wasUpdated = accountDAO.update(currentAccount[0]);
        if(wasUpdated)
        {
            //Success
            Timestamp timeOfTransaction = new Timestamp(System.currentTimeMillis());
            Transaction transaction = new Transaction(0, accountID, oldBalance, amount, "withdrawal", timeOfTransaction );
            transactionDAO.save(transaction);
            return true;
        }
        else
        {
            //Fail
            return false;
        }
    }

    /**
     * Handles transfer request between current user and another user
     * @param userName Current user username
     * @param userAccountID Current user account id
     * @param amount Amount wanting to deposit
     * @param transferredAccountID Account ID to transfer money to
     * @return if the transfer was successful
     */
    public boolean transfer(String userName, int userAccountID, double amount, int transferredAccountID) {
        //Check if username matches accountID
        UserNameBankAccountIDPair pair = new UserNameBankAccountIDPair(userAccountID, userName);
        if (!(userNameAccountDao.relationshipBetweenUserAndAccountExists(pair))) {
            throw new IllegalArgumentException("User did not have access or account did not exist");
        }
        BankAccount[] accounts = accountDAO.retrieveByID(userAccountID);
        if (accounts.length == 0) {
            throw new IllegalArgumentException("Account does not exist");
        }
        BankAccount currentAccount = accounts[0];
        if (currentAccount.getCurrentBalance() - amount < 0) {
            throw new IllegalArgumentException("Invalid Amount");
        }
        double oldBalance = accounts[0].getCurrentBalance();
        BankAccount[] otherAccount = accountDAO.retrieveByID(transferredAccountID);
        if (otherAccount.length == 0) {
            throw new IllegalArgumentException("Other Account does not exist");
        }
        BankAccount transferAccount = otherAccount[0];
        currentAccount.setCurrentBalance(currentAccount.getCurrentBalance() - amount);
        transferAccount.setCurrentBalance((transferAccount.getCurrentBalance() + amount));
        Boolean wasTransferred = accountDAO.transfer(currentAccount, transferAccount);
        if (wasTransferred) {
            Timestamp timeOfTransaction = new Timestamp(System.currentTimeMillis());
            Transaction transaction = new Transaction(0, userAccountID, oldBalance, amount, "transfer", timeOfTransaction );
            transactionDAO.save(transaction);
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * Handles getting transaction history for a user Customer Version
     * @param userName Current user username
     * @param userAccountID Current user account id
     * @return List of transactions for the account
     */
    public ArrayList<Transaction> getTransaction(String userName, int userAccountID) {
        //Check if username matches accountID
        UserNameBankAccountIDPair pair = new UserNameBankAccountIDPair(userAccountID, userName);
        if (!(userNameAccountDao.relationshipBetweenUserAndAccountExists(pair))) {
            throw new IllegalArgumentException("User did not have access or account did not exist");
        }
        ArrayList<Transaction> transactions = transactionDAO.retrieveByAccountID(userAccountID);
        return transactions;
    }

    /**
     * Handles getting the account balance
     * @param userName Current user username
     * @param userAccountID Current user account id
     * @return Balance of the account
     */
    public Double getBalance(String userName, int userAccountID)
    {
        UserNameBankAccountIDPair pair = new UserNameBankAccountIDPair(userAccountID, userName);
        if (!(userNameAccountDao.relationshipBetweenUserAndAccountExists(pair))) {
            throw new IllegalArgumentException("User did not have access or account did not exist");
        }
        BankAccount[] accounts = accountDAO.retrieveByID(userAccountID);
        BankAccount currentAccount = accounts[0];
        return currentAccount.getCurrentBalance();
    }

    /**
     * Create an account based on Current Account Information
     * @param account Passed account information
     * @return whether the account was created or not
     */
    public boolean createAccount(BankAccount account)
    {
        if(accountDAO.save(account) != -1)
        {
            return true;
        }
        return false;
    }

    /**
     * Finds the accounts a user owns
     * @param user Current User
     * @return Array of all accounts owned by the user
     */
    public UserNameBankAccountIDPair[] getAccounts(User user)
    {
        UserNameBankAccountIDPair[] pairs = userNameAccountDao.retrieveByID(user.getEmail());
        return pairs;
    }

    /**
     * Handles getting transaction history for a user
     * @param userAccountID Current user account id
     * @return List of transactions for the account
     */
    public ArrayList<Transaction> getTransaction(int userAccountID) {
        //Check if username matches accountID
        ArrayList<Transaction> transactions = transactionDAO.retrieveByAccountID(userAccountID);
        return transactions;
    }
}
