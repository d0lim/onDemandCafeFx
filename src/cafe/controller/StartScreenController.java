package cafe.controller;


import cafe.MainApp;
import javafx.fxml.FXML;



public class StartScreenController {


	private MainApp mainapp;

    @FXML
    public void initialize() {
    	
    }
	
	public void setScreen(MainApp mainapp) {
		this.mainapp=mainapp;
		System.out.println("했다");
	}
	
	@FXML
	public void handlecustomer() {
		System.out.println("clicked customer");
		MainApp.showCustomerPage();
	}
	
	
	@FXML
	public void handleowner() {
		System.out.println("clicked owner");
		MainApp.showOwnerTabs();
	}
	
	
	
}