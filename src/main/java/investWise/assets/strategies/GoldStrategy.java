package main.java.investWise.assets.strategies;

import main.java.investWise.assets.Asset;

public class GoldStrategy implements InvestmentStrategy {
    @Override
    public double calculateValue(Asset asset){
        System.out.println("Gold calculating value....");
        return asset.quantity * asset.purchasePrice;
    }

    @Override
    public boolean isHalal(Asset asset){
        System.out.println("Halal");
        return true;
    }
}
