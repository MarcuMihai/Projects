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
import dataModel.Client;
import dataModel.User;

public class ClientDAO {
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO client (idc,name,idCardNr,cnp,adress)"
            + " VALUES (?,?,?,?,?)";
    private final static String findStatementString = "SELECT * FROM client where idc = ?";
    private final static String findStatementString2 = "SELECT * FROM client where name = ?";
    private final static String findAllStatementString = "SELECT * FROM client";
    private static final String deleteStatementString = "DELETE FROM client WHERE idc = ?";
    private static final String updateStatementString = "UPDATE client c set c.name=?, c.idCardNr=?, c.cnp=?, c.adress=? WHERE c.idc=?";
    public static void insert(Client c) {
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, c.getIdc());
            insertStatement.setString(2, c.getName());
            insertStatement.setString(3, c.getIdCardNr());
            insertStatement.setString(4, c.getCnp());
            insertStatement.setString(5, c.getAdress());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            DBConnection.close(insertStatement);
            DBConnection.close(dbConnection);
        }
    }

    public static Client findById(int idc) {
        Client toReturn = null;
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setInt(1,idc);
            rs = findStatement.executeQuery();
            if(rs.next()){
                String name = rs.getString("name");
                String idCardNr = rs.getString("idCardNr");
                String cnp = rs.getString("cnp");
                String adress = rs.getString("adress");
                toReturn = new Client(idc, name, idCardNr, cnp, adress);}
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDao:findById " + e.getMessage());
        } finally {
            DBConnection.close(rs);
            DBConnection.close(findStatement);
            DBConnection.close(dbConnection);
        }
        return toReturn;
    }

    public static Client findByName(String name) {
        Client toReturn = null;

        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement findStatement1 = null;
        ResultSet rs = null;
        try {
            findStatement1 = dbConnection.prepareStatement(findStatementString2);
            findStatement1.setString(1,name);
            rs = findStatement1.executeQuery();
            if(rs.next()){
                int idc=rs.getInt("idc");
                String idCardNr = rs.getString("idCardNr");
                String cnp = rs.getString("cnp");
                String adress = rs.getString("adress");
                toReturn = new Client(idc, name, idCardNr, cnp, adress);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDao:findByName " + e.getMessage());
        } finally {
            DBConnection.close(rs);
            DBConnection.close(findStatement1);
            DBConnection.close(dbConnection);
        }
        return toReturn;
    }

    public static void delete(Client c) {
        Connection dbConnection = DBConnection.getConnection();

        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, c.getIdc());
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDao:delete " + e.getMessage());
        } finally {
            DBConnection.close(deleteStatement);
            DBConnection.close(dbConnection);
        }
    }

    public static void update(Client c) {
        Connection dbConnection = DBConnection.getConnection();

        PreparedStatement updateStatement = null;
        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setString(1, c.getName());
            updateStatement.setString(2, c.getIdCardNr());
            updateStatement.setString(3, c.getCnp());
            updateStatement.setString(4, c.getAdress());
            updateStatement.setInt(5, c.getIdc());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDao:update " + e.getMessage());
        } finally {
            DBConnection.close(updateStatement);
            DBConnection.close(dbConnection);
        }
    }
    public static ArrayList<Client> allClients() {
        ArrayList<Client> toReturn = new ArrayList<Client>();
        Connection dbConnection = DBConnection.getConnection();
        PreparedStatement allClientsStatement = null;
        ResultSet rs = null;
        try {
            allClientsStatement = dbConnection.prepareStatement(findAllStatementString);
            rs = allClientsStatement.executeQuery();
            while(rs.next()){
                int idc= rs.getInt("idc");
                String name = rs.getString("name");
                String idCardNr = rs.getString("idCardNr");
                String cnp = rs.getString("cnp");
                String adress = rs.getString("adress");
                Client c = new Client(idc,name,idCardNr,cnp,adress);
                toReturn.add(c);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDao:allClients " + e.getMessage());
        } finally {
            DBConnection.close(rs);
            DBConnection.close(allClientsStatement);
            DBConnection.close(dbConnection);
        }
        return toReturn;
    }

}
