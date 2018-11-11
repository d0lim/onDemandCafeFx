package cafe;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import cafe.view.IngredientEditController;
import cafe.view.IngredientOverview;
import cafe.view.OwnerTabController;
import cafe.view.StartScreenController;
import cafe.model.Ingredient;
import cafe.model.IngredientHandler;

public class MainApp extends Application {

	private Stage primaryStage;
	static  BorderPane rootLayout;
	
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("OOP Cafe");

        initRootLayout();

        start_program();

	}
	
	public static void start_program() {
		try {
		 FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainApp.class.getResource("view/StartScreen.fxml"));
         AnchorPane start = (AnchorPane) loader.load();

         rootLayout.setCenter(start);
		//첫화면띄우기
        
		StartScreenController s=new StartScreenController();
		//s.setScreen(this);
		
		rootLayout.requestFocus();
		start.requestFocus();
			
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
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

	 public static void showIngredientOverview() {
	        try {
	            
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/IngredientOverview.fxml"));
	            AnchorPane IngredientOverview = (AnchorPane) loader.load();



				// 주석 UTF8로 다시 적어주세용
	            rootLayout.setCenter(IngredientOverview);
	            
	            IngredientHandler temp=new IngredientHandler();
	            IngredientOverview controller = loader.getController();
	            controller.setMainApp(temp);
	            
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	 public static void showOwnerTabs() {
		 try {
			 FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/OwnerTabs.fxml"));
	            AnchorPane OwnerTab = (AnchorPane) loader.load();
	            
	            rootLayout.setCenter(OwnerTab);
	            
	            OwnerTabController control=new OwnerTabController();
	           // control.setMainApp()
	            
		 }
		 catch (IOException e){
			 e.printStackTrace();
		 }
	 }
	
	   public static boolean showIngredientEditDialog(Ingredient ingredient) {
		 try {
			
			 	FXMLLoader loader=new FXMLLoader();
			 	loader.setLocation(MainApp.class.getResource("view/IngredientEditDialog.fxml"));
			 	AnchorPane page = loader.load();
			 

			 	Stage dialogStage = new Stage();
			 	
		 	
		        dialogStage.setTitle("Edit Ingredient");
		        dialogStage.initModality(Modality.WINDOW_MODAL);
		        //dialogStage.initOwner(primaryStage);

		      
		        Scene scene = new Scene(page);
			 	dialogStage.setScene(scene);
		       

			 	
			 	
		        IngredientEditController controller = loader.getController();
		        controller.setDialogStage(dialogStage);
		        controller.setIngredient(ingredient);

		        
		        dialogStage.showAndWait();
		        
		        
		        return controller.isOkClicked();
			 
			
		 }
		 
		 catch(IOException e) {
			 e.printStackTrace();
			
			 return false;
		 }
	 }

	 public Stage getPrimaryStage() {
	        return primaryStage;
	    }

	 public MainApp Mainapp() {
		 return this;
	 }
	
	public static void main(String[] args) {
		launch(args);
	}
}
