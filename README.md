# InvestWise - Your Personal Islamic Investment Manager

## Overview

InvestWise is a Java-based application designed to help users manage their investment portfolios while adhering to Islamic finance principles. It provides tools for tracking assets, generating reports, and ensuring compliance with Sharia guidelines.

## Features

* **User Management:** Securely register, authenticate, and manage user accounts.
* **Asset Management:** Add, edit, and remove various asset types (stocks, real estate, gold).
* **Portfolio Tracking:** View and manage your investment portfolio.
* **Halal Compliance:** Determine the Sharia compliance of your investments.
* **Report Generation:** Generate Excel and PDF reports of your portfolio.
* **Account Linking:** Link bank and stock market accounts.
* **OTP Verification:** Securely verify account linking with OTP.
* **Financial Goal Management:** (Future) Define and track financial goals.
* **Risk & Allocation:** (Future) Assess investment risk and optimize asset allocation.
* **Zakat Calculation:** (Future) Calculate Zakat on eligible assets.
* **Data Persistence:** Save and load user data.
* **Update History:** Track changes to assets.

## Architecture

The application is structured using object-oriented principles and incorporates several design patterns. Here's a breakdown of the main components:

### Core Classes

* **`Main`**: The entry point of the application.  It initializes and runs the `MenuHandler`.
* **`MenuHandler`**:  Handles user interaction through a menu-driven interface.  It orchestrates the flow of the application, including login, signup, and access to various features.
* **`User`**: Represents a user of the application, storing personal details, accounts, and assets.
* **`UserService`**: Manages user data, including registration, authentication, and data persistence.
* **`Account`**: Represents a financial account (bank or stock market) linked to a user.
* **`AccountManager`**: Manages the linking and verification of user accounts.

### Asset Management

* **`Asset`**: Represents a generic asset with properties like name, type, quantity, purchase price, and purchase date.  It also maintains an update history and supports the Observer pattern.
* **`AssetManager`**: Manages a collection of `Asset` objects for a user.  It provides functionality to add, edit, remove, and retrieve assets, and applies the selected `InvestmentStrategy`.
* **`InvestmentStrategy`**: An interface defining the strategy for calculating asset value and determining Halal compliance.
* **`StockStrategy`**, **`RealEstateStrategy`**, **`GoldStrategy`**: Concrete implementations of `InvestmentStrategy` for different asset types.

### Reporting

* **`ReportGenerator`**: An abstract class defining the common structure for report generation.
* **`ExcelReportGenerator`**: Generates portfolio reports in Excel-like format (plain text).
* **`PDFReportGenerator`**: Generates portfolio reports in PDF-like format (plain text).
* **`PortfolioService`**: Handles saving and loading user portfolio data.

### Observers

* **`Observer`**:  The interface for objects that want to be notified of asset changes.
* **`Dashboard`**:  An observer that displays updates when an asset is modified.

## Design Patterns in InvestWise

The InvestWise application leverages several design patterns to promote flexibility, maintainability, and extensibility:

* **Strategy Pattern**:
    * The `InvestmentStrategy` interface (implemented by `StockStrategy`, `GoldStrategy`, `RealEstateStrategy`) is used to dynamically switch valuation and Halal compliance rules based on different asset types (stocks, real estate, gold).
    * **Benefit**: This pattern decouples asset-specific logic from the `AssetManager`. Adding support for new asset types becomes straightforward without requiring modifications to the core `AssetManager` code.
    * **Classes Involved**: `AssetManager`, `InvestmentStrategy`, `StockStrategy`, `GoldStrategy`, `RealEstateStrategy`, `Asset`.

* **Observer Pattern**:
    * The `Asset` class acts as the subject and notifies `Dashboard` (an Observer) of changes through the `notifyObserver()` method.
    * This mechanism allows the UI (represented by `Dashboard`) to automatically update when asset data is modified (e.g., after editing an asset's properties).
    * **Benefit**: This pattern ensures that the UI remains synchronized with the underlying data without creating tight dependencies between the UI components and the business logic.
    * **Classes Involved**: `Asset`, `Observer`, `Dashboard`.

* **Template Method Pattern**:
    * The abstract `ReportGenerator` class defines a template for generating reports. This template outlines the standard steps involved: fetching data, formatting the report content, and exporting it to a specific format.
    * Concrete subclasses like `PDFReportGenerator` and `ExcelReportGenerator` implement the format-specific variations of these steps (e.g., how the data is formatted into a PDF-like or Excel-like structure, and how it's "exported" to a text file in this implementation).
    * **Benefit**: This pattern ensures consistency in the report generation process across different output formats while allowing subclasses to customize the formatting and export details.
    * **Classes Involved**: `ReportGenerator`, `PDFReportGenerator`, `ExcelReportGenerator`.

## How to Run

1.  **Prerequisites:** Ensure you have Java Development Kit (JDK) installed (version 8 or later).
2.  **Compilation:** Compile the Java source files using a Java compiler (e.g., `javac`).  You'll need to compile all the files.
3.  **Execution:** Run the `Main` class.
4.  **Interaction:** The application will present a menu-driven interface.  Follow the prompts to register, login, manage assets, and generate reports.

