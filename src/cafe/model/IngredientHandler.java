package cafe.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class IngredientHandler {

    static private boolean isFirst = true;

    public static ObservableList<Ingredient> i_s = FXCollections.observableArrayList();

    public ObservableList<Ingredient> getIngredients() {

        if (isFirst) {
            init_i();
            isFirst = false;
        }
        return i_s;
    }

    private void init_i() {
        //ArrayList<Ingredient> i_s=new ArrayList<Ingredient>();
        i_s.add(new Ingredient("Espresso", 1000));
        i_s.add(new Ingredient("Steamed Milk", 1000));
        i_s.add(new Ingredient("Water", 500));
        i_s.add(new Ingredient("Chocolate Syrup", 1000));

    }

}
