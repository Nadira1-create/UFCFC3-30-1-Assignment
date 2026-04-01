# Accessories Shop — Stock Management System

**Module:** UFCFC3-30-1 Introduction to Object-Oriented Systems Development  
**Assessment:** Portfolio Project — 50% of total module mark  
**Author:** Nadira Robleh  
**Package:** `Assignment`

---

## Project Overview

This project is a Java object-oriented application for a car parts and accessories shop. It allows shop staff to manage stock items including Navigation Systems, Tyres, Car Batteries, and Oil Filters through both a console interface and a fully interactive Swing GUI.

The system is built across three steps as required by the assessment brief:

- **Step 1** — Design and implement the `StockItem` base class
- **Step 2** — Design and implement the `NavSys` subclass demonstrating inheritance
- **Step 3** — Design and implement three more subclasses (`Tyre`, `CarBattery`, `OilFilter`), demonstrate polymorphism and dynamic method binding, and deliver a GUI-based application

---

## Object-Oriented Concepts Demonstrated

| Concept | How it appears in this project |
|---|---|
| **Encapsulation** | All instance variables are `private`; access is through getters and setters only |
| **Inheritance** | `NavSys`, `Tyre`, `CarBattery`, and `OilFilter` all extend `StockItem` using `extends` |
| **Method overriding** | Every subclass overrides `getStockName()`, `getStockDescription()`, and `toString()` |
| **`super` keyword** | Subclass constructors call `super(stockCode, quantity, price)`; `toString()` calls `super.toString()` |
| **Polymorphism** | A `StockItem[]` array in `TestPolymorphism` holds instances of all four different subclasses |
| **Dynamic method binding** | The JVM resolves the correct subclass override of `toString()` at runtime, not at compile time |
| **Constants** | `MAX_STOCK = 100` and `STANDARD_VAT_PERCENT = 17.5` are `public static final` fields on `StockItem` |
| **Defensive validation** | `null` strings are replaced with `""` and trimmed; invalid constructor arguments throw `IllegalArgumentException` |

---

## Project Structure

```
Assignment/
├── StockItem.java            Base class (Step 1)
├── NavSys.java               Navigation system subclass (Step 2)
├── Tyre.java                 Tyre subclass (Step 3)
├── CarBattery.java           Car battery subclass (Step 3)
├── OilFilter.java            Oil filter subclass (Step 3)
├── ShopGUI.java              Swing GUI application (Step 3)
├── TestStockItem.java        Console test for Step 1
├── TestNavSys.java           Console test for Step 2
└── TestPolymorphism.java     Polymorphism demonstration for Step 3
```

---

## Class Hierarchy

```
StockItem
├── NavSys
├── Tyre
├── CarBattery
└── OilFilter
```

`StockItem` is the root of the hierarchy. It defines all shared state and behaviour. Every subclass inherits stock code, quantity, price, VAT calculation, `addStock()`, and `sellStock()` from `StockItem`, and overrides the name, description, and `toString()` methods to provide product-specific output.

---

## Class Reference

### `StockItem`

The base class representing any item stocked by the shop.

**Instance variables**

| Variable | Type | Description |
|---|---|---|
| `stockCode` | `String` (final) | Fixed unique identifier — cannot change after construction |
| `quantityInStock` | `int` | Current number of units in stock |
| `priceWithoutVAT` | `double` | Price per unit before VAT is applied |

**Constants**

| Constant | Value | Description |
|---|---|---|
| `MAX_STOCK` | `100` | Maximum units allowed in stock at any time |
| `STANDARD_VAT_PERCENT` | `17.5` | VAT rate used for all price calculations |

**Key methods**

| Method | Returns | Description |
|---|---|---|
| `getStockName()` | `"Unknown Stock Name"` | Overridden by all subclasses |
| `getStockDescription()` | `"Unknown Stock Description"` | Overridden by all subclasses |
| `addStock(int amount)` | `boolean` | Increases stock; returns `false` and prints error if `amount < 1` or total exceeds `MAX_STOCK` |
| `sellStock(int amount)` | `boolean` | Reduces stock and returns `true` if successful; returns `false` if `amount < 1` or exceeds current stock |
| `getPriceWithVAT()` | `double` | Calculates `priceWithoutVAT × (1 + 17.5 / 100)` |
| `getVAT()` | `double` | Returns `17.5` |
| `toString()` | `String` | Returns a 6-line formatted summary using all the methods above |

---

### `NavSys` — Navigation System

