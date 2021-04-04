package controller;

import view.AccountView;
import view.ClientView;
import view.EmployeeView;

import javax.swing.*;

public class EmployeeController {
    private EmployeeView view;

    public EmployeeController(EmployeeView view) {
        this.view=view;
    }

    public void clientListener( JFrame frame) {
        ClientView v = new ClientView(view.getU());
        frame.setVisible(false);
        frame.dispose();
    }
    public void accountListener( JFrame frame) {
        AccountView v = new AccountView(view.getU());
        frame.setVisible(false);
        frame.dispose();
    }
}
