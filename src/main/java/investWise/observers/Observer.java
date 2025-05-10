package main.java.investWise.observers;

import main.java.investWise.assets.Asset;

public interface Observer {
    public void update(Asset asset);
}
