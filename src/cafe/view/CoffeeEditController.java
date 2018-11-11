package cafe.view;


import cafe.model.Coffee;
import cafe.model.Ingredient;
import cafe.model.IngredientHandler;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CoffeeEditController {

    @FXML
    private TextField name;
    @FXML
    private TableView<Ingredient> ingreTable;
    @FXML
    private TableColumn<Ingredient, String> ingreName;
    @FXML
    private TableColumn<Ingredient, Integer> ingrePrice;

    // if some ingredients are selected, we need to add those ingredients to Selected Table

    @FXML
    private TableView<Ingredient> selectedTable;
    @FXML
    private TableColumn<Ingredient, String> selectedIngreName;
    @FXML
    private TableColumn<Ingredient, Integer> selectedIngrePrice;
    @FXML
    private Label sum;
    @FXML
    private Button saveBtn;
    @FXML
    private Button okBtn;

    private Stage dialogStage;
    private Coffee coffee;
    private int clickedButton = 0;


    @FXML
    private void initialize() {


        name.setText("");
        sum.setText("₩");

        // need to initialize table view with all of ingredients
        ingreName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        ingrePrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());

        selectedIngreName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        selectedIngrePrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());


        setIngreList();

        addButtonToIngreTable();
        addButtonToSelectedTable();
        //ingreTable.getSelectionModel().selectedItemProperty().addListener(event -> editIngreSelection());

    }

    public void setIngreList() {
        IngredientHandler ingredientHandler = new IngredientHandler();
        this.ingreTable.setItems(ingredientHandler.getIngredients());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage; // 주석 UTF8로 다시 적어주세용
    }


    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;

        name.setText(this.coffee.getName());

        // need to add logic about setting ingredients
        this.selectedTable.setItems(FXCollections.observableArrayList(this.coffee.getIngreList()));
        this.sum.setText(this.coffee.getPrice() + " ₩");
    }


    private void editIngreSelection(Ingredient clicked, boolean add) {
        if (add)
            this.coffee.getIngreList().add(clicked);
        else
            this.coffee.getIngreList().remove(clicked);
        this.coffee.calculatePrice();
        this.selectedTable.setItems(FXCollections.observableArrayList(this.coffee.getIngreList()));
        this.sum.setText(this.coffee.getPrice() + " ₩");
    }

    public void setEditMode(boolean editMode) {
        if (!editMode)
            okBtn.setVisible(false);
    }

    private void addButtonToIngreTable() {
        TableColumn<Ingredient, Void> colBtn = new TableColumn("");
        Callback<TableColumn<Ingredient, Void>, TableCell<Ingredient, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Ingredient, Void> call(final TableColumn<Ingredient, Void> param) {
                final TableCell<Ingredient, Void> cell = new TableCell<>() {
                    private final Button actionBtn = new Button("Add");

                    {
                        actionBtn.setOnAction((ActionEvent event) -> {
                            Ingredient clicked = getTableView().getItems().get(getIndex());
                            editIngreSelection(clicked, true);
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
        ingreTable.getColumns().add(colBtn);
    }

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
        selectedTable.getColumns().add(colBtn);
    }

    public int isOkClicked() {
        return clickedButton;
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
    private void handleOk() {
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
}


