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
import model.Product;
import presentation.Presentation;

/**
 * Are scopul de a manipula datele din tabela product.
 */
public class ProductDao {
    /**
     * Initializeaza un logger pentru clasa ProductDao.
     */
    protected static final Logger LOGGER = Logger.getLogger(ProductDao.class.getName());
    /**
     * Instructiunea SQL pentru inserarea in tabela.
     */
    private static final String insertStatementString = "INSERT INTO product (idproduct,name,quantity,price)"
            + " VALUES (?,?,?,?)";
    /**
     * Instructiunea SQL pentru selectarea tuturor datelor unui produs cu un id specificat.
     */
    private final static String findStatementString = "SELECT * FROM product where idproduct = ?";
    /**
     * Instructiunea SQL pentru selectarea tuturor datelor unui produs cu un nume specificat.
     */
    private final static String findStatementString1 = "SELECT * FROM product where name = ?";
    /**
     * Instructiunea SQL pentru selectarea tuturor datelor produselor.
     */
    private final static String findStatementString2 = "SELECT * FROM product";
    /**
     * Instructiunea SQL pentru stergerea din tabela a unui produs cu un id specificat.
     */
    private static final String deleteStatementString = "DELETE FROM product WHERE idproduct = ?";

    /**
     *
     * @param idp
     * @return obiectul de tip Product cu id-ul dat ca parametru daca acesta se gaseste in tabela sau null.
     */
    public static Product findById(int idp) {
        Product toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setInt(1,idp);
            rs = findStatement.executeQuery();
            if(rs.next()){
            String name = rs.getString("name");
            int quantity = rs.getInt("quantity");
            double price = rs.getDouble("price");
            toReturn = new Product(idp,name,quantity,price);}
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ProductDao:findById " + e.getMessage());
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
     * @return obiectul de tip Produs cu numele dat ca parametru daca acesta se gaseste in tabela sau null.
     */
    public static Product findByName(String name) {
        Product toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement1 = null;
        ResultSet rs = null;
        try {
            findStatement1 = dbConnection.prepareStatement(findStatementString1);
            findStatement1.setString(1,name);
            rs = findStatement1.executeQuery();
            if(rs.next()){
            int idp=rs.getInt("idproduct");
            int quantity = rs.getInt("quantity");
            double price = rs.getDouble("price");
            toReturn = new Product(idp,name,quantity,price);}
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
     * Realizeaza inserarea produselor din tabela product in tabelul PDF specific produselor.
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
                int idp=rs.getInt("idproduct");
                String name=rs.getString("name");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                Presentation.addRowsProduct(t,idp+"",name,quantity+"",price+"");
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
     * Insereaza un produs dat ca parametru in tabela product.
     * @param p
     * @return id-ul produsului inserat sau -1.
     */
    public static int insert(Product p) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, p.getIdproduct());
            insertStatement.setString(2, p.getName());
            insertStatement.setInt(3, p.getQuantity());
            insertStatement.setDouble(4, p.getPrice());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDao:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * Sterge un produs dat ca parametru din tabela product.
     * @param p
     * @return id-ul produsului sters sau -1.
     */
    public static int delete(Product p) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement deleteStatement = null;
        int deletedId = -1;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, p.getIdproduct());
            deleteStatement.executeUpdate();

            ResultSet rs = deleteStatement.getGeneratedKeys();
            if (rs.next()) {
                deletedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDao:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return deletedId;
    }

}
