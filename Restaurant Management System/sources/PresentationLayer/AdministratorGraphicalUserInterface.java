package PresentationLayer;

import BusinessLayer.AdministratorOperations;
import BusinessLayer.Restaurant;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AdministratorGraphicalUserInterface extends JFrame{
    private JTextField userInputName = new JTextField(20);
    private JTextField userInputName2 = new JTextField(20);
    private JTextField userInputListOfComponents = new JTextField(30);
    private JTextField userInputPrice = new JTextField(8);
    private JButton btn1 = new JButton("Add item into menu");
    private JButton btn2 = new JButton("Edit item from menu");
    private JButton btn3 = new JButton("Delete item from menu");
    private JButton btn4 = new JButton("Close");
    private JButton btn5 = new JButton("Base product");
    private JButton btn6 = new JButton("Composite product");
    private JButton btn7 = new JButton("Show menu");
    private JButton btn8 = new JButton("Update menu");
    AdministratorOperations model;
    AdministratorGUIController ctrl;
    private String type;
    public AdministratorGraphicalUserInterface(Restaurant model) throws IOException, ClassNotFoundException {
        this.model=model;
        ctrl=new AdministratorGUIController(model,this);
        JPanel panel1=new JPanel();
        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();
        JPanel panel4=new JPanel();
        JPanel panel5=new JPanel();
        JPanel panel6=new JPanel();
        JPanel panel7=new JPanel();
        JPanel panel8=new JPanel();
        JPanel mainpanel=new JPanel();
        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new GridLayout(1,3,3,3));
        panel3.setLayout(new FlowLayout());
        panel4.setLayout(new FlowLayout());
        panel5.setLayout(new FlowLayout());
        panel6.setLayout(new FlowLayout());
        panel7.setLayout(new FlowLayout());
        panel8.setLayout(new FlowLayout());
        mainpanel.setLayout(new GridLayout(9,1,3,3));
        panel1.add(new JLabel("Choose the operation you want to execute:"));
        mainpanel.add(panel1);
        panel2.add(btn1);
        panel2.add(btn2);
        panel2.add(btn3);
        panel3.add(new JLabel("Introduce the name of the product:"));
        panel6.add(new JLabel("Introduce the new name of the product(for edit command):"));
        panel4.add(new JLabel("Introduce the price of the product(if it is a base product):"));
        panel5.add(new JLabel("Introduce the components of the product(if it is a composite product):"));
        panel3.add(userInputName);
        panel6.add(userInputName2);
        panel4.add(userInputPrice);
        panel5.add(userInputListOfComponents);
        panel7.add(new JLabel("Select the type of the product(for the create and edit operations):"));
        panel7.add(btn5);
        panel7.add(btn6);
        panel8.add(btn8);
        panel8.add(btn7);
        mainpanel.add(panel2);
        mainpanel.add(panel8);
        mainpanel.add(panel7);
        mainpanel.add(panel3);
        mainpanel.add(panel6);
        mainpanel.add(panel4);
        mainpanel.add(panel5);
        mainpanel.add(btn4);
        this.setContentPane(mainpanel);
        this.pack();
        this.setTitle("Administrator Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    String getInputName(){
        return userInputName.getText();
    }
    String getInputNewName(){
        return userInputName2.getText();
    }
    String getInputPrice(){
        return userInputPrice.getText();
    }
    String getInputComponents(){
        return userInputListOfComponents.getText();
    }
    void addCreateItemListener(ActionListener l) {
        btn1.addActionListener(l);
    }
    void addEditItemListener(ActionListener l) {
        btn2.addActionListener(l);
    }
    void addDeleteItemListener(ActionListener l) {
        btn3.addActionListener(l);
    }
    void addExitBtnListener(ActionListener l) {
        btn4.addActionListener(l);
    }
    void addBaseProductListener(ActionListener l) {
        btn5.addActionListener(l);
    }
    void addCompositeProductListener(ActionListener l) {
        btn6.addActionListener(l);
    }
    void addShowMenuListener(ActionListener l) {
        btn7.addActionListener(l);
    }
    void addUpdateMenuListener(ActionListener l) {
        btn8.addActionListener(l);
    }
    public void setTip(String t){
        type=t;
    }
    public String getTip() {
        return type;
    }
}
