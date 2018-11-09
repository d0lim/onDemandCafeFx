package ondemandcafe.view;

import ondemandcafe.MainApp;
import ondemandcafe.model.Ingredient;
import ondemandcafe.model.IngredientHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class IngredientOverview {

	@FXML
	private TableView<Ingredient> ingredientTable;
	@FXML
	private TableColumn<Ingredient,String> ingredientname;
	@FXML
	private Label name;
	@FXML
	private Label price;
	
	private IngredientHandler o;
	private MainApp mainApp;
	
	@FXML
	private void initialize() {
	       
	        ingredientname.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
	        
	        showIngredientDetails(null);
	        
	        ingredientTable.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->showIngredientDetails(newValue));
	        
	    }

	public void setMainApp(IngredientHandler ih) {
	        this.o = ih;
		// 주석 UTF8로 다시 적어주세용
	        ingredientTable.setItems(o.getIngredients());
	}
	
	private void showIngredientDetails(Ingredient i) {
	
		if(i!=null) {
			name.setText(i.getName());
			price.setText(Integer.toString(i.getPrice()));
		}
		else {
			name.setText("");
			price.setText("");
		}
	}
	@FXML
	private void handleDeleteIngredient() {
		int selectedIndex=ingredientTable.getSelectionModel().getSelectedIndex();
		
		if(selectedIndex >=0) {
			ingredientTable.getItems().remove(selectedIndex);
		}
		else {
			Alert alert=new Alert(AlertType.WARNING); // 주석 UTF8로 다시 적어주세용
			//alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No Ingredients Selected");
			alert.setContentText("Please select an ingredient");
			
			alert.showAndWait();
		}
	}
	
	
	@FXML
	private void handleNewIngredient() {
		
		Ingredient temp=new Ingredient();
		
		boolean okClicked =mainApp.showIngredientEditDialog(temp);
		if(okClicked) {
			o.getIngredients().add(temp);
		}
		
		
	}
	
	@FXML
	private void handleEditIngredient() {
		Ingredient selected = ingredientTable.getSelectionModel().getSelectedItem();
		if(selected!=null) {
			
			boolean okClicked=mainApp.showIngredientEditDialog(selected);
			
			if(okClicked) {
				showIngredientDetails(selected);
			}
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Ingredient Selected");
	        alert.setContentText("Please select an ingredient");

	        alert.showAndWait();

		}
	}
	
}

