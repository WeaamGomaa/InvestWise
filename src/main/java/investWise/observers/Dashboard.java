package main.java.investWise.observers;

import main.java.investWise.assets.Asset;
import java.io.Serializable;

public class Dashboard implements Observer,  Serializable {
    //protected Asset asset;

//    public main.java.investWise.observers.Dashboard(main.java.investWise.assets.Asset asset){
//        this.asset = asset;
//        this.asset.addObserver(this);
//    }
    private static final long serialVersionUID = 1L;
    @Override
    public void update(Asset asset){
        System.out.println("UPDATE " + asset.toString());
    }
}
