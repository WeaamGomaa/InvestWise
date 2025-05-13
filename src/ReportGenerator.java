package main.java.investWise;

import main.java.investWise.assets.*;
import main.java.investWise.assets.strategies.InvestmentStrategy;
import main.java.investWise.assets.strategies.RealEstateStrategy;
import main.java.investWise.assets.strategies.StockStrategy;
import main.java.investWise.assets.strategies.GoldStrategy;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public abstract class ReportGenerator {
    List<Asset> assets;
    String outputPath;
    public ReportGenerator(List<Asset> assets, String filename) {
        this.assets = assets;
        this.outputPath = Paths.get(filename + ".txt").toString(); // Default to .txt extension
    }

    InvestmentStrategy getStrategy(String type) {
        String lowerType = type.toLowerCase();
        if ("real estate".equals(lowerType)) return new RealEstateStrategy();
        if ("stocks".equals(lowerType)) return new StockStrategy();
        if ("gold".equals(lowerType)) return new GoldStrategy();
        throw new IllegalArgumentException("Unknown asset type: " + type);
    }

    abstract String generateReport();

    public static void showReportMenu(AssetManager manager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Report Generation ===");
        System.out.println("1. Excel Report");
        System.out.println("2. PDF Report");
        System.out.println("3. Back to Main Menu");
        System.out.print("Choose option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 3) return;

        System.out.print("Enter output file name (without extension): ");
        String filename = scanner.nextLine();

        ReportGenerator generator = (choice == 1)
                ? new ExcelReportGenerator(manager.getAssets(), filename)
                : new PDFReportGenerator(manager.getAssets(), filename);

        try {
            String result = generator.generateReport();
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
