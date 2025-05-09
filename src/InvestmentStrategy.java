public interface InvestmentStrategy {
    double calculateValue(Asset asset);
    boolean isHalal(Asset asset);
}
