package PresentationLayer;

import BusinessLayer.Restaurant;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainGraphicalUserInterface extends JFrame{
    private JButton btn1 = new JButton("Administrator");
    private JButton btn2 = new JButton("Waiter");
    private JButton btn3 = new JButton("Chef");
    private JButton btn4 = new JButton("Exit");
    Restaurant model;
    {
        try {
            model = new Restaurant();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    MainGUIController ctrl=new MainGUIController(this,model);
    public MainGraphicalUserInterface(){
        JPanel panel1=new JPanel();
        JPanel panel2=new JPanel();
        JPanel mainpanel=new JPanel();
        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new GridLayout(1,3,3,3));
        mainpanel.setLayout(new GridLayout(3,1,3,3));
        panel1.add(new JLabel("Select your role:"));
        mainpanel.add(panel1);
        panel2.add(btn1);
        panel2.add(btn2);
        panel2.add(btn3);
        mainpanel.add(panel2);
        mainpanel.add(btn4);
        this.setContentPane(mainpanel);
        this.pack();
        this.setTitle("Main GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    void addAdministratorListener(ActionListener l) {
        btn1.addActionListener(l);
    }
    void addWaiterListener(ActionListener l) {
        btn2.addActionListener(l);
    }
    void addChefListener(ActionListener l) {
        btn3.addActionListener(l);
    }
    void addExitBtnListener(ActionListener l) {
        btn4.addActionListener(l);
    }
}
