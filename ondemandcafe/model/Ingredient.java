package ondemandcafe.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ingredient {

	private StringProperty name;
	private IntegerProperty price;
	
	public Ingredient() {
		this(null,'\0');//디폴트생성자
	}
	public Ingredient(String n,int p) {
		this.name=new SimpleStringProperty(n);
		this.price=new SimpleIntegerProperty(p);
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
	public void setPrice(int c) {
		price.set(c);
	}
	
}
