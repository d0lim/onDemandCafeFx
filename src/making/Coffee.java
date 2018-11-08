package making;


import java.util.ArrayList;
import java.util.Iterator;

public class Coffee {
    String name;
    ArrayList<Ingredient> ingre = new ArrayList<>();
    Iterator<Ingredient> it = ingre.iterator();
    int cost;

    public Coffee(String name, ArrayList<Ingredient> ingreList) {
        this.name = name;
        this.ingre.addAll(ingreList);
    }

    void costCalc() {
        while(it.hasNext()) {
            this.cost += it.next().cost;
        }
    }
}

class CoffeeHandler {
    Coffee makeCoffee(String name, ArrayList<Ingredient> ingreList) {
        return new Coffee(name, ingreList);
    }
    Coffee editCoffee(Coffee prev) {
        System.out.println(prev.ingre);
        //need to add some more..


        return prev;
    }
}
