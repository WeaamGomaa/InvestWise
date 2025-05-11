package main.java.investWise.observers;

import main.java.investWise.assets.Asset;
import java.io.Serializable;

public interface Observer extends Serializable{
    public void update(Asset asset);
}
