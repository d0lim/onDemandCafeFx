package cafe.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class IngredientHandler {

	public static ObservableList<Ingredient> i_s = FXCollections.observableArrayList();

	 public ObservableList<Ingredient> getIngredients(){
		 init_i();
			return i_s;
		}

	private void init_i() {
		//ArrayList<Ingredient> i_s=new ArrayList<Ingredient>();
		i_s.add(new Ingredient("espresso",2000));
		i_s.add(new Ingredient("steamed milk",1000));
		i_s.add(new Ingredient("water",3000));
		i_s.add(new Ingredient("chocolate syrup",5000));
		
	}
	
	
}
