package Assignment;

/**
 * Test class for StockItem (Step 1).
 * Creates a StockItem, exercises addStock, sellStock, setPriceWithoutVAT,
 * and tests the error-message paths required by the brief.
 */
public class TestStockItem {

    public static void main(String[] args) {

        // Task 1 - create item W101, qty 10, price 99.99
        StockItem item = new StockItem("W101", 10, 99.99);
        System.out.println("Printing stock item information");
        System.out.println(item);

        // Task 2 - add 10 more units
        item.addStock(10);
        System.out.println("\nPrinting stock item information");
        System.out.println(item);

        // Task 3 - sell 2 units
        item.sellStock(2);
        System.out.println("\nPrinting stock item information");
        System.out.println(item);

        // Task 4 - set new price 100.99
        item.setPriceWithoutVAT(100.99);
        System.out.println("\nPrinting stock item information");
        System.out.println(item);

        // Error path - try to add 0 (must print error)
        item.addStock(0);

        // Error path - try to sell 0 (must print error)
        item.sellStock(0);

        // Error path - try to set negative price (must print error)
        item.setPriceWithoutVAT(-5.00);

        // Error path - try to exceed MAX_STOCK
        item.addStock(100); // already at 18, this would push over 100
    }
}
