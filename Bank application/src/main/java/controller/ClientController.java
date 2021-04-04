package controller;

import dataAccess.AccountDAO;
import dataAccess.ClientDAO;
import dataAccess.OperationDAO;
import dataModel.Account;
import dataModel.Client;
import dataModel.Operation;
import view.ClientView;

import java.util.ArrayList;

public class ClientController {
    private ClientView view;

    public ClientController(ClientView view) {
        this.view=view;
    }

    public ArrayList<Client> getClientsList() {
        return ClientDAO.allClients();
    }

    public boolean verifyDigit(String s){
        try {
            Long.parseLong(s);
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
    public void addListener(String name, String idCardNr, String cnp, String adress) {
        if (name.isEmpty()) {
            ControllerUtils.showError("Empty name field");
        }
        else if (idCardNr.length()!=6 || !verifyDigit(idCardNr)) {
            ControllerUtils.showError("Wrong id card number field");
        }
        else if (cnp.length() != 13 || !verifyDigit(cnp)) {
            ControllerUtils.showError("Wrong cnp field");
        }
        else if (adress.isEmpty()) {
            ControllerUtils.showError("Empty adress field");
        }
        else {
            int id=0;
            if(!getClientsList().isEmpty()) {
                id = getClientsList().get(getClientsList().size() - 1).getIdc() + 1;
            }
            Client c= new Client(id,name,idCardNr,cnp,adress);
            ClientDAO.insert(c);
            int opId;
            if(!OperationDAO.findAll().isEmpty()) {
                opId = OperationDAO.findAll().get(OperationDAO.findAll().size() - 1).getId() + 1;
            }
            else opId=0;
            Operation o=new Operation(opId,"client","add client",view.getU().getUsername(),id+"");
            OperationDAO.insert(o);
        }
    }
    public void deleteListener(int id) {
        Client c=ClientDAO.findById(id);
        ClientDAO.delete(c);
        int opId;
        if(!OperationDAO.findAll().isEmpty()) {
            opId = OperationDAO.findAll().get(OperationDAO.findAll().size() - 1).getId() + 1;
        }
        else opId=0;
        Operation o=new Operation(opId,"client","delete client",view.getU().getUsername(),id+"");
        OperationDAO.insert(o);
        for (Account a:AccountDAO.findByClientId(c.getIdc())
             ) {
            AccountDAO.delete(a);
            int opId1;
            if(!OperationDAO.findAll().isEmpty()) {
                opId1 = OperationDAO.findAll().get(OperationDAO.findAll().size() - 1).getId() + 1;
            }
            else opId1=0;
            Operation o1=new Operation(opId1,"account","delete account",view.getU().getUsername(),id+"");
            OperationDAO.insert(o1);
        }
    }
    public void updateListener(int id, String newName, String newIdCardNr, String newCnp, String newAdress) {
        Client c=ClientDAO.findById(id);
        if (!newName.isEmpty() && !c.getName().equals(newName)) {
            c.setName(newName);
        }
        if(!newIdCardNr.isEmpty()) {
            if (newIdCardNr.length() != 6 || !verifyDigit(newIdCardNr)) {
                ControllerUtils.showError("Wrong id card number field");
            }
            else if(!c.getIdCardNr().equals(newIdCardNr)){
                c.setIdCardNr(newIdCardNr);
            }
        }
        if(!newCnp.isEmpty()) {
            if (newCnp.length() != 13 || !verifyDigit(newCnp)) {
                ControllerUtils.showError("Wrong cnp field");
            }
            else if(!c.getCnp().equals(newCnp)){
                c.setCnp(newCnp);
            }
        }
        if (!newAdress.isEmpty() && !c.getAdress().equals(newAdress)) {
            c.setAdress(newAdress);
        }
        ClientDAO.update(c);
        int opId;
        if(!OperationDAO.findAll().isEmpty()) {
            opId = OperationDAO.findAll().get(OperationDAO.findAll().size() - 1).getId() + 1;
        }
        else opId=0;
        Operation o=new Operation(opId,"client","delete client",view.getU().getUsername(),id+"");
        OperationDAO.insert(o);
    }

}
