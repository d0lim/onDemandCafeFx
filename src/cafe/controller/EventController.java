package cafe.controller;

import cafe.MainApp;
import cafe.model.Coffee;
import cafe.model.CoffeeHandler;
import cafe.model.Ingredient;
import cafe.model.IngredientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.util.Iterator;


public class EventController {

	@FXML
	private TableView<Ingredient> ingredientTable;
	
	@FXML
	private TableColumn<Ingredient,String> ingredientname;
	@FXML
	private TableColumn<Ingredient,Integer> ingredientprice;
	@FXML
    private TableView<Coffee> coffeeTable;
	@FXML
    private TableColumn<Coffee, String> coffeeName;
	@FXML
    private TableColumn<Coffee, Integer> coffeeOriginalPrice;

	@FXML
	private TableView<Ingredient>  selectedIngTable;
	@FXML
	private TableView<Coffee> selectedCofTable;
	@FXML
	private TableColumn<Coffee,String> selectedcofname;
	@FXML
	private TableColumn<Coffee,Integer> changedcofprice;
	@FXML
	private TableColumn<Ingredient,String> selectedingname;
	
	@FXML
	private TableColumn<Ingredient,Integer >selectedingprice; 
	
	@FXML
	private TableColumn<Ingredient,Integer> changedingprice;
	
	@FXML
	private TextField percentage_ing;

	@FXML
	private TextField percentage_cof;
	
	
	//커피 TableView TalbleColumn 추가하기
	Ingredient selected_i=new Ingredient();
	ObservableList<Ingredient> selected_is=FXCollections.observableArrayList();
	ObservableList<Coffee> selected_cs=FXCollections.observableArrayList();
	Coffee selected_c=new Coffee();
	Coffee canceling_c=new Coffee();
	Ingredient canceling_i=new Ingredient();

	CoffeeHandler coffeeHandler;
	
	
	@FXML
	private void initialize() {
		manage_ingredient_events();
		manage_coffee_events();
	}
	
	private void manage_ingredient_events() {
		ingredientTable.setItems(IngredientHandler.getIngredientObservableList());
		ingredientname.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		ingredientprice.setCellValueFactory(cellData->cellData.getValue().getPriceProperty().asObject());
		ingredientTable.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
			selected_i=newValue;
		});
		selectedIngTable.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
			canceling_i=newValue;
		});
		
		selectedIngTable.setItems(selected_is);
		selectedingname.setCellValueFactory(cellData->cellData.getValue().getNameProperty());
		changedingprice.setCellValueFactory(cellData->cellData.getValue().getPriceProperty().asObject());
		
	}
	private void manage_coffee_events() {
		coffeeName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		coffeeOriginalPrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
		setCoffeeList();
		
		coffeeTable.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
			selected_c=newValue;
		});
		selectedCofTable.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
			canceling_c=newValue;
		});
		
		selectedCofTable.setItems(selected_cs);
		selectedcofname.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		changedcofprice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
		
	}
	
	private void alert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
        //alert.initOwner(dialogStage);
        //alert.setTitle("Invalid");
        //alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(message);
        alert.showAndWait();
	}
	@FXML
	private void  handle_ingredient_sale() {
		int changedprice=0;
			//percentage에 정수가 안들어오는 경우 체크해줘야됨
			if(check_ingsale()) {
			
				if(selected_i!=null) {
					
					changedprice=selected_i.getPrice()-(selected_i.getPrice()  * Integer.parseInt(percentage_ing.getText())/ 100);
					
					selected_i.setSalePrice((int)changedprice);//여기오류
					selected_is.add(selected_i);
					
					ing_tosale();
					
					refresh_coffees();
			}
		}
	}
	
	private boolean check_ingsale() {
		Iterator<Ingredient> it=selected_is.iterator();
		while(it.hasNext()) {
			Ingredient temp=it.next();
			if(selected_i.equals(temp)) {
				alert("already on sale");
				return false;
			}
		}
		return true;
	}
	private void refresh_coffees(){
		ObservableList<Coffee> coffeeList = this.coffeeHandler.getCoffees();
		Iterator<Coffee> it = coffeeList.iterator();

		while (it.hasNext()) {
		    Coffee current = it.next();
		    current.calculatePrice();
        }
		coffeeTable.refresh();
	}
	@FXML
	private void handle_coffee_sale() {
		int changedprice=0;
		//percentage에 정수가 안들어오는 경우 체크해줘야됨
		if(check_cofsale()) {
			
			if(selected_c!=null) {
				
				changedprice=selected_c.getPrice()-(selected_c.getPrice()  * Integer.parseInt(percentage_cof.getText())/ 100);
				
				selected_c.setSalePrice((int)changedprice);//여기오류
				selected_cs.add(selected_c);
				
				cof_tosale();
				
				//refresh_coffees();
			}
		}
	}
	private boolean check_cofsale() {
		Iterator<Coffee> it=selected_cs.iterator();
		while(it.hasNext()) {
			Coffee temp=it.next();
			if(selected_c.equals(temp)) {
				alert("already on sale");
				return false;
			}
		}
		return true;
	}
	

	private void setCoffeeList() {
	    this.coffeeHandler = new CoffeeHandler();
	    this.coffeeTable.setItems(coffeeHandler.getCoffees());
    }

	private void ing_tosale() {
		selected_i.swap_price();
	}
	private void ing_offsale() {
		canceling_i.swap_price();
	}
	private void cof_tosale() {
		selected_c.swap_price();
	}
	private void cof_offsale() {
		canceling_c.swap_price();
	}
	
	@FXML
	private void cancel_ing_sale() {
			ing_offsale();
			selected_is.remove(canceling_i);
	}
	@FXML
	private void cancel_cof_sale() {
			cof_offsale();
			selected_cs.remove(canceling_c);
	}
	@FXML
	private void goback() {
		MainApp.start_program();
	}
	
}
