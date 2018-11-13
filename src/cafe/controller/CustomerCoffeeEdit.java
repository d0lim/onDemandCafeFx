package cafe.controller;

import cafe.MainApp;
import cafe.model.Coffee;
import cafe.model.Ingredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Iterator;

public class CustomerCoffeeEdit {


    @FXML
    private TableView<Ingredient> selectedIngreTable;
    @FXML
    private TableColumn<Ingredient, String> selectedIngreName;
    @FXML
    private TableColumn<Ingredient, Integer> selectedIngrePrice;
    @FXML
    private Label sum;
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane scrollBoard;
    @FXML
    private TextField name;
    @FXML
    private Button okBtn;

    private ObservableList<Ingredient> ingredients;

    private Stage dialogStage;

    private Coffee coffee;

    private int clickedButton = 0;
    private double columnSize = 0;
    private double rowSize = 0;
    private int numOfRow = 5;

    @FXML
    private void initialize() {

        //initialize cell datas

        this.columnSize = gridPane.getPrefWidth() / 5;
        this.rowSize = gridPane.getPrefHeight() / 5;
        IngredientHandler.initalizeIngredient();


        // coffeeName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        // coffeePrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());

        selectedIngreName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        selectedIngrePrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());

        setIngreList();
        setIngreBtn();
        addButtonToSelectedTable();

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage; // 주석 UTF8로 다시 적어주세용
    }


    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
        name.setText(this.coffee.getName());

        this.selectedIngreTable.setItems(FXCollections.observableArrayList(this.coffee.getIngreList()));
        this.sum.setText("₩ " + this.coffee.getPrice());
    }

    private void setIngreList() {
        this.ingredients = IngredientHandler.getIngredientObservableList();
    }

    private Button createButton(Ingredient ingredient) {
        Button button = new Button(ingredient.getName());
        button.setPrefSize(this.columnSize, this.rowSize);
        button.setStyle("-fx-background-color: white");
        button.setOnMouseEntered(ActionEvent -> {
            button.setStyle("-fx-background-color: #ffff73");
            button.setText("+");
        });
        button.setOnMouseExited(ActionEvent -> {
            button.setStyle("-fx-background-color: white");
            button.setText(ingredient.getName());
        });
        return button;
    }

    private void setIngreBtn() {
        int count = 0;
        for (Ingredient ingredient : ingredients) {
            Button ingreBtn = createButton(ingredient);
            ingreBtn.setOnAction((ActionEvent evnet) -> {
                //action that adds this ingredients to this.coffee.ingreList
                editIngreSelection(ingredient, true);
            });
            gridPane.add(ingreBtn, count % 5, count / 5);
            GridPane.setMargin(ingreBtn, new Insets(15, 15, 15, 15));
            count++;
        }
        if (count >= numOfRow * 5) {
            scrollBoard.setPrefHeight(scrollBoard.getHeight() + rowSize);
            gridPane.setPrefHeight(scrollBoard.getHeight());
            gridPane.addRow(1);
        }
    }

    private void editIngreSelection(Ingredient clicked, boolean add) {
        if (add)
            this.coffee.getIngreList().add(clicked);
        else
            this.coffee.getIngreList().remove(clicked);
        this.coffee.calculatePrice();
        this.selectedIngreTable.setItems(FXCollections.observableArrayList(this.coffee.getIngreList()));
        Coffee recommended = CoffeeHandler.getSameIngredients(this.coffee);
        if (recommended != null)
            name.setPromptText(recommended.getName());
        else
            name.setPromptText("");
        this.sum.setText(this.coffee.getPrice() + " ₩");
    }

    /**
     * if okClicked == 1 -> ok button is clicked
     * if okClicked == 2 -> save as new menu
     */
    public int isOkClicked() {
        return clickedButton;
    }

    public void setEditMode(boolean editMode) {
        if (!editMode)
            okBtn.setVisible(false);
    }

    public void setIsManaging(boolean isManaging) {
        if (isManaging)
            okBtn.setText("Save");
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            coffee.setName(name.getText());
            coffee.setPrice(this.coffee.getPrice());

            // okClicked = true;
            clickedButton = 2;
            dialogStage.close();
        }
    }

    @FXML
    private void handleOk() { //add to cart or save
        if (isInputValid()) {
            coffee.setName(name.getText());
            coffee.setPrice(this.coffee.getPrice());

            // okClicked = true;
            clickedButton = 1;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {

        String errorMessage = "";
        if (name.getText() == null || name.getText().length() == 0) {
            errorMessage += "Not valid name";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }

    }

    // Event of order button from callback function
    private void addButtonToSelectedTable() {
        TableColumn<Ingredient, Void> colBtn = new TableColumn("");
        Callback<TableColumn<Ingredient, Void>, TableCell<Ingredient, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Ingredient, Void> call(final TableColumn<Ingredient, Void> param) {
                final TableCell<Ingredient, Void> cell = new TableCell<>() {
                    private final Button actionBtn = new Button("Remove");

                    {
                        actionBtn.setOnAction((ActionEvent event) -> {
                            Ingredient clicked = getTableView().getItems().get(getIndex());
                            editIngreSelection(clicked, false);
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
        selectedIngreTable.getColumns().add(colBtn);
    }



}

