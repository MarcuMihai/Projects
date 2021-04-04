package controller;

import com.itextpdf.text.DocumentException;
import dataAccess.OperationDAO;
import dataAccess.UserDAO;
import dataModel.Operation;
import dataModel.User;
import presentation.Presentation;
import view.AdminView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AdminController {
    private AdminView view;

    public AdminController(AdminView view) {
        this.view=view;
    }

    public ArrayList<User> getUsersList() {
        return UserDAO.allUsers();
    }

    public void addListener(String username, String password, String type) {
        if (username.isEmpty()) {
            ControllerUtils.showError("Empty username field");
        }
        else if (password.isEmpty()) {
            ControllerUtils.showError("Empty password field");
        }
        else if (type.isEmpty() || !(type.equals("employee") || type.equals("admin"))) {
            ControllerUtils.showError("Wrong type field");
        }
        else {
            User u= new User(type,username,password);
            UserDAO.insert(u);
        }
        int opId;
        if(!OperationDAO.findAll().isEmpty()) {
            opId = OperationDAO.findAll().get(OperationDAO.findAll().size() - 1).getId() + 1;
        }
        else opId=0;
        Operation o=new Operation(opId,"user","add user",view.getU().getUsername(),username);
        OperationDAO.insert(o);
    }
    public void deleteListener(String username) {
        User u= UserDAO.findByUsername(username);
        UserDAO.delete(u);
        int opId;
        if(!OperationDAO.findAll().isEmpty()) {
            opId = OperationDAO.findAll().get(OperationDAO.findAll().size() - 1).getId() + 1;
        }
        else opId=0;
        Operation o=new Operation(opId,"user","delete user",view.getU().getUsername(),username);
        OperationDAO.insert(o);
    }
    public void updateListener(String username, String newUsername, String newPassword, String newType) {
        User u= UserDAO.findByUsername(username);
        if(!newUsername.isEmpty())
            u.setUsername(newUsername);
        if(!newPassword.isEmpty())
            u.setPassword(newPassword);
        if(!newType.isEmpty()) {
            if (!(newType.equals("employee") || newType.equals("admin")))
                ControllerUtils.showError("Wrong type field");
            else u.setType(newType);
        }
        UserDAO.update(u,username);
        int opId;
        if(!OperationDAO.findAll().isEmpty()) {
            opId = OperationDAO.findAll().get(OperationDAO.findAll().size() - 1).getId() + 1;
        }
        else opId=0;
        Operation o=new Operation(opId,"user","update user",view.getU().getUsername(),username);
        OperationDAO.insert(o);
    }
    public void reportListener(String username) throws FileNotFoundException, DocumentException {
        ArrayList<Operation> ol=OperationDAO.findAll();
        Presentation p=new Presentation();
        for (Operation o:ol
             ) {
            if(o.getUsername().equals(username))
                p.addRowsClient(o.getId(),o.getType(),o.getName(),o.getUsername(),o.getInputData());
        }
        p.docPdf();
    }
}
