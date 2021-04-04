package dataAccess;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.DBConnection;
import dataModel.User;
public class UserDAO {
    protected static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO user (type,username,password)"
            + " VALUES (?,?,?)";
    private final static String findStatementString = "SELECT * FROM user where username = ?";
    private static final String deleteStatementString = "DELETE FROM user WHERE username = ?";
    private static final String updateStatementString = "UPDATE user u set u.type=?, u.password=?, u.username=? WHERE u.username=?";
    private final static String allUsersStatementString = "SELECT * FROM user";
    public static void insert(User u) {
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, u.getType());
            insertStatement.setString(2, u.getUsername());
            insertStatement.setString(3, u.getPassword());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "UserDAO:insert " + e.getMessage());
        } finally {
            DBConnection.close(insertStatement);
            DBConnection.close(dbConnection);
        }
    }

    public static User findByUsername(String username) {
        User toReturn = null;
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setString(1,username);
            rs = findStatement.executeQuery();
            if(rs.next()){
                String type = rs.getString("type");
                String password = rs.getString("password");
                toReturn = new User(type, username, password);}
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"UserDao:findByUsername " + e.getMessage());
        } finally {
            DBConnection.close(rs);
            DBConnection.close(findStatement);
            DBConnection.close(dbConnection);
        }
        return toReturn;
    }

    public static void delete(User u) {
        Connection dbConnection = DBConnection.getConnection();

        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setString(1, u.getUsername());
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "UserDao:delete " + e.getMessage());
        } finally {
            DBConnection.close(deleteStatement);
            DBConnection.close(dbConnection);
        }
    }

    public static void update(User u, String oldUsername) {
        Connection dbConnection = DBConnection.getConnection();

        PreparedStatement updateStatement = null;
        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setString(1, u.getType());
            updateStatement.setString(2, u.getPassword());
            updateStatement.setString(3, u.getUsername());
            updateStatement.setString(4, oldUsername);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "UserDao:update " + e.getMessage());
        } finally {
            DBConnection.close(updateStatement);
            DBConnection.close(dbConnection);
        }
    }

    public static ArrayList<User> allUsers() {
        ArrayList<User> toReturn = new ArrayList<User>();
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement allUsersStatement = null;
        ResultSet rs = null;
        try {
            allUsersStatement = dbConnection.prepareStatement(allUsersStatementString);
            rs = allUsersStatement.executeQuery();
            while(rs.next()){
                String type = rs.getString("type");
                String username = rs.getString("username");
                String password = rs.getString("password");
                User u = new User(type, username, password);
                toReturn.add(u);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"UserDao:allUsers " + e.getMessage());
        } finally {
            DBConnection.close(rs);
            DBConnection.close(allUsersStatement);
            DBConnection.close(dbConnection);
        }
        return toReturn;
    }
}
