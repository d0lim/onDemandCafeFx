package cafe.view;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import cafe.model.Ingredient;

public class IngredientEditController {

	@FXML
	private TextField name;
	@FXML
	private TextField price;
	
	@FXML
	private void initialize() {
		name.setText("");
		price.setText("");
	}
	
	private Stage dialogStage;
	private Ingredient ingredient;
	private boolean okClicked = false;
	
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage; // 주석 UTF8로 다시 적어주세용
    }
	
	
	


	public void setIngredient(Ingredient i) {
		this.ingredient=i;
		
		if(ingredient.getName()==null) {
			name.setText("");
		}
		else {
		name.setText(ingredient.getName());
		}
		if(ingredient.getPrice()=='\0') {
			price.setText(Integer.toString(0));
		}
		else {
		price.setText(Integer.toString(ingredient.getPrice()));
		}
	}
	public boolean isOkClicked() {
		return okClicked;
	}
	@FXML
	private void handleOk() {
		if(isInputValid()) {
			ingredient.setName(name.getText());
			ingredient.setPrice(Integer.parseInt(price.getText()));
			
			okClicked=true;
			dialogStage.close();
		}
	}
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean isInputValid() {
		String errorMessage="";
		if(name.getText()==null||name.getText().length()==0) {
			errorMessage+="Not valid name";
		}
		if(price.getText()==null||price.getText().length()==0) {
			errorMessage+="Not valid price";
		}
		else {
			try {
				Integer.parseInt(price.getText());
			}
			catch(NumberFormatException e){
				errorMessage +="Must be integer!!";
			}
		}
		if(errorMessage.length()==0) {
			return true;
		}
		else {
			Alert alert=new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			
			alert.showAndWait();
			return false;
		}
		
	}
}
