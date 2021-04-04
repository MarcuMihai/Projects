package PresentationLayer;

import BusinessLayer.Restaurant;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainGUIController {
    private MainGraphicalUserInterface view;
    private Restaurant model;
    public MainGUIController(MainGraphicalUserInterface view,Restaurant model) {
        this.model=model;
        this.view=view;
        view.addAdministratorListener(new AdministratorListener());
        view.addWaiterListener(new WaiterListener());
        view.addChefListener(new ChefListener());
        view.addExitBtnListener(new ExitBtnListener());
    }
    class AdministratorListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                AdministratorGraphicalUserInterface view=new AdministratorGraphicalUserInterface(model);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    class WaiterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                WaiterGraphicalUserInterface view=new WaiterGraphicalUserInterface(model);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    class ChefListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           ChefGraphicalUserInterface view=new ChefGraphicalUserInterface(model);
        }
    }
    class ExitBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}