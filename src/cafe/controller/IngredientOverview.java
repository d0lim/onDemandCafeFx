package cafe.controller;

import cafe.MainApp;
import cafe.model.Ingredient;
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
    private TableColumn<Ingredient, String> ingredientname;
    @FXML
    private Label name;
    @FXML
    private Label price;


    @FXML
    private void goback() {
        MainApp.start_program();
    }

    @FXML
    private void initialize() {

        ingredientname.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

        showIngredientDetails(null);

        ingredientTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showIngredientDetails(newValue));

        ingredientTable.setItems(IngredientHandler.getIngredientObservableList());
    }


    private void showIngredientDetails(Ingredient i) {

        if (i != null) {
            name.setText(i.getName());
            price.setText(Integer.toString(i.getPrice()));
        } else {
            name.setText("");
            price.setText("");
        }
    }

    @FXML
    private void handleDeleteIngredient() {
        int selectedIndex = ingredientTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            ingredientTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(AlertType.WARNING); // 주석 UTF8로 다시 적어주세용
            //alert.initOwner(mainApp.getPrimaryStage());//왜 오류?
            alert.setTitle("No selection");
            alert.setHeaderText("No Ingredients Selected");
            alert.setContentText("Please select an ingredient");

            alert.showAndWait();
        }
    }


    @FXML
    private void handleNewIngredient() {
        IngredientFactory ingredientFactory = new IngredientFactory();

        Ingredient temp = ingredientFactory.createIngredient();

        boolean okClicked = MainApp.showIngredientEditDialog(temp);
        if (okClicked) {
            IngredientHandler.getIngredientObservableList().add(temp);
        }


    }

    @FXML
    private void handleEditIngredient() {
        Ingredient selected = ingredientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {

            boolean okClicked = MainApp.showIngredientEditDialog(selected);

            if (okClicked) {
                showIngredientDetails(selected);
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            //alert.initOwner(mainApp.getPrimaryStage());//왜오류떠?
            alert.setTitle("No Selection");
            alert.setHeaderText("No Ingredient Selected");
            alert.setContentText("Please select an ingredient");

            alert.showAndWait();

        }
    }

}

