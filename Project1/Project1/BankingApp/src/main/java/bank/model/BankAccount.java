package bank.model;

public class BankAccount {
    private int accountID;
    private double currentBalance;
    public final static int DEFAULT_ID = 0;

    public BankAccount()
    {
        this.accountID = DEFAULT_ID;
        this.currentBalance = -1;
    }

    public BankAccount(int accountID, double currentBalance)
    {
        this.accountID = accountID;
        this.currentBalance = currentBalance;
    }

    public int getAccountID() {
        return accountID;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
}
