package cafe.controller;

import cafe.model.Coffee;
import cafe.model.Ingredient;

import java.util.ArrayList;

public class CoffeeFactory {
    Coffee createCoffee(String name, int price, ArrayList<Ingredient> ingreList, Boolean isSpecial) {
        Coffee coffee = new Coffee(name, ingreList);
        coffee.setIsSpecial(isSpecial);
        coffee.setPrice(price);
        return coffee;
    }

    Coffee createCoffee(String name, ArrayList<Ingredient> ingreList) {
        Coffee coffee = new Coffee(name, ingreList);
        return coffee;
    }

    Coffee createCoffee(String name, int price) {
        Coffee coffee = new Coffee(name, price);
        return coffee;
    }

    Coffee createCoffee() {
        Coffee coffee = this.createCoffee(null, 0);
        return coffee;
    }
}
