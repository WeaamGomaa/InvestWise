package main.java.investWise;

import main.java.investWise.assets.Asset;
import main.java.investWise.assets.strategies.*;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

public class ExcelReportGenerator extends ReportGenerator {

    public ExcelReportGenerator(List<Asset> assets, String filename) {
        super(assets, filename);
    }

    @Override
    public String generateReport() {
        StringBuilder report = new StringBuilder();

        // Header
        report.append("===================== Investment Portfolio Report =====================\n");
        report.append("Generated on: ").append(LocalDate.now()).append("\n\n");

        // Table Header
        report.append(String.format("| %-20s | %-10s | %-8s | %-15s | %-15s | %-15s |\n",
                "Asset", "Type", "Qty", "Purchase Price", "Current Value", "Status"));
        report.append(createSeparator(82)).append("\n"); // Separator line

        double totalValue = 0;
        for (Asset asset : assets) {
            InvestmentStrategy strategy = getStrategy(asset.type);
            double assetValue = strategy.calculateValue(asset);
            boolean isHalal = strategy.isHalal(asset);
            totalValue += assetValue;
            report.append(String.format("| %-20s | %-10s | %-8d | $%-14.2f | $%-14.2f | %-15s |\n",
                    asset.Name,
                    asset.type,
                    asset.quantity,
                    asset.purchasePrice,
                    assetValue,
                    isHalal ? "Halal Certified" : "Review Needed"));
            report.append(createSeparator(82)).append("\n"); // Separator line after each row
        }

        // Total Value Row
        report.append(String.format("| %67s | $%-14.2f |\n", "Total Portfolio Value", totalValue));
        report.append(createSeparator(82)).append("\n");

        report.append("===========================================================================\n");

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(report.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage());
        }

        return "Excel report generated at: " + outputPath;
    }

    private String createSeparator(int length) {
        return "-".repeat(length);
    }
}
