package cafe.model;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ingredient {

	private StringProperty name;
	private IntegerProperty price;
	private IntegerProperty saleprice;
	
	public Ingredient() {
		this(null,0); // 주석 UTF8로 다시 적어주세용
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
	public IntegerProperty getPriceProperty() {
		return price;
	}
	public void setPrice(int c) {
		price.set(c);
	}
	public void setSalePrice(int m) {
		this.saleprice=new SimpleIntegerProperty(m);
		
	}
	public IntegerProperty getSaleProperty() {
		return saleprice;
	}
	public int getSalePrice() {
		return saleprice.get();
	}
	public void swap_price() {
		IntegerProperty temp;
		temp=price;
		price=saleprice;
		saleprice=temp;
	}
	
    

}
