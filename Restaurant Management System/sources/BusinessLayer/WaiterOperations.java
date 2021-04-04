package BusinessLayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
/**
 * In aceasta interfata sunt declarate metodele pe care le poate realiza un ospatar.
 */
public interface WaiterOperations {
    /**
     * Aceasta metoda are scopul de a creea o comanda noua si un arraylist care contine produsele ce constituie aceasta comanda.
     * @param nrTable
     * @param s
     */
    public void createOrder(int nrTable,String s);
    /**
     * Are scopul de a calcula pretul final al unei comenzi.
     * @param id
     * @return
     */
    public double computePriceForOrder(int id);
    /**
     * Are scopul de a genera o factura pentru fiecare comanda curenta care sa contina datele despre aceasta.
     * @param id
     */
    public void generateBill(int id);
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
