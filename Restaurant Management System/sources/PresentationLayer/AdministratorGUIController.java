package PresentationLayer;

import BusinessLayer.*;
import DataLayer.RestaurantSerializator;
import main.MainClass;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class AdministratorGUIController {
    private AdministratorOperations model;
    private AdministratorGraphicalUserInterface view;

    public AdministratorGUIController(AdministratorOperations model, AdministratorGraphicalUserInterface view) {
        this.model = model;
        this.view = view;
        view.addCreateItemListener(new CreateItemListener());
        view.addEditItemListener(new EditItemListener());
        view.addDeleteItemListener(new DeleteItemListener());
        view.addExitBtnListener(new ExitBtnListener());
        view.addBaseProductListener(new BaseProductListener());
        view.addCompositeProductListener(new CompositeProductListener());
        view.addShowMenuListener(new ShowMenuListener());
        view.addUpdateMenuListener(new UpdateMenuListener());
    }

    class CreateItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String type=view.getTip();
            String name=view.getInputName();
            double price=0;
            ArrayList<MenuItem> components=new ArrayList<MenuItem>();
            if(type.equals("base"))
                price=Double.parseDouble(view.getInputPrice());
            ArrayList<MenuItem> items=model.getItems();
            String list=view.getInputComponents();
            String[] lists=list.split(",");
            for (String s:lists) {
                for (MenuItem prod : items) {
                    if (s.equals(prod.getName()))
                        components.add(prod);
                }
            }
            model.createMenuItem(name,type,price,components);
        }
    }
    class EditItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String type=view.getTip();
            String name=view.getInputName();
            String newname=view.getInputNewName();
            if(newname.equals(""))
                newname=name;
            double price=0;
            ArrayList<MenuItem> components=new ArrayList<MenuItem>();
            if(type.equals("base"))
                price=Double.parseDouble(view.getInputPrice());
            ArrayList<MenuItem> items=model.getItems();
            String list=view.getInputComponents();
            String[] lists=list.split(",");
            for (String s:lists) {
                for (MenuItem prod : items) {
                    if (s.equals(prod.getName()))
                        components.add(prod);
                }
            }
            model.editMenuItem(name,newname,type,price,components);
            ArrayList<MenuItem> productsIncluded=new ArrayList<MenuItem>();
            MenuItem bp=null;
            for (MenuItem x:items) {
                if (x.getClass().getName().equals(x.getClass().getPackageName() + ".CompositeProduct")) {
                    for (MenuItem y :((CompositeProduct) x).getComponents()) {
                        if (y.getName().equals(name)) {
                            bp=(BaseProduct) y;
                            productsIncluded.add(x);
                        }
                    }
                }
            }
            for (MenuItem m:productsIncluded) {
                ((CompositeProduct) m).getComponents().remove(bp);
                ((BaseProduct)bp).setPrice(price);
                ((CompositeProduct) m).getComponents().add((MenuItem) bp);
                model.editMenuItem(m.getName(),m.getName(),"composite",price,((CompositeProduct) m).getComponents());
            }
        }
    }
    class DeleteItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name=view.getInputName();
            model.deleteMenuItem(name);
            ArrayList<MenuItem> items=model.getItems();
            ArrayList<String> productsIncluded=new ArrayList<String>();
            int i=0;
            for (MenuItem x:items) {
                if (x.getClass().getName().equals(x.getClass().getPackageName() + ".CompositeProduct")) {
                    for (MenuItem y :((CompositeProduct) x).getComponents()) {
                        if (y.getName().equals(name)) {
                            productsIncluded.add(x.getName());
                        }
                    }
                }
            }
            for (String str:productsIncluded) {
                model.deleteMenuItem(str);
            }
        }
    }
    class ExitBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.dispose();
        }
    }
    class BaseProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.setTip("base");
        }
    }
    class CompositeProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            view.setTip("composite");
        }
    }
    class ShowMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            ArrayList<MenuItem> items=model.getItems();
            JFrame Jf=new JFrame();
            String rows[][];
            String  columns[];
            int i=0;
            if(items.isEmpty()) {
                rows=new String[1][1];
                columns=new String[]{"Empty"};
                rows[0][0] = "The menu is empty!";
            }
            else {
                rows=new String[items.size()][2];
                columns=new String[]{"Product name","Price"};
                for (MenuItem x:items)
                {
                    rows[i][0]=x.getName();
                    rows[i][1]=x.computePrice()+"";
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
    class UpdateMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            RestaurantSerializator r2=new RestaurantSerializator();
            try {
                r2.serialization(MainClass.getFileName(),model.getItems(),model.getHmap());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
