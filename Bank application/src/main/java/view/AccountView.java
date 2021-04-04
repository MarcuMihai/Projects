package view;

import controller.AccountController;
import dataModel.Account;
import dataModel.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AccountView {
    private User u;
    private AccountController accountCtrl;
    public JFrame frame;
    private JTextField idClient= new JTextField(10);
    private JTextField type = new JTextField(10);
    private JTextField number= new JTextField(10);
    private JTextField moneyAmount = new JTextField(10);
    private JTextField accountTo = new JTextField(10);
    private JTextField accountFrom = new JTextField(10);
    private JTextField moneyTransferSum = new JTextField(10);
    public JButton btnExit = new JButton("Exit");
    public JButton btnBack = new JButton("Back");
    public JButton btnUpdate = new JButton("Update account");
    public JButton btnAdd = new JButton("Add account");
    public JButton btnDelete = new JButton("Delete account");
    public JButton btnTransfer = new JButton("Realise a transfer");
    public JButton btnConfirmTransfer = new JButton("Confirm transfer");
    public JButton btnElectricity = new JButton("Pay electricity");
    public JButton btnInternet = new JButton("Pay internet");
    public JButton btnGas= new JButton("Pay gas");
    public JButton btnSelectA1 = new JButton("Set sender account");
    public JButton btnSelectA2 = new JButton("Set receiver account");
    public JLabel lblIdClient= new JLabel("Client ID");
    public JLabel lblNumber = new JLabel("Identification number");
    public JLabel lblType = new JLabel("Type");
    public JLabel lblMoneyAmount = new JLabel("Money amount");
    public JLabel lblData = new JLabel("Enter the new data:");
    public JLabel lblAccountTo = new JLabel("Receiver account");
    public JLabel lblAccountFrom = new JLabel("Sender account");
    public JLabel lblMoneyTransferSum = new JLabel("Money amount");
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    JTable table = new JTable();
    public DefaultTableModel model = new DefaultTableModel();
    public Object[] row = new Object[6];
    public int selectedId;

    public AccountView(User u) {
        this.u=u;
        this.accountCtrl= new AccountController(this);
        frame = new JFrame("Account management");
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {

        JPanel panel = new JPanel(new GridLayout(3,2));
        JPanel panel3 = new JPanel(new FlowLayout());
        JPanel panel4 = new JPanel(new GridLayout(5,1));
        JPanel panel5 = new JPanel(new FlowLayout());
        JPanel panel6 = new JPanel(new FlowLayout());
        JPanel panel7 = new JPanel(new FlowLayout());
        JPanel panel8 = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panel9 = new JPanel(new FlowLayout());
        JPanel panel10 = new JPanel(new GridLayout(8,1));
        JPanel panel12 = new JPanel(new FlowLayout());
        JPanel panel11 = new JPanel(new FlowLayout());
        JPanel panel13 = new JPanel(new FlowLayout());
        JPanel panel14 = new JPanel(new FlowLayout());
        JPanel panel15 = new JPanel(new FlowLayout());
        JPanel panel16 = new JPanel(new FlowLayout());

        Object[] columns = {"ID","Client ID","Identification number","Type","Money amount","Creation date"};
        model.setColumnIdentifiers(columns);
        table.setModel(model);

        table.setBackground(Color.CYAN);
        table.setForeground(Color.black);
        Font font = new Font("",1,20);
        table.setFont(font);
        table.setRowHeight(30);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 1000, 200);

        if(accountCtrl.getAccountsList()!=null) {
            ArrayList<Account> accountsList = accountCtrl.getAccountsList();
            for (Account a : accountsList) {
                row[0] = a.getId();
                row[1] = a.getIdClient();
                row[2] = a.getNumber();
                row[3] = a.getType();
                row[4] = a.getMoneyAmount();
                row[5] = a.getCreationDate();
                model.addRow(row);
            }
        }

        accountTo.setEditable(false);
        accountFrom.setEditable(false);

        panel1.add(lblData);
        panel3.add(btnAdd);
        panel3.add(btnUpdate);
        panel3.add(btnDelete);
        panel6.add(lblIdClient);
        panel6.add(idClient);
        panel7.add(lblNumber);
        panel7.add(number);
        panel8.add(lblType);
        panel8.add(type);
        panel2.add(lblMoneyAmount);
        panel2.add(moneyAmount);
        panel4.add(panel1);
        panel4.add(panel6);
        panel4.add(panel7);
        panel4.add(panel8);
        panel4.add(panel2);
        panel5.add(btnBack);
        panel5.add(btnExit);
        panel9.add(btnTransfer);
        panel11.add(lblAccountFrom);
        panel11.add(accountFrom);
        panel12.add(lblAccountTo);
        panel12.add(accountTo);
        panel13.add(lblMoneyTransferSum);
        panel13.add(moneyTransferSum);
        panel14.add(btnConfirmTransfer);
        panel15.add(btnElectricity);
        panel15.add(btnInternet);
        panel15.add(btnGas);
        panel16.add(btnSelectA1);
        panel16.add(btnSelectA2);
        panel10.add(panel16);
        panel10.add(panel11);
        panel10.add(panel12);
        panel10.add(panel13);
        panel10.add(panel14);
        panel10.add(panel15);
        panel.add(pane);
        panel.add(panel3);
        panel.add(panel9);
        panel.add(panel4);
        panel.add(panel10);
        panel.add(panel5);
        frame.add(panel);
        panel10.setVisible(false);

        frame.setSize(1300,800);
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
            accountCtrl.addListener(idClient.getText(),number.getText(),type.getText(),moneyAmount.getText());
            idClient.setText("");
            number.setText("");
            type.setText("");
            moneyAmount.setText("");
            updateTable();
        });

        btnDelete.addActionListener(e->{
            accountCtrl.deleteListener(selectedId);
            idClient.setText("");
            number.setText("");
            type.setText("");
            moneyAmount.setText("");
            updateTable();
        });

        btnUpdate.addActionListener(e->{
            accountCtrl.updateListener(selectedId,number.getText(),type.getText(),moneyAmount.getText());
            idClient.setText("");
            number.setText("");
            type.setText("");
            moneyAmount.setText("");
            updateTable();
        });

        btnConfirmTransfer.addActionListener(e->{
            accountCtrl.transferListener(accountFrom.getText(),accountTo.getText(),moneyTransferSum.getText());
            accountFrom.setText("");
            accountTo.setText("");
            moneyTransferSum.setText("");
            updateTable();
            panel10.setVisible(false);
        });

        btnTransfer.addActionListener(e->{
            panel10.setVisible(true);
        });

        btnElectricity.addActionListener( e -> {
            accountTo.setText("0");
        });

        btnInternet.addActionListener( e -> {
            accountTo.setText("1");
        });

        btnGas.addActionListener( e -> {
            accountTo.setText("2");
        });

        btnSelectA1.addActionListener( e -> {
            accountFrom.setText(selectedId+"");
        });

        btnSelectA2.addActionListener( e -> {
            accountTo.setText(selectedId+"");
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
        if(accountCtrl.getAccountsList()!=null) {
            ArrayList<Account> accountsList = accountCtrl.getAccountsList();
            for (Account a : accountsList) {
                row[0] = a.getId();
                row[1] = a.getIdClient();
                row[2] = a.getNumber();
                row[3] = a.getType();
                row[4] = a.getMoneyAmount();
                row[5] = a.getCreationDate();
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
