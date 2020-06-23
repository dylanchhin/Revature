import bank.dataaccess.*;
import bank.model.BankAccount;
import bank.model.Transaction;
import bank.model.UserNameBankAccountIDPair;
import bank.services.AccountService;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;

public class AccountServiceTest {

    private AccountService accountService;
    private String userName;
    private int accountID;
    private double amount;
    private BankAccount account;
    private UserNameBankAccountIDPair pair;
    private BankAccount secondAccount;
    BankAccount[] accounts;
    @Mock
    private AccountDataAccessObject bankAccountDAO = null;
    @Mock
    private UserNameBankAccountIDPairDAO userPairDAO = null;
    @Mock
    private TransactionDAO transactionDAO = null;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        accountService = new AccountService(bankAccountDAO, userPairDAO, transactionDAO);
        userName = "jack@gmail.com";
        accountID = 6;
        amount = 1100;
        account = new BankAccount(accountID, amount);
        secondAccount = new BankAccount();
        accounts = new BankAccount[]{account};
        pair = new UserNameBankAccountIDPair(accountID, userName);

    }
    @Test
    public void accountDepositSuccess()
    {
        Mockito.when(userPairDAO.relationshipBetweenUserAndAccountExists(any(UserNameBankAccountIDPair.class))).thenReturn(true);
        Mockito.when(bankAccountDAO.retrieveByID(accountID)).thenReturn(accounts);
        Mockito.when(bankAccountDAO.update(account)).thenReturn(true);
        Assert.assertEquals("Not Passed", true, accountService.deposit(userName, accountID, amount));
    }


    @Test
    public void accountDepositFailEmailAccountNotMatch()
    {
        boolean passed = false;
        try
        {
            Mockito.when(userPairDAO.relationshipBetweenUserAndAccountExists(any(UserNameBankAccountIDPair.class))).thenThrow(IllegalArgumentException.class);
            accountService.deposit(userName, accountID, 1000);
            passed = true;
        }
        catch(IllegalArgumentException e)
        {
        }
        Assert.assertFalse("Passed", passed);
    }

    @Test
    public void accountFailedDeposit()
    {
        Mockito.when(userPairDAO.relationshipBetweenUserAndAccountExists(any(UserNameBankAccountIDPair.class))).thenReturn(true);
        Mockito.when(bankAccountDAO.retrieveByID(accountID)).thenReturn(accounts);
        Mockito.when(bankAccountDAO.update(account)).thenReturn(false);
        Assert.assertEquals("Passed", false, accountService.deposit(userName, accountID, amount));
    }

    @Test
    public void accountWithdrawPass()
    {
        Mockito.when(userPairDAO.relationshipBetweenUserAndAccountExists(any(UserNameBankAccountIDPair.class))).thenReturn(true);
        Mockito.when(bankAccountDAO.retrieveByID(accountID)).thenReturn(accounts);
        Mockito.when(bankAccountDAO.update(account)).thenReturn(true);
        Assert.assertEquals("Not Passed", true, accountService.withdraw(userName, accountID, amount));
    }

    @Test
    public void accountWithdrawInsufficientFunds()
    {
        boolean passed = true;
        try
        {
            accountService.withdraw(userName, accountID, 200000);
        }catch(IllegalArgumentException e)
        {
            passed = false;
        }
        Assert.assertFalse(passed);
    }

    @Test
    public void accountWithdrawAccountNotMatch()
    {
        boolean passed = true;
        try
        {
            Mockito.when(userPairDAO.relationshipBetweenUserAndAccountExists(any(UserNameBankAccountIDPair.class))).thenThrow(IllegalArgumentException.class);
            accountService.withdraw(userName, accountID, amount);
        }catch(IllegalArgumentException e)
        {
            passed = false;
        }
        Assert.assertFalse(passed);
    }

    @Test
    public void accountWithdrawFailedWithdraw()
    {
        Mockito.when(userPairDAO.relationshipBetweenUserAndAccountExists(any(UserNameBankAccountIDPair.class))).thenReturn(true);
        Mockito.when(bankAccountDAO.retrieveByID(accountID)).thenReturn(accounts);
        Mockito.when(bankAccountDAO.update(account)).thenReturn(false);
        Assert.assertEquals("Passed", false, accountService.withdraw(userName, accountID, amount));
    }

    @Test
    public void accountTransferSuccess()
    {
        Mockito.when(userPairDAO.relationshipBetweenUserAndAccountExists(any(UserNameBankAccountIDPair.class))).thenReturn(true);
        Mockito.when(bankAccountDAO.retrieveByID(accountID)).thenReturn(accounts);
        Mockito.when(bankAccountDAO.retrieveByID(secondAccount.getAccountID())).thenReturn(accounts);
        Mockito.when(bankAccountDAO.transfer(any(BankAccount.class), any(BankAccount.class))).thenReturn(true);
        Assert.assertEquals("Not Passed", true, accountService.transfer(userName, accountID, amount, secondAccount.getAccountID()));
    }

    @Test
    public void accountTransferTransactionFailed()
    {
        Mockito.when(userPairDAO.relationshipBetweenUserAndAccountExists(any(UserNameBankAccountIDPair.class))).thenReturn(true);
        Mockito.when(bankAccountDAO.retrieveByID(accountID)).thenReturn(accounts);
        Mockito.when(bankAccountDAO.retrieveByID(secondAccount.getAccountID())).thenReturn(accounts);
        Mockito.when(bankAccountDAO.transfer(any(BankAccount.class), any(BankAccount.class))).thenReturn(false);
        Assert.assertEquals("Passed", false, accountService.transfer(userName, accountID, amount, secondAccount.getAccountID()));
    }
    @Test
    public void accountTransferAccountDoesNotMatch() {
        boolean passed = true;
        try {
            Mockito.when(userPairDAO.relationshipBetweenUserAndAccountExists(any(UserNameBankAccountIDPair.class))).thenThrow(IllegalArgumentException.class);
            accountService.transfer(userName, accountID, amount, secondAccount.getAccountID());
        } catch (IllegalArgumentException e) {
            passed = false;
        }
        Assert.assertFalse(passed);
    }
    @Test
    public void accountTransferInsufficientFunds() {
        boolean passed = true;
        try {
            accountService.transfer(userName, accountID, 200000, secondAccount.getAccountID());
        } catch (IllegalArgumentException e) {
            passed = false;
        }
        Assert.assertFalse(passed);
    }
    @Test
    public void accountTransferAccountNotFound() {
        boolean passed = true;
        try {
            Mockito.when(userPairDAO.relationshipBetweenUserAndAccountExists(any(UserNameBankAccountIDPair.class))).thenReturn(true);
            Mockito.when(bankAccountDAO.retrieveByID(accountID)).thenReturn(new BankAccount[]{});
            accountService.transfer(userName, accountID, 200000, secondAccount.getAccountID());
        } catch (IllegalArgumentException e) {
            passed = false;
        }
        Assert.assertFalse(passed);
    }

    @Test
    public void accountGetTransactionPass() {
        ArrayList<Transaction> trans = new ArrayList<Transaction>();
        Mockito.when(userPairDAO.relationshipBetweenUserAndAccountExists(any(UserNameBankAccountIDPair.class))).thenReturn(true);
        Mockito.when(transactionDAO.retrieveByAccountID(accountID)).thenReturn(trans);
        Assert.assertTrue("Not Passed", accountService.getTransaction(userName, accountID).size() == 0);
    }

    @Test
    public void accountGetTransactionAccountDoesNotMatch() {
        boolean passed = true;
        try {
            Mockito.when(userPairDAO.relationshipBetweenUserAndAccountExists(any(UserNameBankAccountIDPair.class))).thenReturn(false);
            accountService.getTransaction(userName, accountID);
        } catch (IllegalArgumentException e) {
            passed = false;
        }
        Assert.assertFalse(passed);
    }

    @Test
    public void accountGetBalancePass() {
        double expected = 1100;
        BankAccount[] accounts = new BankAccount[]{account};
        Mockito.when(userPairDAO.relationshipBetweenUserAndAccountExists(any(UserNameBankAccountIDPair.class))).thenReturn(true);
        Mockito.when(bankAccountDAO.retrieveByID(accountID)).thenReturn(accounts);
        Assert.assertEquals(expected, accountService.getBalance(userName, accountID), .01);
    }

    @Test
    public void accountGetBalanceAccountDoesNotMatch() {
        double expected = 1100;
        boolean passed = true;
        try {
            Mockito.when(userPairDAO.relationshipBetweenUserAndAccountExists(any(UserNameBankAccountIDPair.class))).thenReturn(false);
            accountService.getBalance(userName, accountID);
        } catch (IllegalArgumentException e) {
            passed = false;
        }
        Assert.assertFalse(passed);
    }

}
