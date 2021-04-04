package controller;

import dataAccess.AccountDAO;
import dataAccess.ClientDAO;
import dataAccess.OperationDAO;
import dataAccess.TransferDAO;
import dataModel.Account;
import dataModel.Client;
import dataModel.Operation;
import dataModel.Transfer;
import view.AccountView;
import java.sql.Date;
import java.util.ArrayList;

public class AccountController {
    private AccountView view;

    public AccountController(AccountView view) {
        this.view=view;
    }

    public ArrayList<Account> getAccountsList() {
        return AccountDAO.allAccounts();
    }

    public boolean verifyDigit(String s){
        try {
            Double.parseDouble(s);
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
    public void addListener(String idClient,String number, String type, String moneyAmount) {
        if (idClient.isEmpty() || !verifyDigit(idClient)) {
            ControllerUtils.showError("Wrong client ID field");
        }
        else if (number.length()!=6 || !verifyDigit(number)) {
            ControllerUtils.showError("Wrong identification card number field");
        }
        else if (type.isEmpty() || !(type.equals("credit") || type.equals("debit") || type.equals("utilities"))) {
            ControllerUtils.showError("Wrong type field");
        }
        else if (moneyAmount.isEmpty()) {
            ControllerUtils.showError("Empty money amount field");
        }
        else {
            boolean verif=false;
            for (Client c: ClientDAO.allClients()
                 ) {
                    if(c.getIdc()==Integer.parseInt(idClient)){
                        verif=true;
                    }
            }
            if(!verif)
                ControllerUtils.showError("Client with id: "+ idClient+" doesn't exist!");
            else{
                Date data=new Date(System.currentTimeMillis());
                int id=0;
                if(!getAccountsList().isEmpty()){
                    id= getAccountsList().get(getAccountsList().size()-1).getId()+1;
                }
                Account a= new Account(id,Integer.parseInt(idClient),number,type,Double.parseDouble(moneyAmount), data);
                AccountDAO.insert(a);
                int opId;
                if(!OperationDAO.findAll().isEmpty()) {
                    opId = OperationDAO.findAll().get(OperationDAO.findAll().size() - 1).getId() + 1;
                }
                else opId=0;
                Operation o=new Operation(opId,"account","create account",view.getU().getUsername(),id+"");
                OperationDAO.insert(o);
            }
        }
    }
    public void deleteListener(int id) {
        Account a=AccountDAO.findById(id);
        AccountDAO.delete(a);
        int opId;
        if(!OperationDAO.findAll().isEmpty()) {
            opId = OperationDAO.findAll().get(OperationDAO.findAll().size() - 1).getId() + 1;
        }
        else opId=0;
        Operation o=new Operation(opId,"account","delete account",view.getU().getUsername(),id+"");
        OperationDAO.insert(o);
    }
    public void updateListener(int id,String newNumber, String newType, String newMoneyAmount) {
        Account a=AccountDAO.findById(id);
        if(!newNumber.isEmpty() && verifyDigit(newNumber) && !a.getNumber().equals(newNumber)) {
            a.setNumber(newNumber);
        }
        if(!newType.isEmpty() && (newType.equals("credit") || newType.equals("debit") || newType.equals("utilities"))) {
            a.setType(newType);
        }
        if (!newMoneyAmount.isEmpty() && verifyDigit(newMoneyAmount) && a.getMoneyAmount()!=Double.parseDouble(newMoneyAmount)) {
            a.setMoneyAmount(Double.parseDouble(newMoneyAmount));
        }
        AccountDAO.update(a);
        int opId;
        if(!OperationDAO.findAll().isEmpty()) {
            opId = OperationDAO.findAll().get(OperationDAO.findAll().size() - 1).getId() + 1;
        }
        else opId=0;
        Operation o=new Operation(opId,"account","update account",view.getU().getUsername(),id+"");
        OperationDAO.insert(o);
    }
    public void transferListener(String accountFrom, String accountTo, String moneySum) {
        Account a1=AccountDAO.findById(Integer.parseInt(accountFrom));
        Account a2=AccountDAO.findById(Integer.parseInt(accountTo));
        a1.setMoneyAmount(a1.getMoneyAmount()-Double.parseDouble(moneySum));
        a2.setMoneyAmount(a2.getMoneyAmount()+Double.parseDouble(moneySum));
        AccountDAO.update(a1);
        AccountDAO.update(a2);
        int id=0;
        if(!TransferDAO.allTransfers().isEmpty()) {
            id = TransferDAO.allTransfers().get(TransferDAO.allTransfers().size() - 1).getId() + 1;
        }
        Transfer t=new Transfer(id,Integer.parseInt(accountFrom),Integer.parseInt(accountTo),Double.parseDouble(moneySum));
        TransferDAO.insert(t);
        int opId;
        if(!OperationDAO.findAll().isEmpty()) {
            opId = OperationDAO.findAll().get(OperationDAO.findAll().size() - 1).getId() + 1;
        }
        else opId=0;
        Operation o=new Operation(opId,"transfer","create transfer",view.getU().getUsername(),moneySum+" from id: "+accountFrom+" to id: "+accountTo);
        OperationDAO.insert(o);
    }
}
