package cafe.controller;


import cafe.model.Ingredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


class IngredientHandler {

    private static boolean isFirst = true;



    private static ObservableList<Ingredient> ingredientObservableList = FXCollections.observableArrayList();

    
    static ObservableList<Ingredient> getIngredientObservableList() {
        if (isFirst) {
            initalizeIngredient();
            isFirst = false;
        }
        return ingredientObservableList;
    }

    
    static void initalizeIngredient() {
        IngredientFactory ingredientFactory = new IngredientFactory();
        if (isFirst) {
            isFirst = false;
            ingredientObservableList.add(ingredientFactory.createIngredient("Espresso", 2000));
            ingredientObservableList.add(ingredientFactory.createIngredient("Steamed Milk", 1000));
            ingredientObservableList.add(ingredientFactory.createIngredient("Water", 3000));
            ingredientObservableList.add(ingredientFactory.createIngredient("Chocolate Syrup", 5000));
            ingredientObservableList.add(ingredientFactory.createIngredient("Whipped Cream", 10000));
            ingredientObservableList.add(ingredientFactory.createIngredient("Milk Foam", 2500));
            ingredientObservableList.add(ingredientFactory.createIngredient("Vanilla Syrup", 1000));
            
        }
    }

    static Ingredient findIngredint(String name) {
        for (Ingredient current : ingredientObservableList) {
            //System.out.println(current.getName());
            if (current.getName().equals(name))
                return current;
        }
        System.out.println("No such ingredient of that name!");
        return null;
    }


}
