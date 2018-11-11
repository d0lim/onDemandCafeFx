package cafe;

import cafe.model.Coffee;
import cafe.model.CoffeeHandler;
import cafe.view.CoffeeEditController;
import cafe.view.CoffeeOverview;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("OOP Cafe Coffee");

        initRootLayout();

        showCoffeeOverview();

    }

    public void initRootLayout() {
        try {
            // 주석 UTF8로 다시 적어주세용
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // 주석 UTF8로 다시 적어주세용
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCoffeeOverview() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CoffeeOverview.fxml"));
            AnchorPane IngredientOverview = (AnchorPane) loader.load();


            // 주석 UTF8로 다시 적어주세용
            rootLayout.setCenter(IngredientOverview);

            CoffeeHandler temp = new CoffeeHandler();
            CoffeeOverview controller = loader.getController();
            controller.setMainApp(temp, this);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int showCoffeeEditDialog(Coffee coffee, boolean editMenu) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CoffeeEditDialog.fxml"));
            AnchorPane page = loader.load();


            Stage dialogStage = new Stage();


            dialogStage.setTitle("Edit Coffee");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);


            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CoffeeEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCoffee(coffee);
            controller.setEditMode(editMenu);

            dialogStage.showAndWait();


            return controller.isOkClicked();


        } catch (IOException e) {
            e.printStackTrace();

            return 0;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
