package controller;

import dataAccess.UserDAO;
import dataModel.User;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;

public class LoginController {
    private LoginView view;

    public LoginController(LoginView view) {
        this.view=view;
    }

    public void loginListener(String username, String password, JFrame frame) {
        if (username.isEmpty()) {
            ControllerUtils.showError("Empty username field");
        }
        else if (password.isEmpty()) {
            ControllerUtils.showError("Empty password field");
        }
        else {
            User u = UserDAO.findByUsername(username);
            if (u != null) {
                if (u.getPassword().equals(password)) {
                    if (u.getType().equals("admin")) {
                        AdminView v = new AdminView(u);
                        frame.setVisible(false);
                        frame.dispose();
                    } else if (u.getType().equals("employee")) {
                        EmployeeView v = new EmployeeView(u);
                        frame.setVisible(false);
                        frame.dispose();
                    }
                }
                else ControllerUtils.showError("Wrong password for user "+username);
            }
            else ControllerUtils.showError("Wrong username: "+username);
        }
    }
}
