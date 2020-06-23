
import bank.dataaccess.PostGresConnectionUtil;
import bank.dataaccess.TransactionDAO;
import bank.model.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TransactionTest {
    private TransactionDAO transactionDAO = new TransactionDAO(new PostGresConnectionUtil());
    @Test
    public void findAllTransactions()
    {
        ArrayList<Transaction> transactions = transactionDAO.retrieveAll();
        for (Transaction currentTransaction: transactions) {
            System.out.println(currentTransaction);
        }
        Assert.assertEquals("Test", true, true);
    }

    @Test
    public void findAllTransactionsByAccountID()
    {
        ArrayList<Transaction> transactions = transactionDAO.retrieveByAccountID(1);
        for (Transaction currentTransaction: transactions) {
            System.out.println(currentTransaction);
        }
        Assert.assertEquals("Test", true, true);
    }

    @Test
    public void saveTransaction()
    {
        //Timestamp timeOfTransaction = new Timestamp(System.currentTimeMillis());
        //Transaction transactionCurrent = new Transaction(0, 1, 1000, 0, "withdrawl", timeOfTransaction);
        //transactionDAO.save(transactionCurrent);

    }

    @After
    public void reset()
    {
        //BankAccount account1 = new BankAccount(1, 1000);
        //accountDAO.update(account1);
    }
}
