package dataAccess;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.DBConnection;

import dataModel.Operation;
public class OperationDAO {
    protected static final Logger LOGGER = Logger.getLogger(OperationDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO operation (id,type,name,username,inputData)"
            + " VALUES (?,?,?,?,?)";
    private final static String findStatementString = "SELECT * FROM operation where id = ?";
    private final static String findStatementString2 = "SELECT * FROM operation where username = ?";
    private final static String findAllStatementString = "SELECT * FROM operation";
    public static void insert(Operation o) {
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, o.getId());
            insertStatement.setString(2, o.getType());
            insertStatement.setString(3, o.getName());
            insertStatement.setString(4, o.getUsername());
            insertStatement.setString(5, o.getInputData());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OperationDAO:insert " + e.getMessage());
        } finally {
            DBConnection.close(insertStatement);
            DBConnection.close(dbConnection);
        }
    }

    public static Operation findById(int id) {
        Operation toReturn = null;
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setInt(1,id);
            rs = findStatement.executeQuery();
            if(rs.next()){
                String name = rs.getString("name");
                String username = rs.getString("username");
                String type = rs.getString("type");
                String inputData = rs.getString("inputData");
                toReturn = new Operation(id,type,name,username,inputData);}
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"OperationDao:findById " + e.getMessage());
        } finally {
            DBConnection.close(rs);
            DBConnection.close(findStatement);
            DBConnection.close(dbConnection);
        }
        return toReturn;
    }

    public static ArrayList<Operation> findByUsername(String username) {
        ArrayList<Operation> toReturn = new ArrayList<Operation>();
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString2);
            findStatement.setString(1,username);
            rs = findStatement.executeQuery();
            while(rs.next()){
                String name = rs.getString("name");
                int id = rs.getInt("id");
                String type = rs.getString("type");
                String inputData = rs.getString("inputData");
                Operation o = new Operation(id,type,name,username,inputData);
                toReturn.add(o);}
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"OperationDao:findByUsername " + e.getMessage());
        } finally {
            DBConnection.close(rs);
            DBConnection.close(findStatement);
            DBConnection.close(dbConnection);
        }
        return toReturn;
    }

    public static ArrayList<Operation> findAll() {
        ArrayList<Operation> toReturn = new ArrayList<Operation>();
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement findAllStatement = null;
        ResultSet rs = null;
        try {
            findAllStatement = dbConnection.prepareStatement(findAllStatementString);
            rs = findAllStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String username = rs.getString("username");
                String inputData = rs.getString("inputData");
                Operation o = new Operation(id,type,name,username,inputData);
                toReturn.add(o);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"OperationDao:findAll " + e.getMessage());
        } finally {
            DBConnection.close(rs);
            DBConnection.close(findAllStatement);
            DBConnection.close(dbConnection);
        }
        return toReturn;
    }
}
