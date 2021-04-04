package PresentationLayer;

import BusinessLayer.Restaurant;
import BusinessLayer.WaiterOperations;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class WaiterGraphicalUserInterface extends JFrame{
    private JTextField userInputName = new JTextField(20);
    private JTextField userInputTableNr = new JTextField(3);
    private JTextField userInputOrderId = new JTextField(3);
    private JTextField userOutputPrice = new JTextField(5);
    private JButton btn1 = new JButton("Create new order");
    private JButton btn2 = new JButton("Compute price of order");
    private JButton btn3 = new JButton("Generate order bill");
    private JButton btn4 = new JButton("Close");
    private JButton btn5 = new JButton("Show orders");
    private JButton btn6 = new JButton("Update orders table");
    WaiterOperations model;
    WaiterGUIController ctrl;
    public WaiterGraphicalUserInterface(Restaurant model) throws IOException, ClassNotFoundException {
        this.model=model;
        ctrl=new WaiterGUIController(model,this);
        JPanel panel1=new JPanel();
        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();
        JPanel panel4=new JPanel();
        JPanel panel5=new JPanel();
        JPanel panel6=new JPanel();
        JPanel mainpanel=new JPanel();
        panel1.setLayout(new FlowLayout());
        panel3.setLayout(new FlowLayout());
        panel4.setLayout(new FlowLayout());
        panel5.setLayout(new FlowLayout());
        panel6.setLayout(new FlowLayout());
        panel2.setLayout(new GridLayout(1,3,3,3));
        mainpanel.setLayout(new GridLayout(9,1,3,3));
        panel1.add(new JLabel("Choose the operation you want to execute:"));
        mainpanel.add(panel1);
        panel2.add(btn1);
        panel2.add(btn2);
        panel2.add(btn3);
        panel3.add(new JLabel("Introduce the names of the products you want to add to the order:"));
        panel3.add(userInputName);
        panel4.add(new JLabel("Introduce the number of the table which requested the order:"));
        panel4.add(userInputTableNr);
        panel5.add(new JLabel("Introduce the id of the order you want to compute the price or create bill:"));
        panel5.add(userInputOrderId);
        panel6.add(new JLabel("The final price of the order is:"));
        panel6.add(userOutputPrice);
        mainpanel.add(panel2);
        mainpanel.add(btn6);
        mainpanel.add(btn5);
        mainpanel.add(panel4);
        mainpanel.add(panel3);
        mainpanel.add(panel5);
        mainpanel.add(panel6);
        mainpanel.add(btn4);
        this.setContentPane(mainpanel);
        this.pack();
        this.setTitle("Waiter Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    void addCreateOrderListener(ActionListener l) {
        btn1.addActionListener(l);
    }
    void addComputePriceListener(ActionListener l) {
        btn2.addActionListener(l);
    }
    void addGenerateBillListener(ActionListener l) {
        btn3.addActionListener(l);
    }
    void addExitBtnListener(ActionListener l) {
        btn4.addActionListener(l);
    }
    void addShowOrdersListener(ActionListener l) {
        btn5.addActionListener(l);
    }
    String getInputName(){
        return userInputName.getText();
    }
    String getInputTableNr(){
        return userInputTableNr.getText();
    }
    String getInputOrderId(){
        return userInputOrderId.getText();
    }
    void setOutputPrice(String s){
        userOutputPrice.setText(s);
    }
    void addUpdateOrdersTableListener(ActionListener l) {
        btn6.addActionListener(l);
    }
}
