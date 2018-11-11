package cafe.view;

import cafe.model.Ingredient;
import cafe.model.IngredientHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EventController {

	
	class Wrapper{
		private Ingredient i;
		//private Coffee c;
		
	}
	private IngredientHandler ingredienthandler;
	@FXML
	private Label price;
	
	@FXML
	private TableView<Ingredient> ingredientTable;
	
	@FXML
	private TableColumn<Ingredient,String> ingredientname;
	
	@FXML
	private TableView<Wrapper> selectedTable;
	
	@FXML
	private TableColumn<Wrapper, String> selectedname;
	
	@FXML
	private TableColumn<Wrapper, String> selectedprice; 
	
	@FXML
	private TableColumn<Wrapper,String> changedprice;
	
	@FXML
	private Button onsale;
	@FXML
	private Button cancelsale;
	
	//커피 TableView TalbleColumn 추가하기
	
	
	
	@FXML
	private void initialize() {
	       
	        ingredientname.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
	        
	        showIngredientDetails(null);
	        
	        ingredientTable.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->showIngredientDetails(newValue));
	        
	        ingredientTable.setItems(IngredientHandler.getIngredients());
	        
	        //커피도하기
	}
	private void itableinit() {
		ingredientTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ingredientTable.getSelectionModel().setCellSelectionEnabled(true);
		ingredientTable.getFocusModel().focusedCellProperty().addListener((obs, oldVal, newVal) -> {

		    if(newVal.getTableColumn() != null){
		        ingredientTable.getSelectionModel().selectRange(0, newVal.getTableColumn(), ingredientTable.getItems().size(), newVal.getTableColumn());
		        System.out.println("Selected TableColumn: "+ newVal.getTableColumn().getText());
		        System.out.println("Selected column index: "+ newVal.getColumn());
		        
		        
		    }
		});

	}
	private void showIngredientDetails(Ingredient i) {
		
		if(i!=null) {
			//name.setText(i.getName());
			price.setText(Integer.toString(i.getPrice()));
		}
		else {
			//name.setText("");
			price.setText("");
		}
	}
	
}
