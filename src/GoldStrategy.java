public class GoldStrategy implements InvestmentStrategy{
    @Override
    public double calculateValue(Asset asset){
        System.out.println("Gold calculating value....");
    }

    @Override
    public boolean isHalal(Asset asset){
        System.out.println("Halal");
        return true;
    }
}
