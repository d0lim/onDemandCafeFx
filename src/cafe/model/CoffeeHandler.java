package cafe.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;


public class CoffeeHandler {

    static private boolean isFirst = true;

    public static ObservableList<Coffee> coffees = FXCollections.observableArrayList();

    public ObservableList<Coffee> getCoffees() {
        if (isFirst) {
            initializeCoffee();
            isFirst = false;
        }
        return coffees;
    }

    private void initializeCoffee() {
        //ArrayList<Coffee> coffees=new ArrayList<Coffee>();
        ArrayList<Ingredient> tmpIngreList = new ArrayList<>();

        // make some Ingredient List


        // Americano
        tmpIngreList.add(IngredientHandler.findIngredint("Espresso"));
        tmpIngreList.add(IngredientHandler.findIngredint("Water"));

        coffees.add(new Coffee("Americano", tmpIngreList));


        // init
        tmpIngreList = new ArrayList<>();

        // Cafe Latte
        tmpIngreList.add(IngredientHandler.findIngredint("Espresso"));
        tmpIngreList.add(IngredientHandler.findIngredint("Water"));
        tmpIngreList.add(IngredientHandler.findIngredint("Steamed Milk"));

        coffees.add(new Coffee("Cafe Latte", tmpIngreList));

        // init
        tmpIngreList = new ArrayList<>();

        // Cafe Mocha
        tmpIngreList.add(IngredientHandler.findIngredint("Espresso"));
        tmpIngreList.add(IngredientHandler.findIngredint("Water"));
        tmpIngreList.add(IngredientHandler.findIngredint("Steamed Milk"));
        tmpIngreList.add(IngredientHandler.findIngredint("Chocolate Syrup"));

        coffees.add(new Coffee("Cafe Mocha", tmpIngreList));

    }


}
