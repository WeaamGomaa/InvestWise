package main.java.investWise.assets.strategies;

import main.java.investWise.assets.Asset;

public class StockStrategy implements InvestmentStrategy {
    @Override
    public double calculateValue(Asset asset){
        System.out.println("Stocks calculating value....");
        double value = asset.quantity * asset.purchasePrice;
        return value;
    }

    @Override
    public boolean isHalal(Asset asset){
        System.out.println("Halal");
        return true;
    }
}
