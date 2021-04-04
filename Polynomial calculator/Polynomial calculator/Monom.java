import java.text.DecimalFormat;
import java.util.Comparator;

public class Monom implements Comparable{
    private int grad;
    private double coef;
    public Monom(int grad,double coef) {
        this.grad=grad;
        this.coef=coef;
    }
    public Integer getGrad() {
        return grad;
    }

    public void setGrad(int grad) {
        this.grad = grad;
    }

    public void setCoef(double coef) {
        this.coef = coef;
    }

    public double getCoef() {
        return coef;
    }

        public int compareTo(Object o) {
            if(this.getGrad()>((Monom)o).getGrad())
                    return -1;
            else return 1;
        }

    public void toMonom(String s){
        String s1=" "+s+" ";
        s1=s1.replace("*x","x");
        s1=s1.replace("x ","x^1");
        s1=s1.replace(" x","1x");
        s1=s1.replace("-x","-1x");
        s1=s1.replace(" ","");
        String[] m=s1.split("^");
        for (String a : m) {
            a = a.replace("^", "");
            String[]  b = a.split("x");
            coef = Double.parseDouble(b[0]);
            if(b.length>1)
                grad = Integer.parseInt(b[1]);
            else grad=0;
        }
    }

    public String toString(){
        String s=new String();
        int auxgrad;
        if(grad==0 || coef==0)
                s=coef+"";
            else if(grad==1)
                s=coef+"*x";
            else s=coef+"*x^"+grad;
            return s;
    }
}
