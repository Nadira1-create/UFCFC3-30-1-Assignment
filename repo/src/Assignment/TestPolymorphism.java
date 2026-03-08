package Assignment;

import java.util.Scanner;

/**
 * Step 3 - TestPolymorphism.
 * Demonstrates polymorphism and dynamic method binding across all StockItem subclasses.
 *
 * Required class method:
 *   itemInstance(StockItem s) - asks for add amount, sell amount, new price,
 *     applies actions, and prints the item in between.
 */
public class TestPolymorphism {

    /**
     * Required class method as specified in the brief.
     * Takes one StockItem (or any subclass), asks the user for:
     * - units to add to stock
     * - units to sell from stock
     * - new price (without VAT)
     * Applies each action and prints the item details in between.
     * Demonstrates dynamic method binding: toString() resolves to the correct
     * subclass implementation at runtime.
     *
     * @param s a StockItem (or any subclass) to operate on
     */
    public static void itemInstance(StockItem s) {
        Scanner input = new Scanner(System.in);

        System.out.println("\n--------------------------------------");
        System.out.println("Printing item stock information:");
        System.out.println(s);

        // Add stock
        System.out.print("\nEnter units to add: ");
        int add = input.nextInt();
        s.addStock(add);
        System.out.println("\nPrinting item stock information:");
        System.out.println(s);

        // Sell stock
        System.out.print("\nEnter units to sell: ");
        int sell = input.nextInt();
        s.sellStock(sell);
        System.out.println("\nPrinting item stock information:");
        System.out.println(s);

        // Change price
        System.out.print("\nEnter new price (without VAT): ");
        double newPrice = input.nextDouble();
        s.setPriceWithoutVAT(newPrice);
        System.out.println("\nPrinting item stock information:");
        System.out.println(s);
    }

    /**
     * Main method.
     * Creates one instance of each subclass, builds a StockItem array,
     * and calls itemInstance() for each — demonstrating polymorphism.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

        // Array of 4 StockItems (one NavSys + three invented subclasses)
        StockItem[] items = new StockItem[4];

        items[0] = new NavSys("NS101", 10, 99.99);
        items[1] = new Tyre("T101", 15, 59.99, "205/55R16", "All-season");
        items[2] = new CarBattery("B101", 8, 89.99, 12, 60);
        items[3] = new OilFilter("OF101", 25, 9.99, "Spin-on", "Ford Fiesta 1.0 EcoBoost");

        // Loop through all items, calling itemInstance for each
        for (StockItem s : items) {
            itemInstance(s);
        }
    }
}