Extends `StockItem`. No additional instance variables.

| Method | Returns |
|---|---|
| `getStockName()` | `"Navigation system"` |
| `getStockDescription()` | `" GeoVision Sat Nav"` |
| `toString()` | Delegates entirely to `super.toString()` |

---

### `Tyre`

Extends `StockItem`. Adds `size` (String) and `season` (String).

| Extra variable | Example value |
|---|---|
| `size` | `"205/55R16"` |
| `season` | `"All-season"` |

`getStockDescription()` returns e.g. `"Tyre 205/55R16 (All-season)"`  
`toString()` appends `Tyre Size` and `Season` fields after `super.toString()`

---

### `CarBattery`

Extends `StockItem`. Adds `voltage` (int) and `capacityAh` (int).

| Extra variable | Example value |
|---|---|
| `voltage` | `12` |
| `capacityAh` | `60` |

`getStockDescription()` returns e.g. `"12V 60Ah Battery"`  
`toString()` appends `Voltage` and `Capacity` fields after `super.toString()`

---

### `OilFilter`

Extends `StockItem`. Adds `filterType` (String) and `compatibleModel` (String).

| Extra variable | Example value |
|---|---|
| `filterType` | `"Spin-on"` |
| `compatibleModel` | `"Ford Fiesta 1.0 EcoBoost"` |

`getStockDescription()` returns e.g. `"Spin-on filter for Ford Fiesta 1.0 EcoBoost"`  
`toString()` appends `Filter Type` and `Compatible Model` fields after `super.toString()`

---

## Prerequisites

| Requirement | Version |
|---|---|
| Java Development Kit (JDK) | 17 or later |
| IDE (optional) | IntelliJ IDEA, Eclipse, VS Code with Java extension |

No external libraries or build tools are required. The only dependencies are the Java standard library (`javax.swing`, `java.awt`, `java.util.Scanner`).

