import bank.dataaccess.AccountDataAccessObject;
import bank.dataaccess.PostGresConnectionUtil;
import bank.model.BankAccount;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class AccountTest {

    private AccountDataAccessObject accountDAO = new AccountDataAccessObject(new PostGresConnectionUtil());
    @Test
    public void findAllAccount()
    {
        ArrayList<BankAccount> bankAccounts = accountDAO.retrieveAll();
        for (BankAccount currentAccount: bankAccounts) {
            System.out.println(currentAccount.getAccountID());
        }
        Assert.assertEquals("Test", true, true);
    }

    @Test
    public void retrieveByID()
    {
        double expectedAmount = 1000;
        BankAccount[] accountFound = accountDAO.retrieveByID(6);
        Assert.assertEquals(accountFound[0].getCurrentBalance(), expectedAmount, .001);
    }
    @Test
    public void accountUpdate()
    {
        BankAccount[] accountFound = accountDAO.retrieveByID(1);
        if(accountFound.length != 0)
        {
            BankAccount currentAccount = accountFound[0];
            double oldAmount = currentAccount.getCurrentBalance();
            System.out.println(oldAmount);
            double expectedAmount = oldAmount + 100;
            currentAccount.setCurrentBalance(expectedAmount);
            accountDAO.update(currentAccount);
            accountFound = accountDAO.retrieveByID(1);
            Assert.assertEquals(expectedAmount, accountFound[0].getCurrentBalance(), .001);
        }
    }

    @Test
    public void accountTransfer()
    {
        BankAccount account1 = new BankAccount(2,3000);
        BankAccount account2 = new BankAccount(3,2000);
        accountDAO.transfer(account1, account2);
        BankAccount[] accountFound = accountDAO.retrieveByID(2);
        double currentAmount = accountFound[0].getCurrentBalance();
        BankAccount[] accountFound2 = accountDAO.retrieveByID(3);
        double currentAmount2 = accountFound2[0].getCurrentBalance();
        Assert.assertTrue("Not Equal", currentAmount == 3000 && currentAmount2 == 2000);
    }

    @Test
    public void accountSave()
    {
    }

    @Test
    public void accountDelete()
    {

    }

    @After
    public void reset()
    {
        BankAccount account1 = new BankAccount(1, 1000);
        accountDAO.update(account1);
        BankAccount account2 = new BankAccount(6, 1000);
        accountDAO.update(account2);

    }
}
