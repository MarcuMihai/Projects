package dataAccess;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.DBConnection;
import dataModel.Transfer;
public class TransferDAO {
    protected static final Logger LOGGER = Logger.getLogger(TransferDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO transfer (id,accountFrom,accountTo,moneySum)"
            + " VALUES (?,?,?,?)";
    private final static String findStatementString = "SELECT * FROM transfer where id = ?";
    private final static String findAllStatementString = "SELECT * FROM transfer";
    private static final String deleteStatementString = "DELETE FROM transfer WHERE id = ?";
    public static void insert(Transfer t) {
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, t.getId());
            insertStatement.setInt(2, t.getAccountFrom());
            insertStatement.setInt(3, t.getAccountTo());
            insertStatement.setDouble(4, t.getMoneySum());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "TransferDAO:insert " + e.getMessage());
        } finally {
            DBConnection.close(insertStatement);
            DBConnection.close(dbConnection);
        }
    }

    public static Transfer findById(int id) {
        Transfer toReturn = null;
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setInt(1,id);
            rs = findStatement.executeQuery();
            if(rs.next()){
                int accountFrom = rs.getInt("accountFrom");
                int accountTo = rs.getInt("accountTo");
                double moneySum = rs.getDouble("moneySum");
                toReturn = new Transfer(id, accountFrom, accountTo, moneySum);}
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"TransferDao:findById " + e.getMessage());
        } finally {
            DBConnection.close(rs);
            DBConnection.close(findStatement);
            DBConnection.close(dbConnection);
        }
        return toReturn;
    }

    public static void delete(Transfer t) {
        Connection dbConnection = DBConnection.getConnection();

        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, t.getId());
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "TransferDao:delete " + e.getMessage());
        } finally {
            DBConnection.close(deleteStatement);
            DBConnection.close(dbConnection);
        }
    }

    public static ArrayList<Transfer> allTransfers() {
        ArrayList<Transfer> toReturn = new ArrayList<Transfer>();
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement allTransfersStatement = null;
        ResultSet rs = null;
        try {
            allTransfersStatement = dbConnection.prepareStatement(findAllStatementString);
            rs = allTransfersStatement.executeQuery();
            while(rs.next()){
                int id= rs.getInt("id");
                String accountFrom = rs.getString("accountFrom");
                String accountTo = rs.getString("accountTo");
                String moneySum = rs.getString("moneySum");
                Transfer t = new Transfer(id,Integer.parseInt(accountFrom),Integer.parseInt(accountFrom),Double.parseDouble(moneySum));
                toReturn.add(t);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"TransferDao:allTrasnfers " + e.getMessage());
        } finally {
            DBConnection.close(rs);
            DBConnection.close(allTransfersStatement);
            DBConnection.close(dbConnection);
        }
        return toReturn;
    }
}
