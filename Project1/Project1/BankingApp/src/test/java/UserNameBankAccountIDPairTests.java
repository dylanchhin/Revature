import bank.model.UserNameBankAccountIDPair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserNameBankAccountIDPairTests {
    private UserNameBankAccountIDPair pair1 = new UserNameBankAccountIDPair(1, "bob@gmail.com");

    @Before
    public void init() {
        pair1 = new UserNameBankAccountIDPair(1, "bob@gmail.com");
    }

    @Test
    public void equalsUserNameOff() {
        UserNameBankAccountIDPair pair2 = new UserNameBankAccountIDPair(pair1.getAccountID(), "joe@gmail.com");
        Assert.assertEquals(false, pair1.equals(pair2));
    }

    @Test
    public void equalsAccountIDOff() {
        UserNameBankAccountIDPair pair2 = new UserNameBankAccountIDPair(2, pair1.getCustomerID());
        Assert.assertEquals(false, pair1.equals(pair2));
    }

    @Test
    public void equalsMatches() {
        UserNameBankAccountIDPair pair2 = new UserNameBankAccountIDPair(pair1.getAccountID(), pair1.getCustomerID());
        Assert.assertEquals(true, pair1.equals(pair2));
    }
}
