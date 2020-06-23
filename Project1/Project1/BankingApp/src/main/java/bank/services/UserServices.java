package bank.services;

import bank.dataaccess.PostGresConnectionUtil;
import bank.dataaccess.UserDAO;
import bank.model.User;

import java.util.ArrayList;

/***
 *
 * @author Shawyn Kane
 */
public class UserServices {
    private UserDAO userDAO;

    public UserServices() {
        userDAO = new UserDAO();
    }

    public UserServices(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserServices(PostGresConnectionUtil postGresConnectionUtil) { this.userDAO = new UserDAO(postGresConnectionUtil); }

    public User retrieveUserByEmail(String email) {
        User[] retrievedUser = userDAO.retrieveByID(email);
        if (retrievedUser.length == 0) return null;
        return retrievedUser[0];
    }

    public boolean areEmailOrPasswordEmptyStrings(String email, String password) {
        return (email.trim().isEmpty() || password.trim().isEmpty());
    }

    public boolean validateUserInformation(User user) {
        if (user.missingInformation()) return false;
        User retrievedUser = retrieveUserByEmail(user.getEmail());
        if (retrievedUser == null) return false;
        return user.equals(retrievedUser);
    }

    public boolean login(String email, String password) {
        if (areEmailOrPasswordEmptyStrings(email, password)) return false;
        User retrievedUser = retrieveUserByEmail(email);
        if (retrievedUser != null && retrievedUser.getEmail().equals(email) && retrievedUser.getPassword().equals(password)) return true;
        return false;
    }

    public boolean createUser(User user) {
        if (user.missingInformation()) return false;
        if (userDAO.save(user) != null) return true;
        return false;
    }

    public boolean updateUser(User user) {
        if (user.missingInformation()) return false;
        return userDAO.update(user);
    }

    public boolean deleteUser(User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) return false;
        return userDAO.delete(user);
    }

    public ArrayList<User> getAllUsers() {
        return userDAO.retrieveAll();
    }
}
