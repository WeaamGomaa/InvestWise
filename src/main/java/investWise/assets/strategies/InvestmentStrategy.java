package main.java.investWise.assets.strategies;

import main.java.investWise.assets.Asset;
import java.io.Serializable;

public interface InvestmentStrategy extends Serializable {
    double calculateValue(Asset asset);
    boolean isHalal(Asset asset);
}
