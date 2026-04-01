package Assignment;

import java.util.Scanner;

/**
 * TestPolymorphism - A test class demonstrating polymorphism and dynamic method
 * binding across all {@link StockItem} subclasses (Step 3).
 *
 * <p>This class fulfils the Step 3 requirement of the project brief by
 * implementing the mandatory {@link #itemInstance(StockItem)} class method and
 * a {@link #main(String[])} method that exercises all four subclasses through
 * a shared {@link StockItem} array. This is the canonical demonstration of
 * polymorphism in this project: a single method signature operates correctly
 * on any subclass instance without modification.</p>
 *
 * <h2>Object-Oriented concepts demonstrated:</h2>
 * <ul>
 *   <li><b>Polymorphism</b> — a single {@code StockItem[]} array holds instances
 *       of four different subclasses ({@link NavSys}, {@link Tyre},
 *       {@link CarBattery}, {@link OilFilter}), each treated uniformly through
 *       the common superclass reference.</li>
 *   <li><b>Dynamic method binding</b> — when {@link StockItem#toString()},
 *       {@link StockItem#getStockName()}, and
 *       {@link StockItem#getStockDescription()} are called inside
 *       {@link #itemInstance(StockItem)}, the JVM resolves the correct
 *       subclass override at runtime, not at compile time.</li>
 *   <li><b>Inheritance</b> — all stock management operations ({@code addStock},
 *       {@code sellStock}, {@code setPriceWithoutVAT}) are inherited from
 *       {@link StockItem} and work identically across all subclasses without
 *       duplication.</li>
 * </ul>
 *
 * <h2>Subclasses tested:</h2>
 * <ul>
 *   <li>{@link NavSys}       — stock code {@code "NS101"}, qty {@code 10},
 *       price {@code £99.99}</li>
 *   <li>{@link Tyre}         — stock code {@code "T101"},  qty {@code 15},
 *       price {@code £59.99}, size {@code "205/55R16"},
 *       season {@code "All-season"}</li>
 *   <li>{@link CarBattery}   — stock code {@code "B101"},  qty {@code 8},
 *       price {@code £89.99}, {@code 12V}, {@code 60Ah}</li>
 *   <li>{@link OilFilter}    — stock code {@code "OF101"}, qty {@code 25},
 *       price {@code £9.99},  type {@code "Spin-on"},
 *       compatible with {@code "Ford Fiesta 1.0 EcoBoost"}</li>
 * </ul>
 *
 * @author  Nadira Robleh
 * @version 1.0
 * @see     StockItem
 * @see     NavSys
 * @see     Tyre
 * @see     CarBattery
 * @see     OilFilter
 */
public class TestPolymorphism {

    // -----------------------------------------------------------------------
    // Required Class Method (specified by project brief)
    // -----------------------------------------------------------------------

