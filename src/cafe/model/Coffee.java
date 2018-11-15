package cafe.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Iterator;

public class Coffee extends Ingredient{

    private StringProperty name;
    private IntegerProperty price;
    private IntegerProperty saleprice;
    private ArrayList<Ingredient> ingreList = new ArrayList<>();
    private Boolean isSpecial = false;

    public Coffee() {
        this(null, 0); // 주석 UTF8로 다시 적어주세용
    }

    public Coffee(String name, ArrayList<Ingredient> ingreList) {
        this.name = new SimpleStringProperty(name);
        this.ingreList = ingreList;
        Iterator<Ingredient> it = this.ingreList.iterator();
        int tmp = 0;
        while (it.hasNext()) {
            tmp += it.next().getPrice();
        }
        this.price = new SimpleIntegerProperty(tmp);
    }

    public Coffee(String name, int price) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleIntegerProperty(price);
    }

    public void setIsSpecial(Boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public Boolean getIsSpecial() {
        return this.isSpecial;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setName(String n) {
        this.name.set(n);
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty getPriceProperty() {
        return price;
    }

    public void setPrice(int c) {
        price.set(c);
    }

    public void calculatePrice() {
        Iterator<Ingredient> it = this.ingreList.iterator();
        int tmp = 0;
        while (it.hasNext()) {
            tmp += it.next().getPrice();
        }
        this.price = new SimpleIntegerProperty(tmp);
    }

    public ArrayList<Ingredient> getIngreList() {
        return this.ingreList;
    }

    public void setSalePrice(int m) {
        this.saleprice = new SimpleIntegerProperty(m);

    }

    public IntegerProperty getSaleProperty() {
        return saleprice;
    }

    public void swap_price() {
        IntegerProperty temp;
        temp = price;
        price = saleprice;
        saleprice = temp;
    }

}
