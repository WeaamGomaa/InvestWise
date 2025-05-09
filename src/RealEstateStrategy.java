public class RealEstateStrategy implements InvestmentStrategy{
    @Override
    public double calculateValue(Asset asset){
        System.out.println("Real Estate calculating value....");
    }

    @Override
    public boolean isHalal(Asset asset){
        System.out.println("Halal");
        return true;
    }
}
