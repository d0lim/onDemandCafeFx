package cafe.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class OwnerTabController {

	@FXML
	private AnchorPane IngredientAnchorPane;
	@FXML
	private AnchorPane EventAnchorPane;
	@FXML
	private AnchorPane CoffeeAnchorPane;
	@FXML
	private Tab CoffeeTab;
	//Coffee Controller page
	@FXML
	private Tab EventTab;
	@FXML
	private Tab IngredientTab;
	
	
	//private Event Controller page event;
	IngredientOverview a=new IngredientOverview();
	@FXML
	private void initialize() {
		
	}
	
	
}
