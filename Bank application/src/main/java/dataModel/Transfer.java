package dataModel;

public class Transfer {
    private int id;
    private int accountFrom, accountTo;
    private double moneySum;
    public Transfer(int id,int account1, int account2, double money){
        this.id=id;
        this.accountFrom=account1;
        this.accountTo=account2;
        this.moneySum=money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public double getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(double moneySum) {
        this.moneySum = moneySum;
    }
}
