package model;

/**
 * In aceasta clasa sunt descrise caracteristicile unei comenzi finale, acestea fiind salvate in variabile: id-ul comenzii, id-ul clientului care a realizat comanda si pretul final.
 */
public class Order {
    /**
     * id-ul comenzii.
     */
    private int idorder;
    /**
     * id-ul clientului care a realizat comanda.
     */
    private int idc;
    /**
     * pretul final pe care trebuie sa-l plateasca clientul pentru toate produsele achizitionate.
     */
    private double finalprice;

    /**
     * Initializeaza o comanda noua cu datele oferite ca parametrii.
     * @param ido
     * @param idc
     * @param finalprice
     */
    public Order(int ido,int idc, double finalprice){
        idorder=ido;
        this.idc=idc;
        this.finalprice=finalprice;
    }

    /**
     * numarul ce repreznta id-ul comenzii.
     * @return
     */
    public int getIdorder() {
        return idorder;
    }

    /**
     *
     * @return pretul final ce trebuie platit.
     */
    public double getFinalprice() {
        return finalprice;
    }

    /**
     *
     * @return numarul ce repreznta id-ul clientului.
     */
    public int getIdc() {
        return idc;
    }

    /**
     * seteaza finalprice cu valoarea primita ca parametru.
     * @param finalprice
     */
    public void setFinalprice(double finalprice) {
        this.finalprice = finalprice;
    }

    /**
     * metoda de transformare al unui obiect de tipul Order intr-unul de tip String.
     * @return
     */
    @Override
    public String toString() {
        return "Order{" +
                "idorder=" + idorder +
                ", idc=" + idc +
                ", finalprice=" + finalprice +
                '}';
    }
}
