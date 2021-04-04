package model;

/**
 * In aceasta clasa sunt descrise caracteristicile unui client, fiind salvate in variabile: id-ul clientului, numele si orasul in care locuieste.
 */
public class Client {
    /**
     * reprezinta id-ul clientului.
     */
    private int idc;
    /**
     * reprezinta numele clientului.
     */
    private String name;
    /**
     * reprezinta orasul in care clientul locuieste.
     */
    private String city;

    /**
     * Initializeaza un client nou cu datele oferite ca parametrii.
     * @param id
     * @param name
     * @param city
     */
    public Client(int id,String name,String city){
        idc=id;
        this.name=name;
        this.city=city;
    }

    /**
     *
     * @return string-ul in care este salvat numele clientului.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return numarul ce repreznta id-ul clientului.
     */
    public int getIdc() {
        return idc;
    }

    /**
     *
     * @return string-ul in care este salvat orasul in care locuieste clientul.
     */
    public String getCity() {
        return city;
    }

    /**
     * metoda de transformare al unui obiect de tipul Client intr-unul de tip String.
     * @return
     */
    @Override
    public String toString() {
        return "Client{" +
                "idc=" + idc +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
