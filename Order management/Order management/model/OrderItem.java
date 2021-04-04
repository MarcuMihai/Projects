package model;

/**
 * In aceasta clasa sunt descrise caracteristicile unei comenzi, acestea fiind salvate in variabile: id-ul comenzii, id-ul clientului care a realizat comanda, id-ul produsului cumparat si cantitatea in care acesta a fost solicitat.
 */
public class OrderItem {
    /**
     * id-ul comenzii.
     */
    private int idorderitem;
    /**
     * id-ul clientului care a realizat comanda.
     */
    private int idc;
    /**
     * id-ul produsului comandat.
     */
    private int idproduct;
    /**
     * cantitatea in care a fost solicitat produsul.
     */
    private int quantity;

    /**
     * Initializeaza o comanda noua cu datele oferite ca parametrii.
     * @param idoi
     * @param idc
     * @param idp
     * @param q
     */
    public OrderItem(int idoi,int idc,int idp,int q) {
        idorderitem = idoi;
        this.idc = idc;
        idproduct = idp;
        quantity=q;
    }

    /**
     *
     * @return id-ul comenzii.
     */
    public int getIdorderitem() {
        return idorderitem;
    }

    /**
     *
     * @return id-ul clientului care a realizat comanda.
     */
    public int getIdc() {
        return idc;
    }

    /**
     *
     * @return id-ul produsului comandat.
     */
    public int getIdproduct() {
        return idproduct;
    }

    /**
     *
     * @return cantitatea solicitata.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * metoda de transformare al unui obiect de tipul OrderItem intr-unul de tip String.
     * @return
     */
    @Override
    public String toString() {
        return "OrderItem{" +
                "idorderitem=" + idorderitem +
                ", idc=" + idc +
                ", idproduct=" + idproduct +
                ", quantity=" + quantity +
                '}';
    }
}
