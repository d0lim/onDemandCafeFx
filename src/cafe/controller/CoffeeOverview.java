package cafe.controller;

import cafe.MainApp;
import cafe.model.Coffee;
import cafe.model.CoffeeHandler;
import cafe.model.IngredientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.Iterator;

public class CoffeeOverview {


    @FXML
    private TableView<Coffee> selectedCoffeeTable;
    @FXML
    private TableColumn<Coffee, String> selectedCoffeeName;
    @FXML
    private TableColumn<Coffee, Integer> selectedCoffeePrice;
    @FXML
    private Label sum;
    @FXML
    private GridPane gridPane;

    private CoffeeHandler coffeeHandler;

    private MainApp mainApp;

    private ObservableList<Coffee> coffeeCart = FXCollections.observableArrayList();

    private double columnSize = 0;
    private double rowSize = 0;

    @FXML
    private void initialize() {

        //initialize cell datas

        this.columnSize = gridPane.getPrefWidth() / 5;
        this.rowSize = gridPane.getPrefHeight() / 5;
        IngredientHandler.initalizeIngredient();


        // coffeeName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        // coffeePrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());

        selectedCoffeeName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        selectedCoffeePrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());

        setCoffeeList();
        setMenu();

        addButtonToSelectedCoffeeTable();
        addEditButtonToSelectedCoffeeTable();


    }

    public void setCoffeeList() {
        this.coffeeHandler = new CoffeeHandler();
    }

    public void setMainApp(CoffeeHandler coffeeHandler, MainApp mainApp) {
        this.coffeeHandler = coffeeHandler;
        this.mainApp = mainApp;
        // 주석 UTF8로 다시 적어주세용
        //coffeeTable.setItems(coffeeHandler.getCoffees());
    }


    public Button createButton(Coffee coffee) {
        Button button = new Button(coffee.getName());
        button.setPrefSize(this.columnSize, this.rowSize);
        return button;
    }

    public Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(this.columnSize, this.rowSize);
        return button;
    }

    public void setMenu() {
        int count = 0;
        ObservableList<Coffee> coffeeObservableList = coffeeHandler.getCoffees();
        Iterator<Coffee> it = coffeeObservableList.iterator();
        while (it.hasNext()) {
            Button coffeeBtn = createButton(it.next());



            coffeeBtn.setOnAction((ActionEvent evnet) -> {
                Coffee clicked = findCoffeeOnList(coffeeBtn.getText());
                handleMakeOrder(clicked);
            });
            gridPane.add(coffeeBtn, count % 5, count / 5);
            gridPane.setMargin(coffeeBtn, new Insets(15, 15, 15, 15));
            count++;
        }
        Button addBtn = createButton("+");
        addBtn.setOnAction((ActionEvent event) -> {
            handleNewCoffee();
        });
        gridPane.add(addBtn, count % 5, count / 5);
        gridPane.setMargin(addBtn, new Insets(15, 15, 15, 15));


    }

    public Coffee findCoffeeOnList(String name) {
        ObservableList<Coffee> coffeeObservableList = coffeeHandler.getCoffees();
        Iterator<Coffee> it = coffeeObservableList.iterator();
        while (it.hasNext()) {
            Coffee current = it.next();
            if (name.equals(current.getName())) {
                return current;
            }
        }
        System.out.println("There's no Such coffee of that name");
        return null;
    }

    public void editCart(Coffee item, boolean add) {
        if (add)
            this.coffeeCart.add(item);
        else
            this.coffeeCart.remove(item);
        this.selectedCoffeeTable.setItems(coffeeCart);
        calculateSum();
    }

    /**
     * if okClicked == 1 -> ok button is clicked
     * if okClicked == 2 -> save as new menu
     */

    @FXML 
	private void goback() {
		MainApp.start_program();
	}
    
    @FXML
    private void handleReceiptButton() {
        boolean btnClicked = MainApp.showReceipt(this.coffeeCart);
        if (btnClicked) {
            coffeeCart = null;
            coffeeCart = FXCollections.observableArrayList();
            selectedCoffeeTable.getItems().clear();
        }
    }

    @FXML
    private void handleNewCoffee() {
        Coffee temp = new Coffee();
        int okClicked = MainApp.showCoffeeEditDialog(temp, false);
        if (okClicked == 2) {
            coffeeHandler.getCoffees().add(temp);
            setMenu();
        }
    }

    // Event of order button from callback function
    private void handleMakeOrder(Coffee selected) {
        if (selected != null) {
            Coffee temp = new Coffee();
            temp.setName(selected.getName());
            temp.setPrice(selected.getPrice());
            temp.getIngreList().addAll(selected.getIngreList());

            int okClicked = mainApp.showCoffeeEditDialog(temp, true);

            if (okClicked == 1) {
                editCart(temp, true);
            } else if (okClicked == 2) {
                coffeeHandler.getCoffees().add(temp);
                setMenu();
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

    private void handleEdit(Coffee clicked) {
        if (selectedCoffeeTable != null) {
            Coffee temp = new Coffee();
            temp.setName(clicked.getName());
            temp.setPrice(clicked.getPrice());
            temp.getIngreList().addAll(clicked.getIngreList());
            int okClicked = mainApp.showCoffeeEditDialog(temp, true);
            if (okClicked == 1) {
                clicked.setName(temp.getName());
                clicked.setPrice(temp.getPrice());
                clicked.getIngreList().clear();
                clicked.getIngreList().addAll(temp.getIngreList());
                calculateSum();
            } else if (okClicked == 2) {
                coffeeHandler.getCoffees().add(temp);
                setMenu();
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

    private void calculateSum() {
        int sum = 0;
        Iterator<Coffee> it = this.coffeeCart.iterator();
        while (it.hasNext()) {
            sum += it.next().getPrice();
        }
        this.sum.setText(sum + " ₩");
    }


    private void addButtonToSelectedCoffeeTable() {
        TableColumn<Coffee, Void> colBtn = new TableColumn("");
        Callback<TableColumn<Coffee, Void>, TableCell<Coffee, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Coffee, Void> call(final TableColumn<Coffee, Void> param) {
                final TableCell<Coffee, Void> cell = new TableCell<>() {
                    private final Button actionBtn = new Button("Remove from Cart");

                    {
                        actionBtn.setOnAction((ActionEvent event) -> {
                            Coffee clicked = getTableView().getItems().get(getIndex());
                            editCart(clicked, false);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(actionBtn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        selectedCoffeeTable.getColumns().add(colBtn);
    }


    private void addEditButtonToSelectedCoffeeTable() {
        TableColumn<Coffee, Void> colBtn = new TableColumn("");
        Callback<TableColumn<Coffee, Void>, TableCell<Coffee, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Coffee, Void> call(final TableColumn<Coffee, Void> param) {
                final TableCell<Coffee, Void> cell = new TableCell<>() {
                    private final Button actionBtn = new Button("Edit");

                    {
                        actionBtn.setOnAction((ActionEvent event) -> {
                            Coffee clicked = getTableView().getItems().get(getIndex());
                            handleEdit(clicked);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(actionBtn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        selectedCoffeeTable.getColumns().add(colBtn);
    }

}