    /**
     * Interactively operates on a single {@link StockItem} (or any subclass
     * instance), demonstrating polymorphism and dynamic method binding.
     *
     * <p>This method is specified directly by the project brief. It accepts
     * any {@link StockItem} reference — which may point to a {@link NavSys},
     * {@link Tyre}, {@link CarBattery}, or {@link OilFilter} instance at
     * runtime — and performs the following sequence of operations:</p>
     * <ol>
     *   <li>Prints the item's current details via {@link StockItem#toString()},
     *       which resolves to the correct subclass override through dynamic
     *       method binding.</li>
     *   <li>Prompts the user to enter a number of units to add; calls
     *       {@link StockItem#addStock(int)} and prints updated details.</li>
     *   <li>Prompts the user to enter a number of units to sell; calls
     *       {@link StockItem#sellStock(int)} and prints updated details.</li>
     *   <li>Prompts the user to enter a new price (excluding VAT); calls
     *       {@link StockItem#setPriceWithoutVAT(double)} and prints updated
     *       details.</li>
     * </ol>
     *
     * <p>Error conditions (e.g. adding zero units, selling more than available)
     * are handled internally by {@link StockItem#addStock(int)} and
     * {@link StockItem#sellStock(int)}, which print suitable error messages
     * as required by the brief.</p>
     *
     * <p><b>Dynamic method binding in action:</b> although the parameter type
     * is {@link StockItem}, calling {@code s.toString()} will invoke
     * {@code NavSys.toString()}, {@code Tyre.toString()}, etc., at runtime —
     * the JVM selects the correct implementation based on the actual object
     * type, not the declared reference type.</p>
     *
     * @param s a {@link StockItem} reference pointing to any subclass instance
     *          to operate on; must not be {@code null}
     */
    public static void itemInstance(StockItem s) {
        Scanner input = new Scanner(System.in);

        // Print initial item details before any operations
        System.out.println("\n--------------------------------------");
        System.out.println("Printing item stock information:");
        System.out.println(s);

        // --- Step 1: Add stock ----------------------------------------------
        // Prompt user for units to add; addStock() handles validation internally
        System.out.print("\nEnter units to add: ");
        int add = input.nextInt();
        s.addStock(add);
        System.out.println("\nPrinting item stock information:");
        System.out.println(s);

        // --- Step 2: Sell stock ---------------------------------------------
        // Prompt user for units to sell; sellStock() handles validation internally
        System.out.print("\nEnter units to sell: ");
        int sell = input.nextInt();
        s.sellStock(sell);
        System.out.println("\nPrinting item stock information:");
        System.out.println(s);

        // --- Step 3: Change price -------------------------------------------
        // Prompt user for new price (excluding VAT);
        // setPriceWithoutVAT() handles negative-value validation internally
        System.out.print("\nEnter new price (without VAT): ");
        double newPrice = input.nextDouble();
        s.setPriceWithoutVAT(newPrice);
        System.out.println("\nPrinting item stock information:");
        System.out.println(s);
    }


    // -----------------------------------------------------------------------
    // Main Method
    // -----------------------------------------------------------------------

    /**
     * Entry point for the polymorphism test sequence.
     *
     * <p>Creates one instance of each {@link StockItem} subclass, stores all
     * four in a {@code StockItem[]} array (demonstrating that subclass objects
     * can be referenced through a superclass type), and then iterates over the
     * array with an enhanced {@code for} loop, passing each element to
     * {@link #itemInstance(StockItem)}.</p>
     *
     * <p>This structure directly satisfies the Step 3 requirement from the
     * project brief, which specifies:</p>
     * <ul>
     *   <li>A {@code StockItem[]} array of size 4 holding one instance of
     *       each subclass.</li>
     *   <li>A loop that calls {@link #itemInstance(StockItem)} for each
     *       element.</li>
     * </ul>
     *
     * <p>The array contents and their initial values are as follows:</p>
     * <ul>
     *   <li>{@code items[0]} — {@link NavSys}:
     *       code {@code "NS101"}, qty {@code 10}, price {@code £99.99}</li>
     *   <li>{@code items[1]} — {@link Tyre}:
     *       code {@code "T101"}, qty {@code 15}, price {@code £59.99},
     *       size {@code "205/55R16"}, season {@code "All-season"}</li>
     *   <li>{@code items[2]} — {@link CarBattery}:
     *       code {@code "B101"}, qty {@code 8}, price {@code £89.99},
     *       {@code 12V}, {@code 60Ah}</li>
     *   <li>{@code items[3]} — {@link OilFilter}:
     *       code {@code "OF101"}, qty {@code 25}, price {@code £9.99},
     *       type {@code "Spin-on"},
     *       compatible with {@code "Ford Fiesta 1.0 EcoBoost"}</li>
     * </ul>
     *
     * @param args command-line arguments; not used by this test class
     */
    public static void main(String[] args) {

        // Declare a StockItem array of size 4 — the superclass type is used
        // deliberately to demonstrate that all subclasses are valid StockItems
        // (Liskov Substitution Principle / polymorphism)
        StockItem[] items = new StockItem[4];

        // Populate array with one instance of each subclass
        items[0] = new NavSys("NS101", 10, 99.99);
        items[1] = new Tyre("T101", 15, 59.99, "205/55R16", "All-season");
        items[2] = new CarBattery("B101", 8, 89.99, 12, 60);
        items[3] = new OilFilter("OF101", 25, 9.99, "Spin-on", "Ford Fiesta 1.0 EcoBoost");

        // Iterate over all items and call itemInstance() for each.
        // The correct toString(), getStockName(), and getStockDescription()
        // implementations are selected at runtime via dynamic method binding.
        for (StockItem s : items) {
            itemInstance(s);
        }
    }
}
