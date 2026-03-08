# Accessories Shop Stock Management System

**Module:** UFCFC3-30-1 Introduction to Object-Oriented Systems Development  
**Assessment:** Project Portfolio (50% of total module mark)  
**Package:** `Assignment`  
**IDE:** NetBeans (OOSD1 project)

---

## Overview

A Java-based stock management system for a car parts and accessories shop, built across three development steps. The system demonstrates core object-oriented principles — **encapsulation**, **inheritance**, **method overriding**, **polymorphism**, and **dynamic method binding** — and includes a fully functional Swing GUI.

---

## Project Structure

```
Assignment_UFCFC3-30-1/
│
├── src/
│   └── Assignment/
│       ├── StockItem.java            # Step 1 — Base class
│       ├── NavSys.java               # Step 2 — Navigation system subclass
│       ├── Tyre.java                 # Step 3 — Invented subclass
│       ├── CarBattery.java           # Step 3 — Invented subclass
│       ├── OilFilter.java            # Step 3 — Invented subclass
│       ├── TestStockItem.java        # Step 1 — Test class
│       ├── TestNavSys.java           # Step 2 — Test class
│       ├── TestPolymorphism.java     # Step 3 — Polymorphism demo
│       └── ShopGUI.java              # GUI application (main entry point)
│
├── docs/
│   ├── UML/
│   │   ├── class_diagrams/
│   │   │   ├── Step1_StockItem_UML.png
│   │   │   ├── Step2_NavSys_UML.png
│   │   │   ├── Step3_All_Subclasses_UML.png
│   │   │   ├── ShopGUI_Optional_UML.png
│   │   │   ├── Full_Assignment_UML.png
│   │   │   └── UML_Diagrahm.png
│   │   └── sequence_diagrams/
│   │       ├── sequence_create_stock_item.png
│   │       ├── sequence_stock_operations.png
│   │       ├── sequence_navsys_testing.png
│   │       ├── sequence_polymorphism.png
│   │       └── sequence_dynamic_binding.png
│   └── test_cases/
│       └── OOSD1_UML_and_Test_Cases.pdf
│
├── .gitignore
└── README.md
```

---

## Class Descriptions

### `StockItem` — Step 1 (Base Class)

Represents any generic stock item sold by the shop.

| Member | Type | Description |
|---|---|---|
| `MAX_STOCK` | `public static final int` | Maximum allowed stock (100) |
| `STANDARD_VAT_PERCENT` | `public static final double` | Standard VAT rate (17.5) |
| `stockCode` | `private final String` | Fixed stock code |
| `quantityInStock` | `private int` | Current stock level |
| `priceWithoutVAT` | `private double` | Price per unit before VAT |
| `StockItem(code, qty, price)` | Constructor | Creates item; validates all inputs |
| `getStockName()` | Method | Returns `"Unknown Stock Name"` |
| `getStockDescription()` | Method | Returns `"Unknown Stock Description"` |
| `addStock(amount)` | Method | Increases stock; prints error if `< 1` or `> MAX_STOCK` |
| `sellStock(amount)` | Method | Reduces stock; returns `true`/`false` |
| `getVAT()` | Method | Returns `STANDARD_VAT_PERCENT` |
| `getPriceWithVAT()` | Method | Returns price including VAT |
| `toString()` | Method | Returns formatted item details |

### `NavSys` — Step 2 (Subclass of StockItem)

Represents a GeoVision Sat Nav navigation system.

- Constructor calls `super(stockCode, quantity, price)`
- Overrides `getStockName()` → `"Navigation system"`
- Overrides `getStockDescription()` → `" GeoVision Sat Nav"`
- Overrides `toString()` using `super.toString()`

### `Tyre` — Step 3 (Invented Subclass)

Represents a tyre. Extra fields: `size` (e.g. `205/55R16`), `season` (e.g. `All-season`).

### `CarBattery` — Step 3 (Invented Subclass)

Represents a car battery. Extra fields: `voltage` (e.g. `12`), `capacityAh` (e.g. `60`).

### `OilFilter` — Step 3 (Invented Subclass)

Represents an oil filter. Extra fields: `filterType` (e.g. `Spin-on`), `compatibleModel` (e.g. `Ford Fiesta 1.0 EcoBoost`).

---

## Setup Instructions

### Prerequisites

- Java JDK 17 or later
- NetBeans IDE (recommended) or any Java IDE

### Running in NetBeans

1. Clone or download this repository
2. Open **NetBeans** → File → Open Project → select `OOSD1`
3. Ensure all `.java` files are in the `Assignment` package under `Source Packages`
4. Right-click `ShopGUI.java` → **Run File** to launch the GUI
5. Right-click `TestStockItem.java`, `TestNavSys.java`, or `TestPolymorphism.java` → **Run File** to run the console test classes

### Running from Terminal

```bash
# Compile all files
javac -d out src/Assignment/*.java

# Run the GUI
java -cp out Assignment.ShopGUI

# Run test classes
java -cp out Assignment.TestStockItem
java -cp out Assignment.TestNavSys
java -cp out Assignment.TestPolymorphism
```

