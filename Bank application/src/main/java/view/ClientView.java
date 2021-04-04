package view;
import controller.ClientController;
import dataModel.Client;
import dataModel.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ClientView {
    private User u;
    private ClientController clientCtrl;
    public JFrame frame;
    private JTextField name= new JTextField(10);
    private JTextField idCardNr = new JTextField(10);
    private JTextField cnp= new JTextField(10);
    private JTextField adress = new JTextField(10);
    public JButton btnExit = new JButton("Exit");
    public JButton btnBack = new JButton("Back");
    public JButton btnUpdate = new JButton("Update client");
    public JButton btnAdd = new JButton("Add client");
    public JButton btnDelete = new JButton("Delete client");
    public JLabel lblName = new JLabel("Name");
    public JLabel lblIdCardNr = new JLabel("ID card number");
    public JLabel lblCnp = new JLabel("CNP");
    public JLabel lblAdress = new JLabel("Adress");
    public JLabel lblData = new JLabel("Enter the new data:");
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    JTable table = new JTable();
    public DefaultTableModel model = new DefaultTableModel();
    public Object[] row = new Object[5];
    public int selectedId;

    public ClientView(User u) {
        this.u=u;
        this.clientCtrl= new ClientController(this);
        frame = new JFrame("Client management");
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {

        JPanel panel = new JPanel(new GridLayout(4,1));
        JPanel panel3 = new JPanel(new FlowLayout());
        JPanel panel4 = new JPanel(new GridLayout(5,1));
        JPanel panel5 = new JPanel(new FlowLayout());
        JPanel panel6 = new JPanel(new FlowLayout());
        JPanel panel7 = new JPanel(new FlowLayout());
        JPanel panel8 = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        JPanel panel1 = new JPanel(new FlowLayout());


        Object[] columns = {"ID","Name","ID card number","CNP","Adress"};
        model.setColumnIdentifiers(columns);
        table.setModel(model);

        table.setBackground(Color.CYAN);
        table.setForeground(Color.black);
        Font font = new Font("",1,20);
        table.setFont(font);
        table.setRowHeight(30);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 1000, 200);

        if(clientCtrl.getClientsList()!=null) {
            ArrayList<Client> clientsList = clientCtrl.getClientsList();
            for (Client c : clientsList) {
                row[0] = c.getIdc();
                row[1] = c.getName();
                row[2] = c.getIdCardNr();
                row[3] = c.getCnp();
                row[4] = c.getAdress();
                model.addRow(row);
            }
        }

        panel1.add(lblData);
        panel3.add(btnAdd);
        panel3.add(btnUpdate);
        panel3.add(btnDelete);
        panel6.add(lblName);
        panel6.add(name);
        panel7.add(lblIdCardNr);
        panel7.add(idCardNr);
        panel8.add(lblCnp);
        panel8.add(cnp);
        panel2.add(lblAdress);
        panel2.add(adress);
        panel4.add(panel1);
        panel4.add(panel6);
        panel4.add(panel7);
        panel4.add(panel8);
        panel4.add(panel2);
        panel5.add(btnBack);
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
                selectedId = Integer.parseInt(model.getValueAt(r, 0).toString());
            }
        });

        btnAdd.addActionListener(e->{
            clientCtrl.addListener(name.getText(),idCardNr.getText(),cnp.getText(),adress.getText());
            name.setText("");
            idCardNr.setText("");
            cnp.setText("");
            adress.setText("");
            updateTable();
        });

        btnDelete.addActionListener(e->{
            clientCtrl.deleteListener(selectedId);
            name.setText("");
            idCardNr.setText("");
            cnp.setText("");
            adress.setText("");
            updateTable();
        });

        btnUpdate.addActionListener(e->{
            clientCtrl.updateListener(selectedId,name.getText(),idCardNr.getText(),cnp.getText(),adress.getText());
            name.setText("");
            idCardNr.setText("");
            cnp.setText("");
            adress.setText("");
            updateTable();
        });

        btnBack.addActionListener( e -> {
            EmployeeView v=new EmployeeView(u);
            frame.setVisible(false);
            frame.dispose();
        });

        btnExit.addActionListener( e -> {
            frame.setVisible(false);
            frame.dispose();
        });

    }
    private void updateTable() {
        model.setRowCount(0);
        if(clientCtrl.getClientsList()!=null) {
            ArrayList<Client> clientsList = clientCtrl.getClientsList();
            for (Client c : clientsList) {
                row[0] = c.getIdc();
                row[1] = c.getName();
                row[2] = c.getIdCardNr();
                row[3] = c.getCnp();
                row[4] = c.getAdress();
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
