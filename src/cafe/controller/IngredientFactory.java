package cafe.controller;

import cafe.model.Ingredient;

public class IngredientFactory {
    Ingredient createIngredient(String name, int price) {
        Ingredient ingredient = new Ingredient(name, price);
        return ingredient;
    }
    Ingredient createIngredient() {
        return this.createIngredient(null, 0);
    }
}

