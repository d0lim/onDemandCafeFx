package cafe.controller;

import cafe.MainApp;
import cafe.model.Coffee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import javax.swing.*;
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
    @FXML
    private AnchorPane scrollBoard;
    @FXML
    private Button order;

    private CoffeeHandler coffeeHandler;

    private MainApp mainApp;

    private ObservableList<Coffee> coffeeCart = FXCollections.observableArrayList();

    private double columnSize = 0;
    private double rowSize = 0;
    private int numOfRow = 5;

    private String btnDefault = "-fx-background-color: #b9ceeb; ";
    private String btnEntered = "-fx-background-color: #87a8d0; ";
    private String btnSpecial = "-fx-border-color: #ffc145; -fx-border-width: 3px; ";

    @FXML
    private void initialize() {
        //initialize cell datas
        this.columnSize = gridPane.getPrefWidth() / 5;
        this.rowSize = gridPane.getPrefHeight() / 5;
        IngredientHandler.initalizeIngredient();
        order.setOnMouseEntered(ActionEvent -> {
            order.setStyle(btnEntered);
        });
        order.setOnMouseExited(ActionEvent -> {
            order.setStyle(btnDefault);
        });

        // coffeeName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        // coffeePrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());

        selectedCoffeeName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        selectedCoffeePrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());

        setCoffeeList();
        setMenu();

        addButtonToSelectedCoffeeTable();
        addEditButtonToSelectedCoffeeTable();

    }

    private void setCoffeeList() {
        this.coffeeHandler = new CoffeeHandler();
    }

    private Button createButton(Coffee coffee, Boolean isSpecial) {
        Button button = new Button(coffee.getName());
        button.setPrefSize(this.columnSize, this.rowSize);
        button.setStyle(btnDefault);
        if (isSpecial) {
            button.setStyle(btnDefault + btnSpecial);
        }
        button.setOnMouseEntered(ActionEvent -> {
            button.setStyle(btnEntered);
            if (isSpecial) {
                button.setStyle(btnSpecial + btnEntered);
            }
        });
        button.setOnMouseExited(ActionEvent -> {
            button.setStyle(btnDefault);
            if (isSpecial) {
                button.setStyle(btnDefault + btnSpecial);
            }
        });
        return button;
    }

    private Button createButton(String text, Boolean isSpecial) {
        Button button = new Button(text);
        button.setPrefSize(this.columnSize, this.rowSize);
        button.setStyle(btnDefault + btnSpecial);
        button.setOnMouseEntered(ActionEvent -> {
            button.setStyle(btnEntered + btnSpecial);
        });
        button.setOnMouseExited(ActionEvent -> {
            button.setStyle(btnDefault + btnSpecial);
        });
        return button;
    }

    private void setMenu() {
        int count = 0;
        ObservableList<Coffee> coffeeObservableList = coffeeHandler.getCoffees();
        for (Coffee coffee : coffeeObservableList) {
            Button coffeeBtn = createButton(coffee, coffee.getIsSpecial());
            coffeeBtn.setOnAction((ActionEvent evnet) -> {
                Coffee clicked = findCoffeeOnList(coffeeBtn.getText());
                handleMakeOrder(clicked);
            });
            gridPane.add(coffeeBtn, count % 5, count / 5);
            GridPane.setMargin(coffeeBtn, new Insets(15, 15, 15, 15));
            count++;
        }
        Button addBtn = createButton("+", true);
        addBtn.setOnAction((ActionEvent event) -> {
            handleNewCoffee();
        });
        if (count >= numOfRow * 5) {
            scrollBoard.setPrefHeight(scrollBoard.getHeight() + rowSize);
            gridPane.setPrefHeight(scrollBoard.getHeight());
            gridPane.addRow(1);
        }
        gridPane.add(addBtn, count % 5, count / 5);
        GridPane.setMargin(addBtn, new Insets(15, 15, 15, 15));
    }

    private Coffee findCoffeeOnList(String name) {
        ObservableList<Coffee> coffeeObservableList = coffeeHandler.getCoffees();
        for (Coffee current : coffeeObservableList) {
            if (name.equals(current.getName())) {
                return current;
            }
        }
        System.out.println("There's no Such coffee of that name");
        return null;
    }

    private void editCart(Coffee item, boolean add) {
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
        int okClicked = MainApp.showCustomerCoffeeEditDialog(temp, false, false);
        if (okClicked == 2) {
            temp.setIsSpecial(true);
            coffeeHandler.getCoffees().add(temp);
            coffeeHandler.getDefaultCoffees().add(temp);
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

            int okClicked = MainApp.showCustomerCoffeeEditDialog(temp, true, false);

            if (okClicked == 1) {
                if (!CoffeeHandler.isDefault(temp))
                    temp.setName(temp.getName() + " *");
                editCart(temp, true);
            } else if (okClicked == 2) {
                temp.setIsSpecial(true);
                coffeeHandler.getCoffees().add(temp);
                coffeeHandler.getDefaultCoffees().add(temp);
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
            int okClicked = MainApp.showCustomerCoffeeEditDialog(temp, true, true);
            if (okClicked == 1) {
                clicked.setName(temp.getName());
                clicked.setPrice(temp.getPrice());
                clicked.getIngreList().clear();
                clicked.getIngreList().addAll(temp.getIngreList());
                calculateSum();
            } else if (okClicked == 2) {
                temp.setIsSpecial(true);
                coffeeHandler.getCoffees().add(temp);
                coffeeHandler.getDefaultCoffees().add(temp);
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
                    private final Button actionBtn = new Button("Remove");

                    {
                        actionBtn.setStyle("-fx-background-color: #b9ceeb");
                        actionBtn.setOnMouseEntered(ActionEvent -> {
                            actionBtn.setStyle("-fx-background-color: #87a8d0");
                        });
                        actionBtn.setOnMouseExited(ActionEvent -> {
                            actionBtn.setStyle("-fx-background-color: #b9ceeb");
                        });
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
                        actionBtn.setStyle("-fx-background-color: #b9ceeb");
                        actionBtn.setOnMouseEntered(ActionEvent -> {
                            actionBtn.setStyle("-fx-background-color: #87a8d0");
                        });
                        actionBtn.setOnMouseExited(ActionEvent -> {
                            actionBtn.setStyle("-fx-background-color: #b9ceeb");
                        });
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

