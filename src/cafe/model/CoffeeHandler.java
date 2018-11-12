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
        Ingredient espresso = new Ingredient("Espresso", 1000);
        Ingredient milk = new Ingredient("Steamed Milk", 1000);
        Ingredient water = new Ingredient("Water", 500);
        Ingredient choco = new Ingredient("Chocolate Syrup", 1000);

        // Americano
        tmpIngreList.add(espresso);
        tmpIngreList.add(water);

        coffees.add(new Coffee("Americano", tmpIngreList));


        // init
        tmpIngreList = new ArrayList<>();

        // Cafe Latte
        tmpIngreList.add(espresso);
        tmpIngreList.add(water);
        tmpIngreList.add(milk);

        coffees.add(new Coffee("Cafe Latte", tmpIngreList));

        // init
        tmpIngreList = new ArrayList<>();

        // Cafe Mocha
        tmpIngreList.add(espresso);
        tmpIngreList.add(water);
        tmpIngreList.add(milk);
        tmpIngreList.add(choco);

        coffees.add(new Coffee("Cafe Mocha", tmpIngreList));

    }


}