---

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name
```

### 2. Compile all source files

Run this command from the root of the repository — the folder that **contains** the `Assignment/` directory:

```bash
javac Assignment/*.java
```

This compiles all nine `.java` files in one step. No errors should appear.

---

## Running the Application

### Launch the GUI

```bash
java Assignment.ShopGUI
```

This opens the graphical stock management window. This is the recommended way to interact with the system and is the primary deliverable for Step 3.

---

## Running the Test Classes

Each of the three test classes has its own `main` method and runs independently from the command line.

### Step 1 — StockItem test

```bash
java Assignment.TestStockItem
```

This runs through all tasks from the Step 1 sample run in the brief:

- Task 1: Creates a `StockItem` with code `W101`, quantity `10`, price `99.99` and prints details
- Task 2: Adds `10` units — quantity becomes `20`
- Task 3: Sells `2` units — quantity becomes `18`
- Task 4: Changes price to `100.99` — VAT price recalculates automatically
- Error tests: `addStock(0)`, `sellStock(0)`, `setPriceWithoutVAT(-5.00)`, `addStock(100)` all print the required error messages

---

### Step 2 — NavSys test

```bash
java Assignment.TestNavSys
```

This runs through all tasks from the Step 2 sample run in the brief:

- Task 1: Creates `NavSys` with code `NS101`, quantity `10`, price `99.99`
- Task 2: Adds `10` units
- Task 3: Sells `2` units
- Task 4: Changes price to `100.99`
- Task 5: Calls `addStock(0)` — prints the required error message

---

### Step 3 — Polymorphism demo

```bash
java Assignment.TestPolymorphism
```

This test is **interactive**. It creates one instance of each subclass, stores all four in a `StockItem[]` array, and calls `itemInstance(StockItem s)` on each in a loop. For each item you will be prompted to enter:

1. Number of units to add
2. Number of units to sell
3. New price (excluding VAT)

The correct `toString()` output for each subclass is produced through dynamic method binding — the JVM selects the right override at runtime based on the actual object type, not the `StockItem` reference type.

**Suggested inputs to match the brief sample run:**

| Item | Code | Add | Sell | New Price |
|---|---|---|---|---|
| NavSys | NS101 | 10 | 2 | 100.99 |
| Tyre | T101 | 5 | 3 | 64.99 |
| CarBattery | B101 | 10 | 1 | 94.99 |
| OilFilter | OF101 | 20 | 5 | 10.99 |

---

## Usage Guide — GUI (ShopGUI)

### Left panel — Stock List

Displays every created item in a scrollable list. Each row shows:

```
[CODE] Item Name  |  Qty: N  |  £price
```

Click any item to select it. The full `toString()` output appears in the output panel immediately. Use **Show Details** to print it again at any time. Use **Clear Output** to reset the log.

---

### Right panel — Create Item

| Field | What to enter |
|---|---|
| Type | Choose from the dropdown: `StockItem`, `NavSys`, `Tyre`, `CarBattery`, `OilFilter` |
| Code | A unique stock code, e.g. `NS101`. Duplicate codes are rejected. |
| Quantity | A whole number between `0` and `100` |
| Price (no VAT) | A price per unit, e.g. `99.99`. Must be `0` or greater. |
| Extra 1 | Tyre → tyre size e.g. `205/55R16` · CarBattery → voltage e.g. `12` · OilFilter → filter type e.g. `Spin-on` |
| Extra 2 | Tyre → season e.g. `All-season` · CarBattery → capacity e.g. `60` · OilFilter → compatible model e.g. `Ford Fiesta 1.0 EcoBoost` |

Extra fields are disabled and greyed out automatically when they are not needed (e.g. for `StockItem` and `NavSys`).

Click **Create & Add** to create the item. The form clears automatically on success, ready for the next entry.

---

### Right panel — Operations

Select an item from the list before using any of these buttons.

| Button | Input required | What it does |
|---|---|---|
| **Add Stock** | Amount field | Increases stock by the entered number |
| **Sell Stock** | Amount field | Decreases stock by the entered number |
| **Change Price** | New Price field | Updates the ex-VAT price; the inc-VAT price recalculates automatically |
| **Refresh Details** | None | Re-prints the selected item's current state in the output panel |

All validation errors (invalid amounts, duplicate codes, negative prices, exceeding the stock cap) are shown in the output panel. Nothing fails silently.

---

## Sample Console Output

### TestStockItem (Step 1)

```
Printing stock item information
Stock Type: Unknown Stock Name
Description: Unknown Stock Description
Stock Code: W101
Price Without VAT: 99.99
Price With VAT: 117.48825
Total unit in stock: 10

Printing stock item information
Stock Type: Unknown Stock Name
Description: Unknown Stock Description
Stock Code: W101
Price Without VAT: 99.99
Price With VAT: 117.48825
Total unit in stock: 20

The error was: Increased item must be greater than or equal to one
The error was: Sell amount must be greater than or equal to one
The error was: Price must be greater than or equal to 0
The error was: Stock cannot exceed 100
```

### TestNavSys (Step 2)

```
Printing stock item information
Stock Type: Navigation system
Description:  GeoVision Sat Nav
Stock Code: NS101
Price Without VAT: 99.99
Price With VAT: 117.48825
Total unit in stock: 10

The error was: Increased item must be greater than or equal to one
```

---

## Validation Rules

The following rules are enforced by `StockItem` and the GUI, as specified in the assessment brief.

| Operation | Condition that is rejected | What happens |
|---|---|---|
| `addStock(n)` | `n < 1` | Error printed to console; stock unchanged; returns `false` |
| `addStock(n)` | `current stock + n > 100` | Error printed to console; stock unchanged; returns `false` |
| `sellStock(n)` | `n < 1` | Error printed to console; stock unchanged; returns `false` |
| `sellStock(n)` | `n > current stock` | No error printed; stock unchanged; returns `false` |
| `setPriceWithoutVAT(p)` | `p < 0` | Error printed to console; price unchanged |
| Constructor | `stockCode` is null or blank | `IllegalArgumentException` thrown |
| Constructor | `quantity < 0` or `quantity > 100` | `IllegalArgumentException` thrown |
| Constructor | `price < 0` | `IllegalArgumentException` thrown |
| GUI: Create | Duplicate stock code | Error dialog displayed; item not added |
| GUI: Create | Extra fields blank for `Tyre`, `CarBattery`, or `OilFilter` | Error dialog displayed; item not created |

---

## Deliverables

| Deliverable | Location |
|---|---|
| Java source code | `Assignment/` folder in this repository |
| UML class diagrams (Steps 1, 2, and 3) | Submitted separately via Blackboard |
| Test strategy and test case table (14 cases) | Submitted separately via Blackboard |
| 5-minute video demonstration | Submitted separately via Blackboard |

---

## Author

**Nadira Robleh**  
University of the West of England, Bristol  
Module: UFCFC3-30-1 Introduction to Object-Oriented Systems Development  
Academic Year: 2025–26
