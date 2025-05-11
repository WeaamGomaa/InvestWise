package main.java.investWise.assets.strategies;

import main.java.investWise.assets.Asset;
import java.io.Serializable;


public class StockStrategy implements InvestmentStrategy, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public double calculateValue(Asset asset){
        System.out.println("Stocks calculating value....");
        return asset.quantity * asset.purchasePrice;
    }

    @Override
    public boolean isHalal(Asset asset){
        System.out.println("Halal");
        return true;
    }
}