---

## GUI Usage Guide

### Creating a Stock Item
1. Select item **Type** from the dropdown (StockItem, NavSys, Tyre, CarBattery, OilFilter)
2. Enter **Code**, **Quantity**, and **Price (no VAT)**
3. For Tyre, CarBattery, OilFilter — fill in **Extra 1** and **Extra 2** fields
4. Click **Create & Add** — the item appears in the stock list

### Stock Operations
1. Click an item in the **Stock List** to select it
2. **Add Stock:** Enter amount → click **Add Stock**
3. **Sell Stock:** Enter amount → click **Sell Stock**
4. **Change Price:** Enter new price → click **Change Price**
5. **Show Details:** Click **Show Details** to display full item information

### Error Handling
- Blank stock codes → error dialog
- Duplicate stock codes → error dialog
- Amount less than 1 → error message in output panel
- Stock exceeding MAX_STOCK (100) → error message in output panel
- Negative price → error message in output panel
- Missing extra fields for subclasses → error dialog

---

## Sample Console Output

### TestStockItem
```
Printing stock item information
Stock Type: Unknown Stock Name
Description: Unknown Stock Description
Stock Code: W101
Price Without VAT: 99.99
Price With VAT: 117.48825
Total unit in stock: 10

Printing stock item information
...
Total unit in stock: 20

Printing stock item information
...
Total unit in stock: 18

The error was: Increased item must be greater than or equal to one
The error was: Sell amount must be greater than or equal to one
The error was: Price must be greater than or equal to 0
The error was: Stock cannot exceed 100
```

### TestNavSys
```
Printing stock item information
Stock Type: Navigation system
Description:  GeoVision Sat Nav
Stock Code: NS101
Price Without VAT: 99.99
Price With VAT: 117.48825
Total unit in stock: 10
...
Price Without VAT: 100.99
Price With VAT: 118.66324999999999
Total unit in stock: 18

The error was: Increased item must be greater than or equal to one
```

---

## OO Concepts Demonstrated

| Concept | Implementation |
|---|---|
| **Encapsulation** | All fields are `private`; accessed only via public getters/setters |
| **Inheritance** | `NavSys`, `Tyre`, `CarBattery`, `OilFilter` all `extend StockItem` |
| **Method Overriding** | Each subclass overrides `getStockName()`, `getStockDescription()`, `toString()` |
| **`super` keyword** | Subclass constructors call `super(stockCode, quantity, price)`; `toString()` calls `super.toString()` |
| **Polymorphism** | `StockItem[] items` array holds all subclass instances in `TestPolymorphism` |
| **Dynamic Method Binding** | `toString()` on a `StockItem` reference resolves to the correct subclass method at runtime |
| **Constants** | `MAX_STOCK` and `STANDARD_VAT_PERCENT` as `public static final` |

---

## UML Diagrams

All UML diagrams are located in `docs/UML/`:

### Class Diagrams (in `class_diagrams/`)
- `Step1_StockItem_UML.png` — StockItem base class (Step 1)
- `Step2_NavSys_UML.png` — NavSys added with inheritance arrow (Step 2)
- `Step3_All_Subclasses_UML.png` — All four subclasses (Step 3)
- `Full_Assignment_UML.png` — Complete system including ShopGUI and test classes

### Sequence Diagrams (in `sequence_diagrams/`)
- `sequence_create_stock_item.png` — GUI create item flow
- `sequence_stock_operations.png` — Add/sell/change price via GUI
- `sequence_navsys_testing.png` — TestNavSys execution flow
- `sequence_polymorphism.png` — TestPolymorphism loop execution
- `sequence_dynamic_binding.png` — Dynamic dispatch of getStockName()

---

## Test Cases

A full test strategy and **14 documented test cases** (satisfying the requirement of more than 10 and fewer than 15) are documented in:

`docs/test_cases/OOSD1_UML_and_Test_Cases.pdf`

Test coverage includes:

| ID | Type | Feature |
|---|---|---|
| TC01 | Class Test | StockItem constructor — valid input |
| TC02 | Class Test | Constructor validation — invalid input |
| TC03 | Class Test | VAT calculation |
| TC04 | Class Test | addStock() — normal case |
| TC05 | Class Test | sellStock() — normal case |
| TC06 | Class Test | Boundary conditions |
| TC07 | Class Test | setQuantityInStock() — valid and invalid |
| TC08 | Class Test | toString() — formatted output |
| TC09 | Class Test | NavSys inheritance and overriding |
| TC10 | Class Test | Invented subclasses (Tyre, CarBattery, OilFilter) |
| TC11 | Class Test | Polymorphism and dynamic binding |
| TC12 | GUI Test | Create item via GUI |
| TC13 | GUI Test | Stock operations via GUI |
| TC14 | GUI Test | GUI validation and error handling |

---

## Author

UWE Bristol — UFCFC3-30-1 Introduction to OO Systems Development, 2025–26
