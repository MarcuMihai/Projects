package DataLayer;
import BusinessLayer.MenuItem;
import BusinessLayer.Order;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class RestaurantSerializator {
    private ArrayList<MenuItem> items;
    private HashMap<Order, Collection<MenuItem>> hmap;
    public void deserialization(String s) throws IOException, ClassNotFoundException {
        items = new ArrayList<MenuItem>();
        hmap=new HashMap<Order, Collection<MenuItem>>();
        File f=new File(s);
        if(f.exists()) {
            FileInputStream fileIn = new FileInputStream(s);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            if (fileIn.available() == 0)
                return;
            else {
                items = (ArrayList<MenuItem>) in.readObject();
                if (fileIn.available() == 0)
                    return;
                else hmap = (HashMap<Order, Collection<MenuItem>>) in.readObject();
            }
            in.close();
            fileIn.close();
        }
    }
    public void serialization(String s, ArrayList<MenuItem> items, HashMap<Order, Collection<MenuItem>> hmap) throws IOException {
        FileOutputStream fis = new FileOutputStream(s);
        ObjectOutputStream out = new ObjectOutputStream(fis);
        out.writeObject(items);
        out.writeObject(hmap);
        out.close();
        fis.close();
    }
    public ArrayList<MenuItem> getItems() {
        return items;
    }
    public HashMap<Order, Collection<MenuItem>> getHmap() {
        return hmap;
    }
}
