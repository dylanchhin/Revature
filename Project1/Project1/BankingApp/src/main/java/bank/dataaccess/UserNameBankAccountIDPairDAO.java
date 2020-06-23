package bank.dataaccess;


import bank.model.UserNameBankAccountIDPair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/***
 *
 * The UserNameBankAccountIDPairDAO class implements the DAO interface to provide direct interaction with the database for information relating to the user name and accountID pairs.
 *
 * @author Shawyn Kane
 */
public class UserNameBankAccountIDPairDAO implements DAO<UserNameBankAccountIDPair, Integer> {

    private PostGresConnectionUtil postgresqlConnection;
    private final static String tableName = "useraccountsbankaccounts";

    public UserNameBankAccountIDPairDAO(PostGresConnectionUtil postgresqlConnection) {
        this.postgresqlConnection = postgresqlConnection;
    }

    /***
     *
     * The save(...) method inserts the passed email and bank account id pair information into the useraccountsbankaccounts table in the database to create a new bank account.
     * It does not check to see if an account matches the information provided before creating a new account in the database.
     * @author Shawyn Kane
     * @param obj
     * @return
     */
    @Override
    public Integer save(UserNameBankAccountIDPair obj) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = postgresqlConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO " + postgresqlConnection.getDefaultSchema() + "." + tableName + " (email, accountid) VALUES (?,?)");
            statement.setString(1, obj.getCustomerID());
            statement.setInt(2, obj.getAccountID());

            statement.executeUpdate();
            return obj.getAccountID();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return OPERATION_FAILED;
    }

    /***
     * The retrieveAll() method retrieves all the email and bank account id pair information from the database. This is not actually used, even though it has been implemented.
     *
     * @author Shawyn Kane
     * @return
     */
    @Deprecated
    @Override
    public ArrayList<UserNameBankAccountIDPair> retrieveAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "SELECT * FROM " + postgresqlConnection.getDefaultSchema() + "." + tableName;
        ArrayList<UserNameBankAccountIDPair> userNameBankAccountIDPairs = new ArrayList<>();

        try {
            connection = postgresqlConnection.getConnection();
            String schema = postgresqlConnection.getDefaultSchema();
            statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                userNameBankAccountIDPairs.add(new UserNameBankAccountIDPair(resultSet.getInt("accountID"), resultSet.getString("email")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userNameBankAccountIDPairs;
    }

    /***
     *
     * The retrieveByID(...) method retrieves the email and bank account id pair information from the database for the provided accountID.
     * There could be zero associations, if there is no account with the provided accountID, in which case it will return an UserNameBankAccountIDPair array (UserNameBankAccountIDPair[]) of length zero.
     * If there is an account with the provide accountID then the method will return all email and accountID pairs that have a matching accountID.
     *
     * @author Shawyn Kane
     * @param accountID
     * @return
     */
    @Override
    public UserNameBankAccountIDPair[] retrieveByID(Integer accountID) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "SELECT * FROM " + postgresqlConnection.getDefaultSchema() + "." + tableName + " WHERE accountID = ?";
        ArrayList<UserNameBankAccountIDPair> userNameBankAccountIDPairs = new ArrayList<>();

        try {
            connection = postgresqlConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, accountID);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                userNameBankAccountIDPairs.add(new UserNameBankAccountIDPair(resultSet.getInt("accountID"), resultSet.getString("email")));
            }

            return userNameBankAccountIDPairs.toArray(new UserNameBankAccountIDPair[]{});

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new UserNameBankAccountIDPair[]{};
    }

    /***
     * This method deletes the email and accountID pair from the database that associates a particular user login account and a particular bank account.
     * @author Shawyn Kane
     * @param pair
     */
    @Override
    public boolean delete(UserNameBankAccountIDPair pair) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "DELETE FROM " + postgresqlConnection.getDefaultSchema() + "." + tableName + " WHERE accountID = ? AND email = ?";
        ArrayList<UserNameBankAccountIDPair> userNameBankAccountIDPairs = new ArrayList<>();

        try {
            connection = postgresqlConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, pair.getAccountID());
            statement.setString(2, pair.getCustomerID());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /***
     * This version of the overloaded delete method deletes all the email and accountID pair from the database that associates a particular user login account and a particular bank account, that match the email provided as a parameter.
     * @author Shawyn Kane
     * @param email
     */
    public boolean delete(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "DELETE FROM " + postgresqlConnection.getDefaultSchema() + "." + tableName + " WHERE email = ?";
        ArrayList<UserNameBankAccountIDPair> userNameBankAccountIDPairs = new ArrayList<>();

        try {
            connection = postgresqlConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1,  email );
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /***
     * This version of the overloaded delete method deletes all the email and accountID pair from the database that associates a particular user login account and a particular bank account, that match the accountID provided as a parameter.
     * @author Shawyn Kane
     * @param accountID
     */
    public boolean delete(int accountID) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "DELETE FROM " + postgresqlConnection.getDefaultSchema() + "." + tableName + " WHERE accountID = ?";
        ArrayList<UserNameBankAccountIDPair> userNameBankAccountIDPairs = new ArrayList<>();

        try {
            connection = postgresqlConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, accountID);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /***
     * This method is not implemented and is not supported and will throw an UnsupportedOperationException if invoked.
     *
     * @author Shawyn Kane
     * @param newObj
     * @throws UnsupportedOperationException
     */
    @Deprecated
    @Override
    public boolean update(UserNameBankAccountIDPair newObj) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /***
     * This version of the overload method retrieves the email and bank account id pair information from the database for the provided accountID.
     * There could be zero associations, if there is no account with the provided accountID, in which case it will return an UserNameBankAccountIDPair array (UserNameBankAccountIDPair[]) of length zero.
     * If there is an account with the provide accountID then the method will return all email and accountID pairs that have a matching accountID.
     * @param email
     * @return
     */
    public UserNameBankAccountIDPair[] retrieveByID(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "SELECT * FROM " + postgresqlConnection.getDefaultSchema() + "." + tableName + " WHERE email = ?";
        ArrayList<UserNameBankAccountIDPair> userNameBankAccountIDPairs = new ArrayList<>();

        try {
            connection = postgresqlConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                userNameBankAccountIDPairs.add(new UserNameBankAccountIDPair(resultSet.getInt("accountID"), resultSet.getString("email")));
            }

            return userNameBankAccountIDPairs.toArray(new UserNameBankAccountIDPair[]{});

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new UserNameBankAccountIDPair[]{};
    }

    /***
     * This method just checks to see if the email and bank acount id pair exists in the database and returns true if the pair exists and false if it does not.
     * @author Shawyn Kane
     * @param pair
     * @return
     */
    public boolean relationshipBetweenUserAndAccountExists(UserNameBankAccountIDPair pair) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "SELECT * FROM " + postgresqlConnection.getDefaultSchema() + "." + tableName + " WHERE email = ? and accountid = ?";
        ArrayList<UserNameBankAccountIDPair> userNameBankAccountIDPairs = new ArrayList<>();

        try {
            connection = postgresqlConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, pair.getCustomerID());
            statement.setInt(2, pair.getAccountID());

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            System.out.println("There was an networking issue. Please try again, later.");
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
