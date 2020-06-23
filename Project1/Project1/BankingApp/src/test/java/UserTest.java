import bank.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User testUser1;

    @Before
    public void init() {
        testUser1 = new User("george@gmail.com", "George", "Smith", "12345", "1234567890", "customer");
    }

    @Test
    public void equalsEmailDiffersTest() {
        User testUser = new User("george1@gmail.com", testUser1.getFirstName(), testUser1.getLastName(), testUser1.getPassword(), testUser1.getPhoneNumber(), testUser1.getRole());
        Assert.assertEquals(false, testUser.equals(testUser1));
    }

    @Test
    public void equalsFirstNameDiffersTest() {
        User testUser = new User(testUser1.getEmail(), "Bobby", testUser1.getLastName(), testUser1.getPassword(), testUser1.getPhoneNumber(), testUser1.getRole());
        Assert.assertEquals(false, testUser.equals(testUser1));
    }

    @Test
    public void equalsLastNameDiffersTest() {
        User testUser = new User(testUser1.getEmail(), testUser1.getFirstName(), "Doe", testUser1.getPassword(), testUser1.getPhoneNumber(), testUser1.getRole());
        Assert.assertEquals(false, testUser.equals(testUser1));
    }

    @Test
    public void equalsPasswordDiffersTest() {
        User testUser = new User(testUser1.getEmail(), testUser1.getFirstName(), testUser1.getLastName(), "incorrect", testUser1.getPhoneNumber(), testUser1.getRole());
        Assert.assertEquals(false, testUser.equals(testUser1));
    }

    @Test
    public void equalsPhoneNumberDiffersTest() {
        User testUser = new User(testUser1.getEmail(), testUser1.getFirstName(), testUser1.getLastName(), testUser1.getPassword(), "0987654321", testUser1.getRole());
        Assert.assertEquals(false, testUser.equals(testUser1));
    }

    @Test
    public void equalsRoleDiffersTest() {
        User testUser = new User(testUser1.getEmail(), testUser1.getFirstName(), testUser1.getLastName(), testUser1.getPassword(), testUser1.getPhoneNumber(), "newRole");
        Assert.assertEquals(false, testUser.equals(testUser1));
    }

    @Test
    public void equalsNothingDiffersTest() {
        User testUser = new User(testUser1.getEmail(), testUser1.getFirstName(), testUser1.getLastName(), testUser1.getPassword(), testUser1.getPhoneNumber(), testUser1.getRole());
        Assert.assertEquals(true, testUser.equals(testUser1));
    }

    @Test
    public void missingInformationNothingMissingTest() {
        Assert.assertEquals(false, testUser1.missingInformation());
    }

    @Test
    public void missingInformationEmailNullTest() {
        testUser1.setEmail(null);
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationEmailEmptyStringTest() {
        testUser1.setEmail("");
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationEmailWhiteSpaceTest() {
        testUser1.setEmail("     ");
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationFirstNameNullTest() {
        testUser1.setFirstName(null);
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationFirstNameEmptyStringTest() {
        testUser1.setFirstName("");
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationFirstNameWhiteSpaceTest() {
        testUser1.setFirstName("     ");
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationLastNameNullTest() {
        testUser1.setLastName(null);
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationLastNameEmptyStringTest() {
        testUser1.setLastName("");
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationLastNameWhiteSpaceTest() {
        testUser1.setLastName("     ");
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationPasswordNullTest() {
        testUser1.setPassword(null);
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationPasswordEmptyStringTest() {
        testUser1.setPassword("");
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationPasswordWhiteSpaceTest() {
        testUser1.setPassword("     ");
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationPhoneNumberNullTest() {
        testUser1.setPhoneNumber(null);
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationPhoneNumberEmptyStringTest() {
        testUser1.setPhoneNumber("");
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationPhoneNumberWhiteSpaceTest() {
        testUser1.setPhoneNumber("     ");
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationRoleNullTest() {
        testUser1.setRole(null);
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationRoleEmptyStringTest() {
        testUser1.setRole("");
        Assert.assertEquals(true, testUser1.missingInformation());
    }

    @Test
    public void missingInformationRoleWhiteSpaceTest() {
        testUser1.setRole("     ");
        Assert.assertEquals(true, testUser1.missingInformation());
    }
}
