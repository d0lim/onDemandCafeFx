package cafe;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import cafe.controller.CoffeeEditController;

import cafe.controller.IngredientEditController;

import cafe.controller.Receipt;

import cafe.model.Coffee;

import cafe.model.Ingredient;


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
        
		//StartScreenController s=new StartScreenController();
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
	
	 public static void showOwnerTabs() {
		 try {
			 FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/OwnerTabs.fxml"));
	            AnchorPane OwnerTab = (AnchorPane) loader.load();
	            
	            rootLayout.setCenter(OwnerTab);
	            
	            //OwnerTabController control=new OwnerTabController();
	           // control.setMainApp()
	            
		 }
		 catch (IOException e){
			 e.printStackTrace();
		 }
	 }
	 public static void showCustomerPage() {
		 try {
			 FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/CoffeeOverview.fxml"));
	            AnchorPane CustomerPage = (AnchorPane) loader.load();
	            
	            rootLayout.setCenter(CustomerPage);
	            
	            //Coffee control=new OwnerTabController();
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

	   public static boolean showReceipt(ObservableList<Coffee> coffeeCart) {
	        try {

	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/Receipt.fxml"));
	            AnchorPane page = loader.load();


	            Stage dialogStage = new Stage();


	            dialogStage.setTitle("Receipt");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            //dialogStage.initOwner(primaryStage);


	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);

	            Receipt controller = loader.getController();
	            controller.setCoffeeCart(coffeeCart);
	            controller.setDialogStage(dialogStage);

	            // need to set observable list to Receipt instance


	            dialogStage.showAndWait();


	            // return controller.isOkClicked();
	            // need to change isOKClicked to isButtonClicked
	            return controller.isBtnClicked();


	        } catch (IOException e) {
	            e.printStackTrace();

	            return false;
	        }
	    }
	   public static int showCoffeeEditDialog(Coffee coffee, boolean editMenu, boolean isManaging) {
	        try {

	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MainApp.class.getResource("view/CoffeeEditDialog.fxml"));
	            AnchorPane page = loader.load();


	            Stage dialogStage = new Stage();


	            dialogStage.setTitle("Edit Coffee");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            //dialogStage.initOwner(primaryStage);


	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);

	            CoffeeEditController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.setCoffee(coffee);
	            controller.setEditMode(editMenu);
	            controller.setIsManaging(isManaging);

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

	 public MainApp Mainapp() {
		 return this;
	 }
	
	public static void main(String[] args) {
		launch(args);
	}
}
