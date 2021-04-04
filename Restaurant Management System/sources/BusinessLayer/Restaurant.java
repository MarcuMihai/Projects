package BusinessLayer;

import DataLayer.FileWriter;
import DataLayer.RestaurantSerializator;
import main.MainClass;
import java.io.IOException;
import java.util.*;
/**
 *In aceasta clasa sunt implementate metodele declarate in interfetele specifice unui administrator si unui ospatar. Clasa Restaurant contine un arraylist de obiecte de tipul MenuItem in care este salvat meniul restaurantului si un hashmap care contine fiecare order si produsele din care este alcatuita aceasta.
 */
public class Restaurant extends Observable implements AdministratorOperations,WaiterOperations{
    private ArrayList<MenuItem> items;
    private HashMap<Order, Collection<MenuItem>> hmap;
    int orderid;
    /**
     *Constructorul clasei, in acesta se initializeaza un obiect de tipul RestaurantSerializator care preia obiectele din fisierul in care se salveaza datele din restaurant si le atribuie pe acestea variabilelor din clasa.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Restaurant() throws IOException, ClassNotFoundException {
        RestaurantSerializator rs1=new RestaurantSerializator();
        rs1.deserialization(MainClass.getFileName());
        this.items=rs1.getItems();
        this.hmap=rs1.getHmap();
        if(hmap.isEmpty())
            orderid=0;
        else {
            for (Order o:hmap.keySet()) {
                orderid=o.getOrderId();
            }
        }
    }
    /**
     *Adauga meniului(arraylist-ului) un produs nou care poate fi ori base product ori composite product. Cand se adauga produsul este parcurs si arraylist-ul pentru a face verificarea daca acesta se regaseste deja in meniu sau nu.
     * @param name
     * @param type
     * @param price
     * @param components
     */
    @Override
    public void createMenuItem(String name,String type,double price,ArrayList<MenuItem> components) {
        assert isWellFormed();
        assert !name.equals("");
        int ok=1;
        if(type.equals("base")) {
            for (MenuItem x:items) {
                if(name.equals(x.getName()))
                    ok=0;
            }
            if(ok==1) {
                MenuItem m = new BaseProduct(name, price);
                items.add(m);
            }
        }
        else if(type.equals("composite")){
            ok=1;
            for (MenuItem x:items) {
                if(name.equals(x.getName()))
                    ok=0;
            }
            if(ok==1) {
                MenuItem m = new CompositeProduct(name, components);
                items.add(m);
            }
        }
        assert items!=null;
        assert isWellFormed();
    }
    /**
     *Are scopul de a modifica datele despre un produs din meniu. Daca se modifica pretul unui produs de baza, atunci se va actualiza si pretul produselor compuse care il au pe acesta in compozitie.
     * @param name
     * @param newname
     * @param type
     * @param price
     * @param components
     */
    @Override
    public void editMenuItem(String name,String newname,String type,double price,ArrayList<MenuItem> components) {
        assert !name.equals("");
        assert isWellFormed();
        deleteMenuItem(name);
        createMenuItem(newname,type,price,components);
        assert items!=null;
        assert isWellFormed();
    }
    /**
     *Are scopul de a sterge un produs din meniu. Daca produsul sters este unul de baza atunci se vor sterge si produsele compuse care il au pe acesta in compozitie.
     * @param name
     */
    @Override
    public void deleteMenuItem(String name) {
        assert !name.equals("");
        assert isWellFormed();
        MenuItem m=null;
        for (MenuItem x:items) {
            if(x.getName().equals(name)){
                m=x;
            }
        }
        items.remove(m);
        assert isWellFormed();
    }
    /**
     * Aceasta metoda are scopul de a creea o comanda noua si un arraylist care contine produsele ce constituie aceasta comanda. Comanda si arraylist-ul sunt salvate intr-o structura de tipul HashMap.
     * @param nrTable
     * @param products
     */
    @Override
    public void createOrder(int nrTable,String products) {
        assert isWellFormed();
        assert nrTable>0;
        assert !products.equals("");
        orderid++;
        Date date=new Date();
        Order o=new Order(orderid,date,nrTable);
        ArrayList<MenuItem> orderItems=new ArrayList<MenuItem>();
        String[] list=products.split(",");
        for (String s:list) {
            for (MenuItem prod : items) {
                if (s.equals(prod.getName()))
                    orderItems.add(prod);
            }
        }
        hmap.put(o,orderItems);
        setChanged();
        notifyObservers(hmap);
        assert hmap!=null;
        assert isWellFormed();
    }
    /**
     * Are scopul de a calcula pretul final al unei comenzi.
     * @param id
     * @return
     */
    @Override
    public double computePriceForOrder(int id) {
        assert id>0;
        assert isWellFormed();
        double price=0;
        for (Map.Entry<Order,Collection<MenuItem>> x:hmap.entrySet()) {
            if(x.getKey().getOrderId()==id){
                for (MenuItem m:x.getValue()) {
                    price+=m.computePrice();
                }
            }
        }
        assert isWellFormed();
        assert price>0;
        return price;
    }
    /**
     *Are scopul de a genera o factura pentru fiecare comanda curenta care sa contina datele despre aceasta.
     * @param id
     */
    @Override
    public void generateBill(int id) {
        assert isWellFormed();
        assert id>0;
        Order o=null;
        for (Map.Entry<Order,Collection<MenuItem>> x:hmap.entrySet()) {
            if (x.getKey().getOrderId() == id) {
                o = x.getKey();
            }
        }
        String s=o.toString();
        s+=" si suma de plata este in valoare de: "+computePriceForOrder(o.getOrderId());
        FileWriter fw=new FileWriter();
        fw.writeInTxt(s,o.getOrderId());
        assert isWellFormed();
    }
    /**
     *
     * @return true daca arraylist-ul de produse din meniul restaurantului nu este null si daca hashmap-ul cu comenzile restaurantului nu este la randul sau nici el null.
     */
    public boolean isWellFormed(){
        boolean ok=true;
        for (MenuItem m:items) {
            if(m==null)
                ok=false;
        }
        for (Map.Entry<Order,Collection<MenuItem>> x:hmap.entrySet()) {
            if(x==null)
                ok=false;
        }
        return ok;
    }
    /**
     *
     * @return lista in care sunt salvate produsele restaurantului.
     */
    public ArrayList<MenuItem> getItems() {
        return items;
    }
    /**
     *
     * @return HashMap-ul in care sunt salvate comenzile si listele cu produsele din fiecare dintre acestea.
     */
    public HashMap<Order, Collection<MenuItem>> getHmap() {
        return hmap;
    }
}
