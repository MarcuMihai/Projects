import java.text.DecimalFormat;

public class MainClass extends Operations{
    public static void main(String[] args) {
        Operations model = new Operations();
        ViewCalc view = new ViewCalc(model);
        ControllerCalc controller = new ControllerCalc(model, view);
        view.setVisible(true);
    }
}

