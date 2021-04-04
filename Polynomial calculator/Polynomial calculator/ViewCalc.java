import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class ViewCalc extends JFrame {
    private JTextField userInputTf1 = new JTextField(20);
    private JTextField userInputTf2 = new JTextField(20);
    private JTextField resultTf1 = new JTextField(30);
    private JTextField resultTf2 = new JTextField(30);
    private JButton    btn1 = new JButton("Addition");
    private JButton    btn2 = new JButton("Subtraction");
    private JButton    btn3 = new JButton("Multiplication");
    private JButton    btn4 = new JButton("Division");
    private JButton    btn5 = new JButton("Derivate");
    private JButton    btn6 = new JButton("Integrate");
    private JButton    clearBtn    = new JButton("Clear");

    private Operations model_proj;

    ViewCalc(Operations model) {
        model_proj = model;

        resultTf1.setEditable(false);
        resultTf2.setEditable(false);

        JPanel panel1=new JPanel();
        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();
        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new FlowLayout());
        panel3.setLayout(new FlowLayout());
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout(3,3));
        panel3.add(new JLabel("1st Polynomial:"));
        panel3.add(userInputTf1);
        panel3.add(new JLabel("2nd Polynomial:"));
        panel3.add(userInputTf2);
        panel1.add(btn1);
        panel1.add(btn2);
        panel1.add(btn3);
        panel1.add(btn4);
        panel1.add(btn5);
        panel1.add(btn6);
        panel2.add(new JLabel("Result:"));
        panel2.add(resultTf1);
        panel2.add(new JLabel("Result2:"));
        panel2.add(resultTf2);
        panel3.add(clearBtn);
        content.add(panel1,BorderLayout.CENTER);
        content.add(panel2,BorderLayout.SOUTH);
        content.add(panel3,BorderLayout.NORTH);

        this.setContentPane(content);
        this.pack();
        this.setTitle("Polynomial Calculator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    String getFirstPolynomial() {
        return userInputTf1.getText();
    }
    String getSecondPolynomial() {
        return userInputTf2.getText();
    }

    void setResult1(String newTotal) {
        resultTf1.setText(newTotal);
    }
    void setResult2(String newTotal) {
        resultTf2.setText(newTotal);
    }
    void setInput1(String newTotal) {
        userInputTf1.setText(newTotal);
    }
    void setInput2(String newTotal) {
        userInputTf2.setText(newTotal);
    }

    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

    void addAdditionListener(ActionListener mal) {
        btn1.addActionListener(mal);
    }
    void addSubtractionListener(ActionListener mal) {
        btn2.addActionListener(mal);
    }
    void addMultiplicationListener(ActionListener mal) {
        btn3.addActionListener(mal);
    }
    void addDivisionListener(ActionListener mal) {
        btn4.addActionListener(mal);
    }
    void addDerivativeListener(ActionListener mal) {
        btn5.addActionListener(mal);
    }
    void addIntegrationListener(ActionListener mal) {
        btn6.addActionListener(mal);
    }
    void addClearListener(ActionListener cal) {
        clearBtn.addActionListener(cal);
    }
}