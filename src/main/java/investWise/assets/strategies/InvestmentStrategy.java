package main.java.investWise.assets.strategies;

import main.java.investWise.assets.Asset;

public interface InvestmentStrategy {
    double calculateValue(Asset asset);
    boolean isHalal(Asset asset);
}
