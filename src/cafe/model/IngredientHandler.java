package cafe.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class IngredientHandler {

	private static boolean isFirst= true;
	
	public static ObservableList<Ingredient> i_s = FXCollections.observableArrayList();

	 public static ObservableList<Ingredient> getIngredients(){
		 if(isFirst) {
		 init_i();
		 isFirst=false;
		 }
			return i_s;
		}
	
	 private static void init_i() {
			
			i_s.add(new Ingredient("espresso",2000));
			i_s.add(new Ingredient("steamed milk",1000));
			i_s.add(new Ingredient("water",3000));
			i_s.add(new Ingredient("chocolate syrup",5000));
			
		}
	 
	
	
	
}
