package dataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.ConnectionFactory;
import model.OrderItem;

/**
 * Are scopul de a manipula datele din tabela orderitem.
 */
public class OrderItemDao {
    /**
     * Initializeaza un logger pentru clasa OrderItemDao.
     */
    protected static final Logger LOGGER = Logger.getLogger(OrderItemDao.class.getName());
    /**
     * Instructiunea SQL pentru inserarea in tabela.
     */
    private static final String insertStatementString = "INSERT INTO orderitem (idorderitem,idc,idproduct,quantity)"
            + " VALUES (?,?,?,?)";
    /**
     * Instructiunea SQL pentru selectarea tuturor datelor unei comenzi cu un id specificat.
     */
    private final static String findStatementString = "SELECT * FROM orderitem where idorderitem = ?";

    /**
     *
     * @param idoi
     * @return obiectul de tip OrderItem cu id-ul dat ca parametru daca acesta se gaseste in tabela sau null.
     */
    public static OrderItem findById(int idoi) {
        OrderItem toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, idoi);
            rs = findStatement.executeQuery();
            if(rs.next()){
            int idc = rs.getInt("idc");
            int idproduct = rs.getInt("idproduct");
            int quantity = rs.getInt("quantity");
            toReturn = new OrderItem(idoi,idc,idproduct,quantity);}
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"OrderItemDao:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     * Insereaza o comanda data ca parametru in tabela orderitem.
     * @param oi
     * @return id-ul comenzii inserate sau -1.
     */
    public static int insert(OrderItem oi) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, oi.getIdorderitem());
            insertStatement.setInt(2, oi.getIdc());
            insertStatement.setInt(3, oi.getIdproduct());
            insertStatement.setInt(4, oi.getQuantity());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderItemDao:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

}
