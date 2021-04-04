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
import model.Order;
import presentation.Presentation;

/**
 * Are scopul de a manipula datele din tabela orders.
 */
public class OrderDao {
    /**
     * Initializeaza un logger pentru clasa OrderDao.
     */
    protected static final Logger LOGGER = Logger.getLogger(OrderDao.class.getName());
    /**
     * Instructiunea SQL pentru inserarea in tabela.
     */
    private static final String insertStatementString = "INSERT INTO orders (idorder,idc,finalprice)"
            + " VALUES (?,?,?)";
    /**
     * Instructiunea SQL pentru selectarea tuturor datelor unei comenzi finale cu un id specificat.
     */
    private final static String findStatementString = "SELECT * FROM orders WHERE idorder = ?";
    /**
     * Instructiunea SQL pentru selectarea tuturor datelor unei comenzi finale cu un id al clientului specificat.
     */
    private final static String findStatementString1 = "SELECT * FROM orders WHERE idc = ?";
    /**
     * Instructiunea SQL pentru selectarea datelor tuturor comenzilor finale din tabela.
     */
    private final static String findStatementString2 = "SELECT * FROM orders";
    /**
     * Instructiunea SQL pentru stergerea din tabela a unei comenzi finale cu un id specificat.
     */
    private static final String deleteStatementString = "DELETE FROM orders WHERE idorder = ?";

    /**
     *
     * @param ido
     * @return obiectul de tip Order cu id-ul dat ca parametru daca acesta se gaseste in tabela sau null.
     */
    public static Order findById(int ido) {
        Order toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setInt(1, ido);
            rs = findStatement.executeQuery();
            if(rs.next()){
            int idc = rs.getInt("idc");
            double finalprice = rs.getDouble("finalprice");
            toReturn = new Order(ido,idc,finalprice);}
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"OrderDao:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     *
     * @param idc
     * @return obiectul de tip Order cu id-ul clientului dat ca parametru daca acesta se gaseste in tabela sau null.
     */
    public static Order findByIdClient(int idc) {
        Order toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement1 = null;
        ResultSet rs = null;
        try {
            findStatement1 = dbConnection.prepareStatement(findStatementString1);
            findStatement1.setInt(1, idc);
            rs = findStatement1.executeQuery();
            if(rs.next()){
            int idorder = rs.getInt("idorder");
            double finalprice = rs.getDouble("finalprice");
            toReturn = new Order(idorder,idc,finalprice);}
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"OrderDao:findByIdClient " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement1);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     *Realizeaza inserarea comenzilor din tabela orders in tabelul PDF specific comenzilor finale.
     * @param t
     */
    public static void findReport(PdfPTable t) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement2 = null;
        ResultSet rs = null;
        try {
            findStatement2 = dbConnection.prepareStatement(findStatementString2);
            rs = findStatement2.executeQuery();
            if(rs.next()){
                int idorder=rs.getInt("idorder");
                int idc=rs.getInt("idc");
                double price = rs.getDouble("finalprice");
                Presentation.addRowsOrder(t,idorder+"",idc+"",price+"");
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
     *Insereaza o comanda data ca parametru in tabela orders.
     * @param o
     * @return id-ul comenzii inserate sau -1.
     */
    public static int insert(Order o) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, o.getIdorder());
            insertStatement.setInt(2, o.getIdc());
            insertStatement.setDouble(3, o.getFinalprice());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDao:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * Sterge unei comenzi date ca parametru din tabela orders.
     * @param o
     * @return id-ul comenzii sterse sau -1.
     */
    public static int delete(Order o) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement deleteStatement = null;
        int deletedId = -1;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, o.getIdorder());
            deleteStatement.executeUpdate();

            ResultSet rs = deleteStatement.getGeneratedKeys();
            if (rs.next()) {
                deletedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDao:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return deletedId;
    }

}
