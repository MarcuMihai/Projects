import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Polinom {
    static int nr=0;
    ArrayList <Monom> polynomial = new ArrayList <Monom>(nr);
    public Polinom() {
        super();
    }
    public void addMonom(Monom m){
        int ok=0;
        for (Monom n:polynomial)
            if (n.getGrad() == m.getGrad()) {
                n.setCoef(m.getCoef()+n.getCoef());
                ok++;
            } else {
                nr++;
            }
        if(ok==0)
            polynomial.add(m);
    }

    public int getNr() {
        return nr;
    }

    public void toPolynomial(String s){
        String s1=s.replace("-","+-");
        s1=" "+s1;
        s1=s1.replaceFirst(" \\+","");
        s1=s1.replaceFirst(" ","");
        String ms[]=s1.split("\\+");
        for (String a:ms) {
            Monom mon=new Monom(0,0);
            mon.toMonom(a);
            polynomial.add(mon);
        }
    }

    public String toString(){
        String s=new String();
        Collections.sort(polynomial);
        for (Monom m:polynomial) {
            if(m.getCoef()==-0.0 || m.getCoef()==0.0)
                s+="0+";
            else s+=m.toString()+"+";
        }
        s=s.replace("+-","-");
        s=s+" ";
        s=s.replace("+ ","");
        return s;
   }

   public Polinom delete0(){
        Polinom p=new Polinom();
       for (Monom m:polynomial) {
           if(m.getCoef()!=0)
               p.polynomial.add(m);
       }
       return p;
   }

}
