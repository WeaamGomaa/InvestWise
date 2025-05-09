public class StockStrategy implements InvestmentStrategy{
    @Override
    public double calculateValue(Asset asset){
        System.out.println("Stocks calculating value....");
    }

    @Override
    public boolean isHalal(Asset asset){
        System.out.println("Halal");
        return true;
    }
}
