package cafe.controller;


import cafe.MainApp;
import javafx.fxml.FXML;


public class StartScreenController {


    @FXML
    public void initialize() {

    }


    @FXML
    public void handlecustomer() {
        MainApp.showCustomerPage();
    }


    @FXML
    public void handleowner() {
        MainApp.showOwnerTabs();
    }


}