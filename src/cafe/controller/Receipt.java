package cafe.controller;


import cafe.model.Coffee;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

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
        for (Coffee aCoffeeCart : this.coffeeCart) {
            sum += aCoffeeCart.getPrice();
        }
        this.sum.setText(sum + " ₩");
    }

    public boolean isBtnClicked() {
        return isBtnClicked;
    }

    @FXML
    private void handleYes() {
       
        isBtnClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleNo() {
        
        isBtnClicked = true;
        dialogStage.close();
    }


}


