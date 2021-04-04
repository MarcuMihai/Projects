package BusinessLayer;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem{
    double price=0;
    ArrayList<MenuItem> components;
    public CompositeProduct(String n,ArrayList<MenuItem> comp){
        super(n);
        components=comp;
    }
    public double computePrice(){
        price=0;
        for (MenuItem x:components) {
            price+=x.computePrice();
        }
        return price;
    }
    public ArrayList<MenuItem> getComponents() {
        return components;
    }
}
