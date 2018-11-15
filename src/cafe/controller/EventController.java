package cafe.controller;

import cafe.MainApp;
import cafe.model.Coffee;
import cafe.model.Ingredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class EventController {

    @FXML
    private TableView<Ingredient> ingredientTable;
    @FXML
    private TableColumn<Ingredient, String> ingredientName;
    @FXML
    private TableColumn<Ingredient, Integer> ingredientPrice;
    @FXML
    private TableView<Coffee> coffeeTable;
    @FXML
    private TableColumn<Coffee, String> coffeeName;
    @FXML
    private TableColumn<Coffee, Integer> coffeeOriginalPrice;
    @FXML
    private TableView<Ingredient> selectedIngTable;
    @FXML
    private TableView<Coffee> selectedCofTable;
    @FXML
    private TableColumn<Coffee, String> selectedCofName;
    @FXML
    private TableColumn<Coffee, Integer> changedCofPrice;
    @FXML
    private TableColumn<Ingredient, String> selectedIngName;
    @FXML
    private TableColumn<Ingredient, Integer> selectedingprice;
    @FXML
    private TableColumn<Ingredient, Integer> changedIngPrice;
    @FXML
    private TextField ingredientPercent;
    @FXML
    private TextField coffeePercent;

    private CoffeeFactory coffeeFactory = new CoffeeFactory();
    private IngredientFactory ingredientFactory = new IngredientFactory();
    //커피 TableView TalbleColumn 추가하기
    private Ingredient selectedIngredient = ingredientFactory.createIngredient();
    private ObservableList<Ingredient> selectedIngredientList = FXCollections.observableArrayList();
    private ObservableList<Coffee> selectedCoffeeList = FXCollections.observableArrayList();
    private Coffee selectedCoffee = coffeeFactory.createCoffee();
    private Coffee cancelingCoffee = coffeeFactory.createCoffee();
    private Ingredient cancelingIngredient = ingredientFactory.createIngredient();

    private CoffeeHandler coffeeHandler;


    @FXML
    private void initialize() {
        manageIngredientEvents();
        manageCoffeeEvents();
    }

    private void manageIngredientEvents() {
        ingredientTable.setItems(IngredientHandler.getIngredientObservableList());
        ingredientName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        ingredientPrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        ingredientTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedIngredient = newValue;
        });
        selectedIngTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cancelingIngredient = newValue;
        });

        selectedIngTable.setItems(selectedIngredientList);
        selectedIngName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        changedIngPrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());

    }

    private void manageCoffeeEvents() {
        coffeeName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        coffeeOriginalPrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        setCoffeeList();

        coffeeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedCoffee = newValue;
        });
        selectedCofTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cancelingCoffee = newValue;
        });

        selectedCofTable.setItems(selectedCoffeeList);
        selectedCofName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        changedCofPrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());

    }

    private void alert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleIngredientSale() {
        int changedprice = 0;
        //percentage에 정수가 안들어오는 경우 체크해줘야됨
        if (checkIngSale()) {

            if (selectedIngredient != null) {

                changedprice = selectedIngredient.getPrice() - (selectedIngredient.getPrice() * Integer.parseInt(ingredientPercent.getText()) / 100);

                selectedIngredient.setSalePrice(changedprice);//여기오류
                selectedIngredientList.add(selectedIngredient);

                ingSaleOn();

                refreshCoffees();
            }
        }
    }

    private boolean checkIngSale() {
        for (Ingredient temp : selectedIngredientList) {
            if (selectedIngredient.equals(temp)) {
                alert("already on sale");
                return false;
            }
        }
        return true;
    }

    private void refreshCoffees() {
        ObservableList<Coffee> coffeeList = this.coffeeHandler.getCoffees();

        for (Coffee current : coffeeList) {
            current.calculatePrice();
        }
        coffeeTable.refresh();
    }

    @FXML
    private void handleCoffeeSale() {
        int changedprice = 0;
        //percentage에 정수가 안들어오는 경우 체크해줘야됨
        if (checkCofSale()) {

            if (selectedCoffee != null) {

                changedprice = selectedCoffee.getPrice() - (selectedCoffee.getPrice() * Integer.parseInt(coffeePercent.getText()) / 100);

                selectedCoffee.setSalePrice(changedprice);//여기오류
                selectedCoffeeList.add(selectedCoffee);

                cofSaleOn();

                //refreshCoffees();
            }
        }
    }

    private boolean checkCofSale() {
        for (Coffee temp : selectedCoffeeList) {
            if (selectedCoffee.equals(temp)) {
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

    private void ingSaleOn() {
        selectedIngredient.swap_price();
        selectedIngredient.setName(selectedIngredient.getName()+"(On Sale)");
        
    }

    private void ingSaleOff() {
        cancelingIngredient.swap_price();
        cancelingIngredient.setName(cancelingIngredient.getName().replace("(On Sale)", ""));
    }

    private void cofSaleOn() {
        selectedCoffee.swap_price();
        selectedCoffee.setName(selectedCoffee.getName()+"(On Sale)");
    }

    private void cofSaleOff() {
        cancelingCoffee.swap_price();
        cancelingCoffee.setName(cancelingCoffee.getName().replace("(On Sale)", ""));
    }

    @FXML
    private void cancelIngSale() {
        ingSaleOff();
        selectedIngredientList.remove(cancelingIngredient);
    }

    @FXML
    private void cancelCofSale() {
        cofSaleOff();
        selectedCoffeeList.remove(cancelingCoffee);
    }

    @FXML
    private void goBack() {
        MainApp.start_program();
    }

}
