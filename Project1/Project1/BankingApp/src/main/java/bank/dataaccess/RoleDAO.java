package bank.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/***
 *
 * @author Shawyn Kane
 */
public class RoleDAO implements DAO<String, Integer> {
    private PostGresConnectionUtil postGresConnectionUtil;
    private final String ROLE_TABLE_NAME = "roles";
    private final String ROLE_NAME_COLUMN_NAME = "rolename";
    private final String ROLE_TABLE_ROLE_ID_COLUMN_NAME = "roleid";
    private String schemaRoleTableName;
    private String fullRoleTableRoleIDColumnName;
    private String fullRoleNameColumnName;

    public RoleDAO() {
        postGresConnectionUtil = new PostGresConnectionUtil();
        init();
    }

    public RoleDAO(PostGresConnectionUtil postGresConnectionUtil) {
        this.postGresConnectionUtil = postGresConnectionUtil;
        init();
    }

    private void init() {
        schemaRoleTableName = postGresConnectionUtil.getDefaultSchema() + "." + ROLE_TABLE_NAME;
        fullRoleTableRoleIDColumnName = schemaRoleTableName + "." + ROLE_TABLE_ROLE_ID_COLUMN_NAME;
        fullRoleNameColumnName = schemaRoleTableName + "." + ROLE_NAME_COLUMN_NAME;
    }

    @Deprecated
    @Override
    public Integer save(String obj) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArrayList<String> retrieveAll() {
        ArrayList<String> roles = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "SELECT " + fullRoleNameColumnName + " FROM " + schemaRoleTableName;

        try {
            connection = postGresConnectionUtil.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) roles.add(resultSet.getString(ROLE_NAME_COLUMN_NAME));

            return roles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Deprecated
    @Override
    public String[] retrieveByID(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean delete(String obj) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean update(String newObj) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
