package BusinessLayer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order implements java.io.Serializable{
    private int orderId;
    private Date date;
    private int table;
    public Order(int id,Date date,int nrTable){
        orderId=id;
        this.date=date;
        table=nrTable;
    }
    public int hashCode(){
        return orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public Date getDate() {
        return date;
    }

    public int getTable() {
        return table;
    }

    public String toString(){
        String s=new String();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        s+="Comanda are numarul: "+orderId+" a fost plasata la data si ora: "+df.format(date)+", este destinata mesei cu numarul: "+table;
        return s;
    }
}
