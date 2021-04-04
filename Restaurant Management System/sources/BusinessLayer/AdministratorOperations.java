package BusinessLayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
/**
 * In aceasta interfata sunt declarate metodele pe care le poate realiza un administrator.
 */
public interface AdministratorOperations {
    /**
     * Adauga meniului(arraylist-ului) un produs nou care poate fi ori base product ori composite product.
     * @param name
     * @param type
     * @param price
     * @param components
     */
    public void createMenuItem(String name, String type, double price, ArrayList<MenuItem> components);
    /**
     * Are scopul de a modifica datele despre un produs din meniu.
     * @param name
     * @param newname
     * @param type
     * @param price
     * @param components
     */
    public void editMenuItem(String name,String newname,String type,double price,ArrayList<MenuItem> components);
    /**
     * Are scopul de a sterge un produs din meniu.
     * @param name
     */
    public void deleteMenuItem(String name);
    /**
     *
     * @return lista in care sunt salvate produsele restaurantului.
     */
    public ArrayList<MenuItem> getItems();
    /**
     * HashMap-ul in care sunt salvate comenzile si listele cu produsele din fiecare dintre acestea.
     * @return
     */
    public HashMap<Order, Collection<MenuItem>> getHmap();
    public boolean isWellFormed();
}
