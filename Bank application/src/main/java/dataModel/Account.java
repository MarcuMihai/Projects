package dataModel;

import java.sql.Date;

public class Account {
    private String type, number;
    private int id, idClient;
    private java.sql.Date creationDate;
    private double moneyAmount;
    public Account(int id, int idClient, String number, String type, double money, java.sql.Date data){
        this.id=id;
        this.idClient=idClient;
        this.number=number;
        this.type=type;
        moneyAmount=money;
        this.creationDate=data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
