package bank.dataaccess;
import bank.model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class TransactionDAO implements DAO<Transaction, Integer>
{

    private PostGresConnectionUtil postGresConnectionUtil = null;
    private String tableName = "transactions";

    public TransactionDAO() {
        postGresConnectionUtil = new PostGresConnectionUtil();
    }

    public TransactionDAO(PostGresConnectionUtil postGresConnectionUtil) {
        this.postGresConnectionUtil = postGresConnectionUtil;

    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Integer save(Transaction obj) {
        Connection connection = null;
        try {
            connection = postGresConnectionUtil.getConnection();
            // Insert the bankaccounts table
            String saveStatement = "INSERT INTO " + postGresConnectionUtil.getDefaultSchema() + "." + tableName + " (transactionid, accountid, previousbalance, " +
                    "transactionamount, description, timeoftransaction) VALUES (default,?,?,?,?,?)";
            PreparedStatement bankAccountStatement = connection.prepareStatement(saveStatement);
            bankAccountStatement.setInt(1, obj.getUserID());
            bankAccountStatement.setDouble(2, obj.getPreviousBalance());
            bankAccountStatement.setDouble(3, obj.getTransactionAmount());
            bankAccountStatement.setString(4, obj.getDescription());
            bankAccountStatement.setTimestamp(5, obj.getTimeOfTransaction());
            bankAccountStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     *
     * @return
     */
    @Override
        public ArrayList<Transaction> retrieveAll() {
            Connection connection = null;
            ArrayList<Transaction> transactions = new ArrayList<>();

            try {
                connection = postGresConnectionUtil.getConnection();
                String sql = "SELECT * FROM " + postGresConnectionUtil.getDefaultSchema() + "." + tableName;
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    transactions.add(new Transaction(resultSet.getInt("transactionid"),
                            resultSet.getInt("accountID"),
                            resultSet.getDouble("previousbalance"),
                            resultSet.getDouble("transactionamount"),
                            resultSet.getString("description"),
                            resultSet.getTimestamp("timeoftransaction")));
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
            return transactions;
        }

    /**
     *
     * @param integer
     * @return
     */
    @Override
        public Transaction[] retrieveByID(Integer integer) {
            return new Transaction[0];
        }

        public ArrayList<Transaction> retrieveByAccountID(Integer integer) {
            Connection connection = null;
            ArrayList<Transaction> transactions = new ArrayList<>();

            try {
                connection = postGresConnectionUtil.getConnection();
                String sql = "SELECT * FROM " + postGresConnectionUtil.getDefaultSchema() + "." + tableName + " WHERE accountid =?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, integer);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    transactions.add(new Transaction(resultSet.getInt("transactionid"),
                            resultSet.getInt("accountID"),
                            resultSet.getDouble("previousbalance"),
                            resultSet.getDouble("transactionamount"),
                            resultSet.getString("description"),
                            resultSet.getTimestamp("timeoftransaction")));
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
            return transactions;
        }

    /**
     *
     * @param obj
     * @return
     */
    @Override
        public boolean delete(Transaction obj) {
            return false;
        }

    /**
     *
     * @param newObj
     * @return
     */
    @Override
    public boolean update(Transaction newObj) {
        return false;
    }

}
