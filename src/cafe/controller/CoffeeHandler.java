package cafe.controller;


import cafe.model.Coffee;
import cafe.model.Ingredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;


class CoffeeHandler {

    static private boolean isFirst = true;

    private static ObservableList<Coffee> coffees = FXCollections.observableArrayList();
    private static ObservableList<Coffee> defaultCoffees = FXCollections.observableArrayList();

    static boolean isDefault(Coffee coffee) {
        for (Coffee current : defaultCoffees) {
            if (current.getName().equals(coffee.getName()) && current.getIngreList().equals(coffee.getIngreList()))
                return true;
        }
        return false;
    }

    static Coffee getSameIngredients(Coffee coffee) {
        for (Coffee current : defaultCoffees) {
            if (coffee.getIngreList().containsAll(current.getIngreList()) && current.getIngreList().containsAll(coffee.getIngreList()))
                return current;
        }
        return null;
    }

    static boolean isSameName(String name) {
        for (Coffee current : defaultCoffees) {
            if (current.getName().equals(name))
                return true;
        }
        return false;
    }

    ObservableList<Coffee> getCoffees() {
        if (isFirst) {
            initializeCoffee();
            isFirst = false;
        }
        return coffees;
    }

    ObservableList<Coffee> getDefaultCoffees() {
        return defaultCoffees;
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
        defaultCoffees.addAll(coffees);

    }

}
