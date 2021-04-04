package BusinessLayer;

public class BaseProduct extends MenuItem{
    private double price;
    public BaseProduct(String n,double p){
        super(n);
        price=p;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double computePrice(){
        return price;
    }
}
