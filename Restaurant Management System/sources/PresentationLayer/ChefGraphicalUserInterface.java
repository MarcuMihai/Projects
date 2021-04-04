package PresentationLayer;

import BusinessLayer.MenuItem;
import BusinessLayer.Order;
import BusinessLayer.Restaurant;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class ChefGraphicalUserInterface extends JFrame implements Observer{
    String s;
    JTextArea ta= new JTextArea(1, 1);
    JPanel panel=new JPanel();
    Restaurant model;
    public ChefGraphicalUserInterface(Restaurant model){
        this.model = model;
        model.addObserver(this);
        this.setSize(500,150);
        this.add(panel);
        this.setTitle("Chef Menu");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
    @Override
    public void update(Observable o, Object arg) {
        s="";
        ta.setEditable(false);
        HashMap<Order, ArrayList<MenuItem>> orders = (HashMap<Order, ArrayList<MenuItem>>) arg;
        for(HashMap.Entry<Order, ArrayList<MenuItem>> x : orders.entrySet()) {
            s="De pregatit comanda cu id-ul: ";
            s+=x.getKey().getOrderId()+" care contine produsele: ";
            for (MenuItem m : x.getValue()) {
                s+=m.getName()+", ";
            }
            s+=" ";
            s=s.replace(",  ",".");
            s+="\n";
        }
        ta.append(s);
        panel.add(ta);
    }
}
