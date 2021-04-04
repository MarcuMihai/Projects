package PresentationLayer;

import BusinessLayer.MenuItem;
import BusinessLayer.Order;
import BusinessLayer.WaiterOperations;
import DataLayer.RestaurantSerializator;
import main.MainClass;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;

public class WaiterGUIController {
    private WaiterOperations model;
    private WaiterGraphicalUserInterface view;
    public WaiterGUIController(WaiterOperations model, WaiterGraphicalUserInterface view) {
        this.model = model;
        this.view = view;
        view.addCreateOrderListener(new CreateOrderListener());
        view.addComputePriceListener(new ComputePriceListener());
        view.addGenerateBillListener(new GenerateBillListener());
        view.addExitBtnListener(new ExitBtnListener());
        view.addShowOrdersListener(new ShowOrdersListener());
        view.addUpdateOrdersTableListener(new UpdateOrdersTableListener());
    }
    class CreateOrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name=view.getInputName();
            int tableNr=Integer.parseInt(view.getInputTableNr());
            model.createOrder(tableNr,name);
        }
    }
    class ComputePriceListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int id=Integer.parseInt(view.getInputOrderId());
            double price=model.computePriceForOrder(id);
            view.setOutputPrice(price+"");
        }
    }
    class GenerateBillListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int id=Integer.parseInt(view.getInputOrderId());
            model.generateBill(id);
        }
    }
    class ShowOrdersListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            HashMap<Order, Collection<MenuItem>> hmap;
            hmap=model.getHmap();
            JFrame Jf=new JFrame();
            String rows[][];
            String columns[];
            int i=0;
            if(hmap.isEmpty()){
                rows=new String[1][1];
                columns=new String[]{"Empty"};
                rows[0][0]="There are no current orders!";
            }
            else {
                rows=new String[hmap.size()][3];
                columns=new String[]{"Order id","Date","Table number"};
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                for (Order x:hmap.keySet())
                {
                    rows[i][0]=x.getOrderId()+"";
                    rows[i][1]=df.format(x.getDate());
                    rows[i][2]=x.getTable()+"";
                    i++;
                }
            }
            final JTable table=new JTable(rows,columns);
            Jf.add(new JScrollPane(table));
            Jf.setLocationRelativeTo(null);
            Jf.setVisible(true);
            Jf.pack();
        }
    }
    class UpdateOrdersTableListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            RestaurantSerializator r2=new RestaurantSerializator();
            try {
                r2.serialization(MainClass.getFileName(),model.getItems(),model.getHmap());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    class ExitBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.dispose();
        }
    }
}
