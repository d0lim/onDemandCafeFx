package cafe.controller;


import cafe.model.Coffee;
import cafe.model.Ingredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;


class CoffeeHandler {

    static private boolean isFirst = true;

    private static ObservableList<Coffee> coffees = FXCollections.observableArrayList();


    static boolean isDefault(Coffee coffee) {
        for (Coffee current : coffees) {
            if (current.getName().equals(coffee.getName()) && current.getIngreList().equals(coffee.getIngreList()))
                return true;
        }
        return false;
    }

    static Coffee getSameIngredients(Coffee coffee) {
        for (Coffee current : coffees) {
            if (coffee.getIngreList().containsAll(current.getIngreList()) && current.getIngreList().containsAll(coffee.getIngreList()))
                return current;
        }
        return null;
    }

    static boolean isSameName(Coffee coffee) {
        for (Coffee current : coffees) {
            if (current.getName().equals(coffee.getName()))
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

    private void initializeCoffee() {
        CoffeeFactory coffeeFactory = new CoffeeFactory();
        //ArrayList<Coffee> coffees=new ArrayList<Coffee>();
        ArrayList<Ingredient> tmpIngreList = new ArrayList<>();

        // make some Ingredient List

        // Americano
        tmpIngreList.add(IngredientHandler.findIngredint("Espresso"));
        tmpIngreList.add(IngredientHandler.findIngredint("Water"));

        coffees.add(coffeeFactory.createCoffee("Americano", tmpIngreList));


        // init
        tmpIngreList = new ArrayList<>();

        // Cafe Latte
        tmpIngreList.add(IngredientHandler.findIngredint("Espresso"));
        tmpIngreList.add(IngredientHandler.findIngredint("Water"));
        tmpIngreList.add(IngredientHandler.findIngredint("Steamed Milk"));

        coffees.add(coffeeFactory.createCoffee("Cafe Latte", tmpIngreList));

        // init
        tmpIngreList = new ArrayList<>();

        // Cafe Mocha
        tmpIngreList.add(IngredientHandler.findIngredint("Espresso"));
        tmpIngreList.add(IngredientHandler.findIngredint("Water"));
        tmpIngreList.add(IngredientHandler.findIngredint("Steamed Milk"));
        tmpIngreList.add(IngredientHandler.findIngredint("Chocolate Syrup"));

        coffees.add(coffeeFactory.createCoffee("Cafe Mocha", tmpIngreList));
    }

}
