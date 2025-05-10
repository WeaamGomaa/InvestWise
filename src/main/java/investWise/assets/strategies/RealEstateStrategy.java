package main.java.investWise.assets.strategies;

import main.java.investWise.assets.Asset;

public class RealEstateStrategy implements InvestmentStrategy {
    @Override
    public double calculateValue(Asset asset){
        System.out.println("Real Estate calculating value....");
        return asset.quantity * asset.purchasePrice;
    }

    @Override
    public boolean isHalal(Asset asset){
        System.out.println("Halal");
        return true;
    }
}
