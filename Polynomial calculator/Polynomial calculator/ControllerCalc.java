import java.awt.event.*;

public class ControllerCalc {

    private Operations model_proj;
    private ViewCalc view_proj;

    ControllerCalc(Operations model, ViewCalc view) {
        model_proj = model;
        view_proj = view;
        view.addAdditionListener(new AdditionListener());
        view.addSubtractionListener(new SubtractionListener());
        view.addMultiplicationListener(new MultiplicationListener());
        view.addDivisionListener(new DivisionListener());
        view.addDerivativeListener(new DerivativeListener());
        view.addIntegrationListener(new IntegrationListener());
        view.addClearListener(new ClearListener());
    }

    class AdditionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String p1 = "";
            String p2 = "";
            try {
                p1 = view_proj.getFirstPolynomial();
                p2 = view_proj.getSecondPolynomial();
                Polinom pol1 = new Polinom();
                Polinom pol2 = new Polinom();
                Polinom result = new Polinom();
                pol1.toPolynomial(p1);
                pol2.toPolynomial(p2);
                result = model_proj.addition(pol1, pol2);
                view_proj.setResult1(result.toString());
                view_proj.setResult2("");
            } catch (NumberFormatException er) {
                view_proj.showError("Incorrect data input!");
            }
        }
    }
    class SubtractionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String p1 = "";
            String p2 = "";
            try {
                p1 = view_proj.getFirstPolynomial();
                p2 = view_proj.getSecondPolynomial();
                Polinom pol1 = new Polinom();
                Polinom pol2 = new Polinom();
                Polinom result = new Polinom();
                pol1.toPolynomial(p1);
                pol2.toPolynomial(p2);
                result = model_proj.subtraction(pol1, pol2);
                System.out.println(result.toString());
                view_proj.setResult1(result.toString());
                view_proj.setResult2("");
            } catch (NumberFormatException er) {
                view_proj.showError("Incorrect data input!");
            }
        }
    }
    class MultiplicationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String p1 = "";
            String p2 = "";
            try {
                p1 = view_proj.getFirstPolynomial();
                p2 = view_proj.getSecondPolynomial();
                Polinom pol1 = new Polinom();
                Polinom pol2 = new Polinom();
                Polinom result = new Polinom();
                pol1.toPolynomial(p1);
                pol2.toPolynomial(p2);
                result = model_proj.multiplication(pol1, pol2);
                view_proj.setResult1(result.toString());
                view_proj.setResult2("");
            } catch (NumberFormatException er) {
                view_proj.showError("Incorrect data input!");
            }
        }
    }
    class DivisionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String p1 = "";
            String p2 = "";
            try {
                p1 = view_proj.getFirstPolynomial();
                p2 = view_proj.getSecondPolynomial();
                Polinom pol1 = new Polinom();
                Polinom pol2 = new Polinom();
                pol1.toPolynomial(p1);
                pol2.toPolynomial(p2);
                String rez=new String();
                rez = model_proj.division(pol1, pol2);
                String result[]=rez.split(" ");
                String r0=new String();
                if(result.length==1)
                    r0="0";
                else r0=result[1];
                view_proj.setResult1(result[0]);
                view_proj.setResult2(r0);
            } catch (NumberFormatException er) {
                view_proj.showError("Incorrect data input!");
            }
        }
    }
    class DerivativeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String p1 = "";
            String p2 = "";
            try {
                p1 = view_proj.getFirstPolynomial();
                p2 = view_proj.getSecondPolynomial();
                Polinom pol1 = new Polinom();
                Polinom pol2 = new Polinom();
                Polinom result1 = new Polinom();
                Polinom result2 = new Polinom();
                pol1.toPolynomial(p1);
                pol2.toPolynomial(p2);
                result1 = model_proj.derivative(pol1);
                result2 = model_proj.derivative(pol2);
                view_proj.setResult1(result1.toString());
                view_proj.setResult2(result2.toString());

            } catch (NumberFormatException er) {
                view_proj.showError("Incorrect data input!");
            }
        }
    }
    class IntegrationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String p1 = "";
            String p2 = "";
            try {
                p1 = view_proj.getFirstPolynomial();
                p2 = view_proj.getSecondPolynomial();
                Polinom pol1 = new Polinom();
                Polinom pol2 = new Polinom();
                Polinom result1 = new Polinom();
                Polinom result2 = new Polinom();
                pol1.toPolynomial(p1);
                pol2.toPolynomial(p2);
                result1 = model_proj.integration(pol1);
                result2 = model_proj.integration(pol2);
                view_proj.setResult1(result1.toString()+"+C");
                view_proj.setResult2(result2.toString()+"+C");
            } catch (NumberFormatException er) {
                view_proj.showError("Incorrect data input!");
            }
        }
    }

        class ClearListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                view_proj.setResult1("");
                view_proj.setResult2("");
                view_proj.setInput1("");
                view_proj.setInput2("");
            }
        }
    }