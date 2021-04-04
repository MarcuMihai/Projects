package dataModel;

public class Client {
    private int idc;
    private String name, idCardNr, cnp, adress;
    public Client(int id,String name, String nrIdCard, String cnp, String adress){
        idc=id;
        this.name=name;
        idCardNr=nrIdCard;
        this.cnp=cnp;
        this.adress=adress;
    }

    public int getIdc() {
        return idc;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getIdCardNr() {
        return idCardNr;
    }

    public void setIdCardNr(String idCardNr) {
        this.idCardNr = idCardNr;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
