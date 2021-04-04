package view;

import com.itextpdf.text.DocumentException;
import controller.AdminController;
import dataModel.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AdminView {
    private User u;
    private AdminController adminCtrl;
    public JFrame frame;
    private JTextField username= new JTextField(10);
    private JTextField password = new JTextField(10);
    private JTextField type = new JTextField(10);
    public JButton btnExit = new JButton("Exit");
    public JButton btnUpdate = new JButton("Update user");
    public JButton btnAdd = new JButton("Add user");
    public JButton btnDelete = new JButton("Delete user");
    public JButton btnReport = new JButton("Generate report");
    public JLabel lblUsername = new JLabel("Username");
    public JLabel lblPassword = new JLabel("Password");
    public JLabel lblType = new JLabel("Type");
    public JLabel lblData = new JLabel("Enter the new credentials:");
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    JTable table = new JTable();
    public DefaultTableModel model = new DefaultTableModel();
    public Object[] row = new Object[3];
    public String selectedUsername,selectedPassword,selectedType;

    public AdminView(User u) {
        this.u=u;
        this.adminCtrl= new AdminController(this);
        frame = new JFrame("Admin tab");
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {

        JPanel panel = new JPanel(new GridLayout(4,1));
        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panel3 = new JPanel(new FlowLayout());
        JPanel panel4 = new JPanel(new GridLayout(5,1));
        JPanel panel5 = new JPanel(new FlowLayout());
        JPanel panel6 = new JPanel(new FlowLayout());
        JPanel panel7 = new JPanel(new FlowLayout());
        JPanel panel8 = new JPanel(new FlowLayout());


        Object[] columns = {"Username","Password","Type"};
        model.setColumnIdentifiers(columns);
        table.setModel(model);

        table.setBackground(Color.CYAN);
        table.setForeground(Color.black);
        Font font = new Font("",1,20);
        table.setFont(font);
        table.setRowHeight(30);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 1000, 200);

        if(adminCtrl.getUsersList()!=null) {
            ArrayList<User> usersList = adminCtrl.getUsersList();
            for (User u : usersList) {
                row[0] = u.getUsername();
                row[1] = u.getPassword();
                row[2] = u.getType();
                model.addRow(row);
            }
        }
        panel1.add(lblData);
        panel3.add(btnAdd);
        panel3.add(btnUpdate);
        panel3.add(btnDelete);
        panel3.add(btnReport);
        panel6.add(lblUsername);
        panel6.add(username);
        panel7.add(lblPassword);
        panel7.add(password);
        panel8.add(lblType);
        panel8.add(type);
        panel4.add(panel1);
        panel4.add(panel6);
        panel4.add(panel7);
        panel4.add(panel8);
        panel5.add(btnExit);
        panel.add(pane);
        panel.add(panel3);
        panel.add(panel4);
        panel.add(panel5);
        frame.add(panel);

        frame.setSize(700,650);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                selectedUsername = model.getValueAt(r, 0).toString();
                selectedPassword = model.getValueAt(r, 1).toString();
                selectedType = model.getValueAt(r, 2).toString();
            }
        });

        btnAdd.addActionListener(e->{
            adminCtrl.addListener(username.getText(),password.getText(),type.getText());
            username.setText("");
            password.setText("");
            type.setText("");
            updateTable();
        });

        btnDelete.addActionListener(e->{
            adminCtrl.deleteListener(selectedUsername);
            username.setText("");
            password.setText("");
            type.setText("");
            updateTable();
        });

        btnUpdate.addActionListener(e->{
            adminCtrl.updateListener(selectedUsername,username.getText(),password.getText(),type.getText());
            username.setText("");
            password.setText("");
            type.setText("");
            updateTable();
        });

        btnReport.addActionListener(e->{
            try {
                adminCtrl.reportListener(selectedUsername);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (DocumentException documentException) {
                documentException.printStackTrace();
            }
        });

        btnExit.addActionListener( e -> {
            frame.setVisible(false);
            frame.dispose();
        });

    }
    private void updateTable() {
        model.setRowCount(0);
        if(adminCtrl.getUsersList()!=null) {
            ArrayList<User> usersList = adminCtrl.getUsersList();
            for (User u : usersList) {
                row[0] = u.getUsername();
                row[1] = u.getPassword();
                row[2] = u.getType();
                model.addRow(row);
            }
        }
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }
}
