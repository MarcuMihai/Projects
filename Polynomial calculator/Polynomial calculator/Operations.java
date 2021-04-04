import java.util.Collections;

public class Operations {

    public static Polinom addition(Polinom a, Polinom b) {
        Polinom result = new Polinom();
        for (Monom m : a.polynomial) {
            Monom m1 = new Monom(m.getGrad(), m.getCoef());
            result.addMonom(m1);
        }
        for (Monom n : b.polynomial) {
            result.addMonom(n);
        }
        return result;
    }

    public static Polinom subtraction(Polinom a, Polinom b) {
        Polinom result = new Polinom();
        for (Monom m : a.polynomial) {
            Monom m1 = new Monom(m.getGrad(), m.getCoef());
            result.addMonom(m1);
        }
        for (Monom n : b.polynomial) {
            Monom n1 = new Monom(n.getGrad(), -n.getCoef());
            result.addMonom(n1);
        }
        return result;
    }

    public static Polinom multiplication(Polinom a, Polinom b) {
        Polinom result = new Polinom();
        for (Monom m : a.polynomial) {
            for (Monom n : b.polynomial){
            Monom m1 = new Monom(m.getGrad()+n.getGrad(), m.getCoef()*n.getCoef());
            result.addMonom(m1);
         }
        }
        return result;
    }

    public static String division(Polinom a, Polinom b) {
        Polinom deimp = a;
        Polinom result = new Polinom();
        Collections.sort(deimp.polynomial);
        Collections.sort(b.polynomial);
        if (b.toString().equals("0") && !a.toString().equals("0"))
            return "Inf";
        else if (b.toString().equals("0") && a.toString().equals("0"))
            return "Undefined!";
        else{
            if (deimp.polynomial.get(0).getGrad() >= b.polynomial.get(0).getGrad()) {
                while (!deimp.toString().equals(" ")) {
                    Monom m = new Monom(deimp.polynomial.get(0).getGrad() - b.polynomial.get(0).getGrad(), deimp.polynomial.get(0).getCoef() / b.polynomial.get(0).getCoef());
                    result.polynomial.add(m);
                    Polinom p = new Polinom();
                    p.polynomial.add(m);
                    deimp = subtraction(deimp, multiplication(b, p));
                    deimp = deimp.delete0();
                }
                return result + " " + deimp;
            } else return "0 " + a;
        }
    }

    public static Polinom derivative(Polinom a) {
        Polinom result = new Polinom();
        for (Monom m : a.polynomial) {
            Monom m1 = new Monom(m.getGrad()-1, m.getCoef() * m.getGrad());
            result.addMonom(m1);
        }
        return result;
    }

    public static Polinom integration(Polinom a) {
        Polinom result = new Polinom();
        for (Monom m : a.polynomial) {
            Monom m1 = new Monom(m.getGrad()+1, m.getCoef() / (m.getGrad()+1));
            result.addMonom(m1);
        }
        return result;
    }
}


