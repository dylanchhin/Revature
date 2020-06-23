import bank.dataaccess.UserDAO;
import bank.model.User;
import bank.services.UserServices;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/***
 *
 * @author Shawyn Kane
 */
public class UserServicesTest {
    private User testUser1;
    private User testUser2;
    private User testUserMissingInfo;

    @Mock
    private UserDAO userDAO = null;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        testUser1 = new User("george@gmail.com", "George", "Smith", "12345", "1234567890", "customer");
        testUser2 = new User("bob@gmail.com", "Bob", "Smith", "12345","1111111111", "customer");
        testUserMissingInfo = new User("", "", "", "", "", "");
    }

    @Test
    public void retrieveUserByEmailReturnsUserTest() {
        User[] users = new User[]{testUser1};
        Mockito.when(userDAO.retrieveByID(testUser1.getEmail())).thenReturn(users);
        UserServices us = new UserServices(userDAO);

        Assert.assertEquals(testUser1, us.retrieveUserByEmail(testUser1.getEmail()));
    }

    @Test
    public void retrieveUserByEmailReturnsNullTest() {
        User[] users = new User[0];
        Mockito.when(userDAO.retrieveByID(testUser1.getEmail())).thenReturn(users);
        UserServices us = new UserServices(userDAO);

        Assert.assertEquals(null, us.retrieveUserByEmail(testUser1.getEmail()));
    }

    @Test
    public void areEmailOrPasswordEmptyStringsWithEmptyEmail() {
        UserServices us = new UserServices();
        Assert.assertEquals(true, us.areEmailOrPasswordEmptyStrings("", testUser1.getPassword()));
    }

    @Test
    public void areEmailOrPasswordEmptyStringsWithEmptyPassword() {
        UserServices us = new UserServices();
        Assert.assertEquals(true, us.areEmailOrPasswordEmptyStrings(testUser1.getEmail(), ""));
    }

    @Test
    public void validateUserInformationMissingUserInformation() {
        User testUser = testUserMissingInfo;
        User[] returnedUser = new User[]{testUser};
        Mockito.when(userDAO.retrieveByID(testUser.getEmail())).thenReturn(returnedUser);
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(false, us.validateUserInformation(testUser));
    }

    @Test
    public void validateUserInformationUserDoesNotExist() {
        User testUser = testUserMissingInfo;
        User[] returnedUser = new User[0];
        Mockito.when(userDAO.retrieveByID(testUser.getEmail())).thenReturn(returnedUser);
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(false, us.validateUserInformation(testUser));
    }

    @Test
    public void validateUserInformationUserInformationMatches() {
        User testUser = testUser1;
        User[] returnedUser = new User[]{testUser};
        Mockito.when(userDAO.retrieveByID(testUser.getEmail())).thenReturn(returnedUser);
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(true, us.validateUserInformation(testUser));
    }

    @Test
    public void validateUserInformationUserInformationDoesNotMatch() {
        User testUser = testUser1;
        User[] returnedUser = new User[]{new User(testUser1.getEmail(), testUser1.getFirstName(), testUser1.getLastName(), "3245", "1111111111", testUser1.getRole())};
        Mockito.when(userDAO.retrieveByID(testUser.getEmail())).thenReturn(returnedUser);
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(false, us.validateUserInformation(testUser));
    }

    @Test
    public void loginEmptyStringForCredentials() {
        User testUser = testUserMissingInfo;
        User[] returnedUser = new User[]{testUser};
        Mockito.when(userDAO.retrieveByID(testUser.getEmail())).thenReturn(returnedUser);
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(false, us.login(testUser.getEmail(), testUser.getPassword()));
    }

    @Test
    public void loginUserDoesNotExist() {
        User testUser = testUser1;
        User[] returnedUser = new User[0];
        Mockito.when(userDAO.retrieveByID(testUser.getEmail())).thenReturn(returnedUser);
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(false, us.login(testUser.getEmail(), testUser.getPassword()));
    }

    @Test
    public void loginSuccessful() {
        User testUser = testUser1;
        User[] returnedUser = new User[]{testUser};
        Mockito.when(userDAO.retrieveByID(testUser.getEmail())).thenReturn(returnedUser);
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(true, us.login(testUser.getEmail(), testUser.getPassword()));
    }

    @Test
    public void createUserMissingInformation() {
        User testUser = testUserMissingInfo;
        Mockito.when(userDAO.save(testUser)).thenReturn(testUser.getEmail());
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(false, us.createUser(testUser));
    }

    @Test
    public void createUserFailedToCreate() {
        User testUser = testUser1;
        Mockito.when(userDAO.save(testUser)).thenReturn(null);
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(false, us.createUser(testUser));
    }

    @Test
    public void createUserSuccessful() {
        User testUser = testUser1;
        Mockito.when(userDAO.save(testUser)).thenReturn(testUser.getEmail());
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(true, us.createUser(testUser));
    }

    @Test
    public void updateUserMissingInformation() {
        User testUser = testUserMissingInfo;
        Mockito.when(userDAO.update(testUser)).thenReturn(true);
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(false, us.updateUser(testUser));
    }

    @Test
    public void updateUserFailedToCreate() {
        User testUser = testUser1;
        Mockito.when(userDAO.update(testUser)).thenReturn(false);
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(false, us.updateUser(testUser));
    }

    @Test
    public void updateUserSuccessful() {
        User testUser = testUser1;
        Mockito.when(userDAO.update(testUser)).thenReturn(true);
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(true, us.updateUser(testUser));
    }

    @Test
    public void deleteUserMissingInformation() {
        User testUser = testUserMissingInfo;
        Mockito.when(userDAO.delete(testUser)).thenReturn(true);
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(false, us.deleteUser(testUser));
    }

    @Test
    public void deleteUserFailedToCreate() {
        User testUser = testUser1;
        Mockito.when(userDAO.delete(testUser)).thenReturn(false);
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(false, us.deleteUser(testUser));
    }

    @Test
    public void deleteUserSuccessful() {
        User testUser = testUser1;
        Mockito.when(userDAO.delete(testUser)).thenReturn(true);
        UserServices us = new UserServices(userDAO);
        Assert.assertEquals(true, us.deleteUser(testUser));
    }

}
