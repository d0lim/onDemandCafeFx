package cafe.view;

import cafe.MainApp;
import cafe.model.Coffee;
import cafe.model.CoffeeHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;

import java.util.Iterator;

public class CoffeeOverview {

    @FXML
    private TableView<Coffee> coffeeTable;
    @FXML
    private TableColumn<Coffee, String> coffeeName;
    @FXML
    private TableColumn<Coffee, Integer> coffeePrice;
    @FXML
    private Button newCoffee;
    @FXML
    private TableView<Coffee> selectedCoffeeTable;
    @FXML
    private TableColumn<Coffee, String> selectedCoffeeName;
    @FXML
    private TableColumn<Coffee, Integer> selectedCoffeePrice;
    @FXML
    private Label sum;

    private CoffeeHandler coffeeHandler;

    private MainApp mainApp;

    private ObservableList<Coffee> coffeeCart = FXCollections.observableArrayList();


    @FXML
    private void initialize() {

        //initialize cell datas

        coffeeName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        coffeePrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());

        selectedCoffeeName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        selectedCoffeePrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());

        setCoffeeList();
        addButtonToCoffeeTable();
        addButtonToSelectedCoffeeTable();
        addEditButtonToSelectedCoffeeTable();


    }

    public void setMainApp(CoffeeHandler coffeeHandler, MainApp mainApp) {
        this.coffeeHandler = coffeeHandler;
        this.mainApp = mainApp;
        // 주석 UTF8로 다시 적어주세용
        coffeeTable.setItems(coffeeHandler.getCoffees());
    }

    public void setCoffeeList() {
        CoffeeHandler coffeeHandler = new CoffeeHandler();
        this.coffeeTable.setItems(coffeeHandler.getCoffees());
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
    private void handleNewCoffee() {
        Coffee temp = new Coffee();
        int okClicked = mainApp.showCoffeeEditDialog(temp, false);
        if (okClicked == 2) {
            coffeeHandler.getCoffees().add(temp);
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
            } else if (okClicked == 2) {
                coffeeHandler.getCoffees().add(temp);
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

    private void addButtonToCoffeeTable() {
        TableColumn<Coffee, Void> colBtn = new TableColumn("");
        Callback<TableColumn<Coffee, Void>, TableCell<Coffee, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Coffee, Void> call(final TableColumn<Coffee, Void> param) {
                final TableCell<Coffee, Void> cell = new TableCell<>() {
                    private final Button actionBtn = new Button("Add to Cart");

                    {
                        actionBtn.setOnAction((ActionEvent event) -> {
                            Coffee clicked = getTableView().getItems().get(getIndex());
                            handleMakeOrder(clicked);
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
        coffeeTable.getColumns().add(colBtn);
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

