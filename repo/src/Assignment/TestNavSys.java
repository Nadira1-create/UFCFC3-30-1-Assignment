package Assignment;

/**
 * Test class for NavSys (Step 2).
 * Creates a NavSys instance, exercises addStock, sellStock, setPriceWithoutVAT,
 * and tests the error-message path required by the brief (add 0 units).
 */
public class TestNavSys {

    public static void main(String[] args) {

        // Task 1 - create NavSys NS101, qty 10, price 99.99
        NavSys nav = new NavSys("NS101", 10, 99.99);
        System.out.println("Printing stock item information");
        System.out.println(nav);

        // Task 2 - add 10 more units
        nav.addStock(10);
        System.out.println("\nPrinting stock item information");
        System.out.println(nav);

        // Task 3 - sell 2 units
        nav.sellStock(2);
        System.out.println("\nPrinting stock item information");
        System.out.println(nav);

        // Task 4 - set new price 100.99
        nav.setPriceWithoutVAT(100.99);
        System.out.println("\nPrinting stock item information");
        System.out.println(nav);

        // Task 5 - try to add 0 (must print error per brief)
        nav.addStock(0); // must print: The error was: Increased item must be greater than or equal to one
    }
}
