public class Dashboard implements Observer{
    protected Asset asset;

//    public Dashboard(Asset asset){
//        this.asset = asset;
//        this.asset.addObserver(this);
//    }
    @Override
    public void update(Asset asset){
        System.out.println("[UPDATE] " + asset.toString());
    }
}
