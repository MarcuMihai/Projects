package dataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.itextpdf.text.pdf.PdfPTable;
import connection.ConnectionFactory;
import model.Client;
import presentation.Presentation;

/**
 * Are scopul de a manipula datele din tabela client.
 */
public class ClientDao {
    /**
     * Initializeaza un logger pentru clasa ClientDao.
     */
    protected static final Logger LOGGER = Logger.getLogger(ClientDao.class.getName());
    /**
     *Instructiunea SQL pentru inserarea in tabela.
     */
    private static final String insertStatementString = "INSERT INTO client (idc,name,city)"
            + " VALUES (?,?,?)";
    /**
     * Instructiunea SQL pentru selectarea tuturor datelor unui client cu un id specificat.
     */
    private final static String findStatementString = "SELECT * FROM client where idc = ?";
    /**
     * Instructiunea SQL pentru selectarea tuturor datelor unui client cu un nume specificat.
     */
    private final static String findStatementString1 = "SELECT * FROM client where name = ?";
    /**
     * Instructiunea SQL pentru selectarea tuturor datelor clientilor.
     */
    private final static String findStatementString2 = "SELECT * FROM client";
    /**
     * Instructiunea SQL pentru stergerea din tabela a unui client cu un id specificat.
     */
    private static final String deleteStatementString = "DELETE FROM client WHERE idc = ?";

    /**
     *
     * @param idc
     * @return obiectul de tip Client cu id-ul dat ca parametru daca acesta se gaseste in tabela sau null.
     */
    public static Client findById(int idc) {
        Client toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setInt(1,idc);
            rs = findStatement.executeQuery();
            if(rs.next()){
            String name = rs.getString("name");
            String city = rs.getString("city");
            toReturn = new Client(idc, name, city);}
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDao:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     *
     * @param name
     * @return obiectul de tip Client cu numele dat ca parametru daca acesta se gaseste in tabela sau null.
     */
    public static Client findByName(String name) {
        Client toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement1 = null;
        ResultSet rs = null;
        try {
            findStatement1 = dbConnection.prepareStatement(findStatementString1);
            findStatement1.setString(1,name);
            rs = findStatement1.executeQuery();
            if(rs.next()){
            int idc=rs.getInt("idc");
            String city = rs.getString("city");
            toReturn = new Client(idc, name, city);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDao:findByName " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement1);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     * Realizeaza inserarea clientilor din tabela client in tabelul PDF specific clientilor.
     * @param t
     */
    public static void findReport(PdfPTable t) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement2 = null;
        ResultSet rs = null;
        try {
            findStatement2 = dbConnection.prepareStatement(findStatementString2);
            rs = findStatement2.executeQuery();
            while(rs.next()){
                int idc=rs.getInt("idc");
                String name=rs.getString("name");
                String city = rs.getString("city");
                Presentation.addRowsClient(t,idc,name,city);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDao:findReport " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement2);
            ConnectionFactory.close(dbConnection);
        }
    }

    /**
     *Insereaza un client dat ca parametru in tabela client.
     * @param c
     * @return id-ul clientului inserat sau -1.
     */
    public static int insert(Client c) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, c.getIdc());
            insertStatement.setString(2, c.getName());
            insertStatement.setString(3, c.getCity());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDao:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * Sterge un client dat ca parametru din tabela client.
     * @param c
     * @return id-ul clientului sters sau -1.
     */
    public static int delete(Client c) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement deleteStatement = null;
        int deletedId = -1;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, c.getIdc());
            deleteStatement.executeUpdate();

            ResultSet rs = deleteStatement.getGeneratedKeys();
            if (rs.next()) {
                deletedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDao:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return deletedId;
    }
}
