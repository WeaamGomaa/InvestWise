package main.java.investWise.assets;

import java.io.Serializable;
import main.java.investWise.observers.Observer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Asset implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Observer> observers = new ArrayList<>();
    public String Name;
    public String type;
    public double value;
    public int quantity;
    public double purchasePrice;
    public LocalDate purchaseDate;

    public Asset(String Name, String type){
        this.Name = Name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Asset Name: " + Name
                + " | Type: " + type
                + " | Value: $" + value
                + " | Quantity: " + quantity
                + " | Purchase Price: " + purchasePrice
                + " | Purchase Date: " + purchaseDate;

    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setCurrentValue(double value){
        this.value = value;
    }

    public void addObserver(Observer observer){
        this.observers.add(observer);
    }
    public void notifyObserver(){
        if (observers != null) {  // Safety check
            for (Observer observer : observers) {
                if (observer != null) {  // Critical!
                    observer.update(this);
                }
            }
        }
    }
}
