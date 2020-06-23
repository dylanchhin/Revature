package bank.dataaccess;

import bank.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/***
 *
 * @author Shawyn Kane
 * @see DAO
 */
public class UserDAO implements DAO<User, String> {

    private PostGresConnectionUtil postGresConnectionUtil;
    private final String USER_TABLE_NAME = "users";
    private final String ROLE_TABLE_NAME = "roles";
    private final String EMAIL_COLUMN_NAME = "email";
    private final String FIRST_NAME_COLUMN_NAME = "firstname";
    private final String LAST_NAME_COLUMN_NAME = "lastname";
    private final String PASSWORD_COLUMN_NAME = "password";
    private final String PHONE_NUMBER_COLUMN_NAME = "phonenumber";
    private final String USER_TABLE_ROLE_ID_COLUMN_NAME = "roleid";
    private final String ROLE_TABLE_ROLE_ID_COLUMN_NAME = "roleid";
    private final String ROLE_NAME_COLUMN_NAME = "rolename";

    private String schemaUserTableName;
    private String schemaRoleTableName;
    private String fullEmailColumnName;
    private String fullFirstNameColumnName;
    private String fullLastNameColumnName;
    private String fullPasswordColumnName;
    private String fullPhoneNumberColumnName;
    private String fullUserTableRoleIDColumnName;
    private String fullRoleTableRoleIDColumnName;
    private String fullRoleNameColumnName;

    public UserDAO() {
        postGresConnectionUtil = new PostGresConnectionUtil();
        init();
    }

    public UserDAO(PostGresConnectionUtil postGresConnectionUtil) {
        this.postGresConnectionUtil = postGresConnectionUtil;
        init();
    }

    private void init() {
        schemaUserTableName = postGresConnectionUtil.getDefaultSchema() + "." + USER_TABLE_NAME;
        schemaRoleTableName = postGresConnectionUtil.getDefaultSchema() + "." + ROLE_TABLE_NAME;
        fullEmailColumnName = schemaUserTableName + "." + EMAIL_COLUMN_NAME;
        fullFirstNameColumnName = schemaUserTableName + "." + FIRST_NAME_COLUMN_NAME;
        fullLastNameColumnName = schemaUserTableName + "." + LAST_NAME_COLUMN_NAME;
        fullPasswordColumnName = schemaUserTableName + "." + PASSWORD_COLUMN_NAME;
        fullPhoneNumberColumnName = schemaUserTableName + "." + PHONE_NUMBER_COLUMN_NAME;
        fullUserTableRoleIDColumnName = schemaUserTableName + "." + USER_TABLE_ROLE_ID_COLUMN_NAME;
        fullRoleTableRoleIDColumnName = schemaRoleTableName + "." + ROLE_TABLE_ROLE_ID_COLUMN_NAME;
        fullRoleNameColumnName = schemaRoleTableName + "." + ROLE_NAME_COLUMN_NAME;
    }

    @Override
    public String save(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "INSERT INTO " + schemaUserTableName + " (" + EMAIL_COLUMN_NAME + ", " + FIRST_NAME_COLUMN_NAME + ", " + LAST_NAME_COLUMN_NAME + ", " + PASSWORD_COLUMN_NAME + ", " + PHONE_NUMBER_COLUMN_NAME + ", " + USER_TABLE_ROLE_ID_COLUMN_NAME +
                ") VALUES (?, ?, ?, ?, ?, (SELECT " + fullRoleTableRoleIDColumnName + " FROM " + schemaRoleTableName + " WHERE " + fullRoleNameColumnName + " = ?))";

        try {
            connection = postGresConnectionUtil.getConnection();
            // Setup query
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getRole());


            // execute query
            statement.executeUpdate();
            // if everything worked without exception or error return the username passed in
            return user.getEmail();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // if something went wrong return null
        return null;
    }

    @Override
    public ArrayList<User> retrieveAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT " + fullEmailColumnName + ", " + fullFirstNameColumnName + ", " + fullLastNameColumnName + ", " + fullPasswordColumnName + ", " + fullPhoneNumberColumnName + ", " + fullRoleNameColumnName + " FROM " + schemaUserTableName + " INNER JOIN " + schemaRoleTableName + " ON (" + fullUserTableRoleIDColumnName + " = " + fullRoleTableRoleIDColumnName + ") ";
        try {
            connection = postGresConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                users.add(new User(resultSet.getString(EMAIL_COLUMN_NAME),
                        resultSet.getString(FIRST_NAME_COLUMN_NAME),
                        resultSet.getString(LAST_NAME_COLUMN_NAME),
                        resultSet.getString(PASSWORD_COLUMN_NAME),
                        resultSet.getString(PHONE_NUMBER_COLUMN_NAME),
                        resultSet.getString(ROLE_NAME_COLUMN_NAME)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User[] retrieveByID(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "SELECT " + fullEmailColumnName + ", " + fullFirstNameColumnName + ", " + fullLastNameColumnName + ", " + fullPasswordColumnName + ", " + fullPhoneNumberColumnName + ", " + fullRoleNameColumnName + " FROM " + schemaUserTableName + " INNER JOIN " + schemaRoleTableName + " ON (" + fullUserTableRoleIDColumnName + " = " + fullRoleTableRoleIDColumnName + ") " + "WHERE " + fullEmailColumnName + " = ?";
        try {
            connection = postGresConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) return new User[]{ new User(resultSet.getString(EMAIL_COLUMN_NAME), resultSet.getString(FIRST_NAME_COLUMN_NAME), resultSet.getString(LAST_NAME_COLUMN_NAME), resultSet.getString(PASSWORD_COLUMN_NAME), resultSet.getString(PHONE_NUMBER_COLUMN_NAME), resultSet.getString(ROLE_NAME_COLUMN_NAME))};
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new User[0];
    }

    @Override
    public boolean delete(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "DELETE FROM " + schemaUserTableName + " WHERE " + fullEmailColumnName + " = ?";
        try {
            connection = postGresConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates user table with new information
     * @param user Current USer updated info
     * @return if the table was updated or not
     */
    @Override
    public boolean update(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "UPDATE " + schemaUserTableName + " SET " + EMAIL_COLUMN_NAME + " = ?, "
                + FIRST_NAME_COLUMN_NAME + " = ?, "
                + LAST_NAME_COLUMN_NAME + " = ?, " + PASSWORD_COLUMN_NAME + " = ?, "
                + PHONE_NUMBER_COLUMN_NAME + " = ?, "
                + USER_TABLE_ROLE_ID_COLUMN_NAME + " = (SELECT " + fullRoleTableRoleIDColumnName + " FROM "
                + schemaRoleTableName + " WHERE " + fullRoleNameColumnName + " = ?) WHERE " + fullEmailColumnName + " = ?";
        try
        {
            connection = postGresConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getRole());
            statement.setString(7, user.getEmail());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
