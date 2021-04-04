package view;

import controller.EmployeeController;
import dataModel.User;

import javax.swing.*;
import java.awt.*;

public class EmployeeView {
    private EmployeeController employeeCtrl;
    private User u;
    public JFrame frame;
    public JButton btnExit = new JButton("Exit");
    public JButton btnClient = new JButton("Client management");
    public JButton btnAccount = new JButton("Account management");
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    public EmployeeView(User u) {
        this.u=u;
        this.employeeCtrl= new EmployeeController(this);
        frame = new JFrame("Employee tab");
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        JPanel panel = new JPanel(new GridLayout(2,1));
        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        panel1.add(btnClient);
        panel1.add(btnAccount);
        panel.add(panel1);
        panel2.add(btnExit);
        panel.add(panel2);
        frame.add(panel);

        frame.setSize(250,200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        btnClient.addActionListener(e->{
            employeeCtrl.clientListener(frame);
        });

        btnAccount.addActionListener(e->{
            employeeCtrl.accountListener(frame);
        });

        btnExit.addActionListener( e -> {
            frame.setVisible(false);
            frame.dispose();
        });

    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }
}
