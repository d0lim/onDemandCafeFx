package cafe.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;


public class IngredientHandler {

    private static boolean isFirst = true;

    private static ObservableList<Ingredient> ingredientObservableList = FXCollections.observableArrayList();

    public static ObservableList<Ingredient> getIngredientObservableList() {
        if (isFirst) {
            initalizeIngredient();
            isFirst = false;
        }
        return ingredientObservableList;
    }

    public static void initalizeIngredient() {
        isFirst = false;

        ingredientObservableList.add(new Ingredient("Espresso", 2000));
        ingredientObservableList.add(new Ingredient("Steamed Milk", 1000));
        ingredientObservableList.add(new Ingredient("Water", 3000));
        ingredientObservableList.add(new Ingredient("Chocolate Syrup", 5000));

    }

    public static Ingredient findIngredint(String name) {
        Iterator<Ingredient> it = ingredientObservableList.iterator();
        while (it.hasNext()) {
            Ingredient current = it.next();
            //System.out.println(current.getName());
            if (current.getName().equals(name))
                return current;
        }
        System.out.println("No such ingredient of that name!");
        return null;
    }


}
