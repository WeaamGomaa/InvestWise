package main.java.investWise.observers;

import main.java.investWise.assets.Asset;

public class Dashboard implements Observer {
    protected Asset asset;

//    public main.java.investWise.observers.Dashboard(main.java.investWise.assets.Asset asset){
//        this.asset = asset;
//        this.asset.addObserver(this);
//    }
    @Override
    public void update(Asset asset){
        System.out.println("[UPDATE] " + asset.toString());
    }
}
