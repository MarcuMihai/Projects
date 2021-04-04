package BusinessLayer;

public abstract class MenuItem implements java.io.Serializable{
    String name;
    public MenuItem(String name){
        this.name=name;
    }
    public abstract double computePrice();
    public String getName() {
        return name;
    }
}
