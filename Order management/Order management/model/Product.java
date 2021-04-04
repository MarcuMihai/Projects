package model;

/**
 * In aceasta clasa sunt descrise caracteristicile unui produs, acestea fiind salvate in variabile: id-ul produsului, numele acestuia, cantitatea disponibila in stoc si pretul unei bucati.
 */
public class Product {
    /**
     * id-ul produsului.
     */
    private int idproduct;
    /**
     * numele produsului.
     */
    private String name;
    /**
     * cantitatea de produs disponibila.
     */
    private int quantity;
    /**
     * pretul unei bucati.
     */
    private double price;

    /**
     * Initializeaza un produs nou cu datele oferite ca parametrii.
     * @param id
     * @param name
     * @param quantity
     * @param price
     */
    public Product(int id,String name,int quantity,double price){
        idproduct=id;
        this.name=name;
        this.quantity=quantity;
        this.price=price;
    }

    /**
     *
     * @return id-ul produsului.
     */
    public int getIdproduct() {
        return idproduct;
    }

    /**
     *
     * @return numele produsului.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return cantitatea produsului.
     */
    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    /**
     * seteaza id-ul produsului cu valoarea primita ca parametru.
     * @param idproduct
     */
    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }

    /**
     * seteaza cantitatea disponibila de produs cu valoarea primita ca parametru.
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * metoda de transformare al unui obiect de tipul Produs intr-unul de tip String.
     * @return
     */
    @Override
    public String toString() {
        return "Product{" +
                "idproduct=" + idproduct +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
