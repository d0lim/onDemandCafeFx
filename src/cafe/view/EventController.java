package cafe.view;

import cafe.MainApp;
import cafe.model.Ingredient;
import cafe.model.IngredientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.shape.Box;
import java.net.*;
import java.util.*;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.scene.control.cell.*;
import javafx.util.*;


public class EventController {

	
	class Wrapper {
		private Ingredient i;
		//private Coffee c;

		private StringProperty name;
		
		public StringProperty getNameProperty() {
			// TODO Auto-generated method stub
			return name;
		}
	}
	private IngredientHandler ingredienthandler;
	
	@FXML
	private TableView<Ingredient> ingredientTable;
	
	@FXML
	private TableColumn<Ingredient,String> ingredientname;
	

	@FXML
	private TableView<Ingredient>  selectedTable;
	
	@FXML
	private TableColumn<Ingredient,String> selectedname;
	
	@FXML
	private TableColumn<Ingredient,Integer >selectedprice; 
	
	@FXML
	private TableColumn<Ingredient,Integer> changedprice;
	
	@FXML
	private TableColumn<Ingredient,Boolean> ing_check;
	
	@FXML
	private TextField percentage;

	
	
	//커피 TableView TalbleColumn 추가하기
	Ingredient selected_i=new Ingredient();
	ObservableList<Ingredient> selected_is=FXCollections.observableArrayList();
	ObservableList<ObservableList<String>> temp_name=FXCollections.observableArrayList();
	ObservableList<String> temp_price=FXCollections.observableArrayList();
	ObservableList<String> temp_saleprice=FXCollections.observableArrayList();
	//ObservableList<Ingredient> canceling_i=FXCollections.observableArrayList();
	
	Ingredient temp_ing=new Ingredient();
	Ingredient canceling_i=new Ingredient();
	
	
	@FXML
	private void initialize() {
		/*ingredientname.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		
		ingredientTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ingredientTable.getSelectionModel().setCellSelectionEnabled(true);
		ingredientTable.setItems(IngredientHandler.getIngredients());
		

		selected_i = ingredientTable.getSelectionModel().getSelectedItems();*/
		//selectedTable.setRowFactory(cellData->selectedItems);
		/*ingredientTable.getFocusModel().focusedCellProperty().addListener((obs, oldVal, newVal) -> {

		    if(newVal.getTableColumn() != null){
		        ingredientTable.getSelectionModel().selectRange(0, newVal.getTableColumn(), ingredientTable.getItems().size(), newVal.getTableColumn());
		        System.out.println("Selected TableColumn: "+ newVal.getTableColumn().getText());
		       // System.out.println("Selected column index: "+ newVal.getColumn());
		        
		        selected_i.getSelectionModel().getSelectedItems();
		        selectedTable.setItems((ObservableList<cafe.view.EventController.Wrapper>) newVal.getTableColumn());
		    }
		});*/
		//TableColumn<Ingredient,Boolean>  checkCol = new TableColumn<>("Check");
		/*ing_check.setCellFactory(
		        CheckBoxTableCell.forTableColumn(ing_check)
			    );
		ing_check.setCellValueFactory(
			            new PropertyValueFactory<>("selected")
			    );
		
			    ingredientname.setCellValueFactory(
			            new PropertyValueFactory<>("name")
			    );*/
			   /* ing_check.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
			    ing_check.setCellFactory(param -> new CheckBoxTableCell<Ingredient, Boolean>());
			    ingredientname.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
    */
		/*ColumnSelectRow columSelect = new ColumnSelectRow(); 
		ingredientTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ing_check.getColumns().add(columSelect);
		ingredientname.setCellValueFactory(cellData->cellData.getValue().getNameProperty());
		ingredientTable.getColumns().add(ingredientname);*/
		/*ingredientTable.setRowFactory(new Callback<TableView<Box>, TableRow<Box>>() {
		    @Override
		    public TableRow<Box> call(TableView<Box> tableView2)
		    {
		        final TableRow<Box> row = new TableRow<>();

		        row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(MouseEvent event)
		            {
		                final int index = row.getIndex();

		                if (index >= 0 && index < boxTable.getItems().size())
		                {
		                    if(boxTable.getSelectionModel().isSelected(index))
		                        boxTable.getSelectionModel().clearSelection(index);
		                    else
		                        boxTable.getSelectionModel().select(index);

		                    event.consume();
		                }
		            }
		        });
		        return row;
		    }
		});
		*/
		ingredientTable.setItems(IngredientHandler.getIngredients());
		ingredientname.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		ingredientTable.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
			selected_i=newValue;
		});
		selectedTable.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
			canceling_i=newValue;
		});
		//selectedTable.setId(selected_is);
		selectedTable.setItems(selected_is);
		selectedname.setCellValueFactory(cellData->cellData.getValue().getNameProperty());
		selectedprice.setCellValueFactory(cellData->cellData.getValue().getSaleProperty().asObject());
		changedprice.setCellValueFactory(cellData->cellData.getValue().getPriceProperty().asObject());
		
	}
	
	private void itableinit() {
		
		ingredientname.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		// showIngredientDetails(null);
		ingredientTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ingredientTable.getSelectionModel().setCellSelectionEnabled(true);
		ingredientTable.setItems(IngredientHandler.getIngredients());
		ingredientTable.getFocusModel().focusedCellProperty().addListener((obs, oldVal, newVal) -> {

		    if(newVal.getTableColumn() != null){
		        ingredientTable.getSelectionModel().selectRange(0, newVal.getTableColumn(), ingredientTable.getItems().size(), newVal.getTableColumn());
		        System.out.println("Selected TableColumn: "+ newVal.getTableColumn().getText());
		        System.out.println("Selected column index: "+ newVal.getColumn());
		        
		       // selectedTable.setItems((ObservableList<cafe.view.EventController.Wrapper>) newVal.getTableColumn());
		    }
		});
		
		

	}
	
	@FXML
	private void handle_sale() {
		int changedprice=0;
		//for(int i=0;i<selected_i.size();i++) {
			//temp_ing=selected_i.get(i); //percentage에 정수가 안들어오는 경우 체크해줘야됨
			if(selected_i!=null) {
			//temp_ing=selected_i;
			changedprice=selected_i.getPrice()  * Integer.parseInt(percentage.getText())/ 100;
			System.out.println(changedprice);
			selected_i.setSalePrice((int)changedprice);//여기오류
			//temp_ing.setSalePrice(changedprice);
		//}
			selected_is.add(selected_i);
			System.out.println(selected_is);
			tosale();
			
		}
	}
	private void tosale() {
		selected_i.swap_price();
	}
	private void offsale() {
		canceling_i.swap_price();
	}
	
	@FXML
	private void cancel_sale() {
		//목록에있는거 갖고와서 가격원래대로
		
		//for(int i=0;i<canceling_i.size();i++) {
			//temp_ing=canceling_i.get(i);
			//temp_ing=canceling_i;
			//temp_ing.setPrice(temp_ing.getPrice());
			//temp_ing.setSalePrice(0);
		//}
			//canceling_i.swap_price();
			//canceling_i.setPrice(0);
			System.out.println(canceling_i.getPrice()+" "+canceling_i.getSalePrice());
			offsale();
			System.out.println(canceling_i.getPrice()+" "+canceling_i.getSalePrice());
			selected_is.remove(canceling_i);
		
	}
	
	
}
