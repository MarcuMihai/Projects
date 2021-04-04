package controller;

import javax.swing.*;

public class ControllerUtils {
    public static void showError(String msg){
        JOptionPane.showMessageDialog(null,
                msg,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
