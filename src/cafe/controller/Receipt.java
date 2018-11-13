package cafe.controller;


import cafe.model.Coffee;
import cafe.model.Ingredient;
import cafe.model.IngredientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Iterator;

public class Receipt {

    @FXML
    private TableView<Coffee> receiptTable;
    @FXML
    private TableColumn<Coffee, String> receiptCoffeeName;
    @FXML
    private TableColumn<Coffee, Integer> receiptCoffeePrice;
    @FXML
    private Label sum;

    private Stage dialogStage;
    private ObservableList<Coffee> coffeeCart;
    private boolean isBtnClicked = false;


    @FXML
    private void initialize() {

        sum.setText("₩");

        // need to initialize table view with all of ingredientObservableList
        receiptCoffeeName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        receiptCoffeePrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());




    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage; // 주석 UTF8로 다시 적어주세용
    }

    public void setCoffeeCart(ObservableList<Coffee> coffeeCart) {
        this.coffeeCart = coffeeCart;
        this.receiptTable.setItems(this.coffeeCart);
        calculateSum();
    }



    private void calculateSum() {
        int sum = 0;
        Iterator<Coffee> it = this.coffeeCart.iterator();
        while (it.hasNext()) {
            sum += it.next().getPrice();
        }
        this.sum.setText(sum + " ₩");
    }

    public boolean isBtnClicked() {
        return isBtnClicked;
    }

    @FXML
    private void handleYes() {
        System.out.println("Print Receipt!");
        isBtnClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleNo() {
        System.out.println("Don't Print Receipt!");
        isBtnClicked = true;
        dialogStage.close();
    }


}


