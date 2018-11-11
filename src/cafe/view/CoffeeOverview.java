package cafe.view;

import cafe.MainApp;
import cafe.model.Coffee;
import cafe.model.CoffeeHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CoffeeOverview {

    @FXML
    private TableView<Coffee> coffeeTable;
    @FXML
    private TableColumn<Coffee, String> coffeeName;
    @FXML
    private Label name;
    @FXML
    private Label price;

    private CoffeeHandler coffeeHandler;

    private MainApp mainApp;




    @FXML
    private void initialize() {

        coffeeName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

        showCoffeeDetails(null);

        coffeeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showCoffeeDetails(newValue));

    }

    public void setMainApp(CoffeeHandler coffeeHandler, MainApp mainApp) {
        this.coffeeHandler = coffeeHandler;
        this.mainApp = mainApp;
        // 주석 UTF8로 다시 적어주세용
        coffeeTable.setItems(coffeeHandler.getCoffees());
    }

    private void showCoffeeDetails(Coffee coffee) {
        if (coffee != null) {
            name.setText(coffee.getName());
            price.setText(Integer.toString(coffee.getPrice()));
        } else {
            name.setText("");
            price.setText("");
        }
    }

    @FXML
    private void handleDeleteCoffee() {
        int selectedIndex = coffeeTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            coffeeTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(AlertType.WARNING); // 주석 UTF8로 다시 적어주세용
            //alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Coffee Selected");
            alert.setContentText("Please select an Coffee");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewCoffee() {

        Coffee temp = new Coffee();

        boolean okClicked = mainApp.showCoffeeEditDialog(temp);
        if (okClicked) {
            coffeeHandler.getCoffees().add(temp);
        }


    }

    @FXML
    private void handleEditCoffee() {
        Coffee selected = coffeeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {

            boolean okClicked = mainApp.showCoffeeEditDialog(selected);

            if (okClicked) {
                showCoffeeDetails(selected);
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            //alert.initOwner(mainApp.getPrimaryStage());//왜오류떠?
            alert.setTitle("No Selection");
            alert.setHeaderText("No Coffee Selected");
            alert.setContentText("Please select an Coffee");

            alert.showAndWait();

        }
    }

}

