package main.java.investWise.assets;

import java.io.Serializable;
import main.java.investWise.observers.Observer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Asset implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Observer> observers = new ArrayList<>();
    private List<String> updateHistory = new ArrayList<>();
    public String Name;
    public String type;
    public double value;
    public int quantity;
    public double purchasePrice;
    public LocalDate purchaseDate;

    public Asset(String Name, String type){
        this.Name = Name;
        this.type = type;
        recordCreation();
    }

    private void recordCreation() {
        String creationEntry = String.format("[%s] ASSET CREATED: %s (%s)",
                LocalDateTime.now(),
                Name,
                type);
        updateHistory.add(creationEntry);
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

    public void setName(String newName){
        if (!this.Name.equals(newName)){
            recordChange("Name", this.Name, newName);
            this.Name = newName;
        }
    }

    public void setQuantity(int newQuantity) {
        if(newQuantity != this.quantity){
            recordChange("Quantity", this.quantity, newQuantity);
            this.quantity = newQuantity;
        }
    }

    public void setPurchaseDate(LocalDate newDate) {
        if(!newDate.equals(this.purchaseDate)){
            recordChange("Date", this.purchaseDate, newDate);
            this.purchaseDate = newDate;
        }
    }

    public void setPurchasePrice(double newPrice) {
        if(newPrice != this.purchasePrice){
            recordChange("Price", this.purchasePrice, newPrice);
            this.purchasePrice = newPrice;
        }
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

    // Record a change
    public void recordChange(String fieldChanged, Object oldValue, Object newValue) {
        String entry = String.format("[%s] %s CHANGED: %s -> %s",
                LocalDateTime.now(),
                fieldChanged.toUpperCase(),
                oldValue,
                newValue);
        updateHistory.add(entry);
    }

    // Get all changes
    public List<String> getUpdateHistory() {
        return updateHistory == null ? new ArrayList<>() : new ArrayList<>(updateHistory);
    }
}
