package dataAccess;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.DBConnection;
import dataModel.Account;
import dataModel.Client;

public class AccountDAO {
    protected static final Logger LOGGER = Logger.getLogger(AccountDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO account (id,idClient,number,type,moneyAmount,creationDate)"
            + " VALUES (?,?,?,?,?,?)";
    private final static String findStatementString = "SELECT * FROM account where id = ?";
    private final static String findStatementString2 = "SELECT * FROM account where idClient = ?";
    private final static String findAllStatementString = "SELECT * FROM account";
    private static final String deleteStatementString = "DELETE FROM account where id = ?";
    private static final String updateStatementString = "UPDATE account a set a.idClient=?,a.number=?, a.type=?, a.moneyAmount=?, a.creationDate=? WHERE a.id=?";
    public static void insert(Account a) {
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, a.getId());
            insertStatement.setInt(2, a.getIdClient());
            insertStatement.setString(3, a.getNumber());
            insertStatement.setString(4, a.getType());
            insertStatement.setDouble(5, a.getMoneyAmount());
            insertStatement.setDate(6, a.getCreationDate());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AccountDAO:insert " + e.getMessage());
        } finally {
            DBConnection.close(insertStatement);
            DBConnection.close(dbConnection);
        }
    }

    public static Account findById(int id) {
        Account toReturn = null;
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setInt(1,id);
            rs = findStatement.executeQuery();
            if(rs.next()){
                int idClient = rs.getInt("idClient");
                String number = rs.getString("number");
                String type = rs.getString("type");
                double moneyAmount = rs.getDouble("moneyAmount");
                Date creationDate = rs.getDate("creationDate");
                toReturn = new Account(id, idClient, number, type, moneyAmount, creationDate);}
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"AccountDao:findById " + e.getMessage());
        } finally {
            DBConnection.close(rs);
            DBConnection.close(findStatement);
            DBConnection.close(dbConnection);
        }
        return toReturn;
    }

    public static ArrayList<Account> findByClientId(int idClient) {
        ArrayList<Account> toReturn=new ArrayList<Account>();
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString2);
            findStatement.setInt(1,idClient);
            rs = findStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String number = rs.getString("number");
                String type = rs.getString("type");
                double moneyAmount = rs.getDouble("moneyAmount");
                Date creationDate = rs.getDate("creationDate");
                Account a = new Account(id, idClient,number, type, moneyAmount, creationDate);
                toReturn.add(a);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"AccountDao:findById " + e.getMessage());
        } finally {
            DBConnection.close(rs);
            DBConnection.close(findStatement);
            DBConnection.close(dbConnection);
        }
        return toReturn;
    }

    public static void delete(Account a) {
        Connection dbConnection = DBConnection.getConnection();

        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, a.getId());
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AccountDao:delete " + e.getMessage());
        } finally {
            DBConnection.close(deleteStatement);
            DBConnection.close(dbConnection);
        }
    }

    public static void update(Account a) {
        Connection dbConnection = DBConnection.getConnection();

        PreparedStatement updateStatement = null;
        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setInt(1, a.getIdClient());
            updateStatement.setString(2, a.getNumber());
            updateStatement.setString(3, a.getType());
            updateStatement.setDouble(4, a.getMoneyAmount());
            updateStatement.setDate(5, a.getCreationDate());
            updateStatement.setInt(6, a.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AccountDao:update " + e.getMessage());
        } finally {
            DBConnection.close(updateStatement);
            DBConnection.close(dbConnection);
        }
    }
    public static ArrayList<Account> allAccounts() {
        ArrayList<Account> toReturn = new ArrayList<Account>();
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement allAccountsStatement = null;
        ResultSet rs = null;
        try {
            allAccountsStatement = dbConnection.prepareStatement(findAllStatementString);
            rs = allAccountsStatement.executeQuery();
            while(rs.next()){
                int id= rs.getInt("id");
                int idClient= rs.getInt("idClient");
                String number = rs.getString("number");
                String type = rs.getString("type");
                Double moneyAmount = rs.getDouble("moneyAmount");
                Date creationDate = rs.getDate("creationDate");
                Account a = new Account(id,idClient,number,type,moneyAmount,creationDate);
                toReturn.add(a);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"AccountDao:allAccounts " + e.getMessage());
        } finally {
            DBConnection.close(rs);
            DBConnection.close(allAccountsStatement);
            DBConnection.close(dbConnection);
        }
        return toReturn;
    }
}
